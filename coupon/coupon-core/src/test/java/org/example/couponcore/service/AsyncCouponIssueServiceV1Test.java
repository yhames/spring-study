package org.example.couponcore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.couponcore.TestConfig;
import org.example.couponcore.execption.CouponIssueException;
import org.example.couponcore.execption.ErrorCode;
import org.example.couponcore.model.Coupon;
import org.example.couponcore.model.CouponType;
import org.example.couponcore.repository.mysql.CouponJpaRepository;
import org.example.couponcore.repository.redis.dto.CouponIssueRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.couponcore.utils.CouponRedisUtils.getIssueRequestKey;
import static org.example.couponcore.utils.CouponRedisUtils.getIssueRequestQueueKey;

@Disabled
class AsyncCouponIssueServiceV1Test extends TestConfig {

    @Autowired
    AsyncCouponIssueServiceV1 sut;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    CouponJpaRepository couponJpaRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @AfterEach
    void clear() {
        Collection<String> keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
    }

    @Test
    @DisplayName("쿠폰 발급 - 쿠폰이 존재하지 않는다면 예외를 반환한다.")
    void test() {
        long couponId = 1L;
        long userId = 1L;

        CouponIssueException exception = Assertions.assertThrows(CouponIssueException.class, () -> {
            sut.issue(couponId, userId);
        });

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.COUPON_NOT_EXIST);
    }


    @Test
    @DisplayName("쿠폰 발급 - 발급 가능한 수량이 초과되었다면 예외를 반환한다.")
    void test2() {
        long userId = 1000L;
        Coupon coupon = Coupon.builder()
                .title("Test Coupon")
                .couponType(CouponType.FIRST_COME_FIRST_SERVED)
                .totalQuantity(10)
                .issuedQuantity(0)
                .dateIssueStart(LocalDateTime.now().minusDays(1))
                .dateIssueEnd(LocalDateTime.now().plusDays(1))
                .build();
        couponJpaRepository.save(coupon);
        IntStream.range(0, coupon.getTotalQuantity()).forEach(idx ->
                redisTemplate.opsForSet().add(getIssueRequestKey(coupon.getId()), String.valueOf(idx)));

        CouponIssueException exception = Assertions.assertThrows(CouponIssueException.class, () -> {
            sut.issue(coupon.getId(), userId);
        });

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.INVALID_COUPON_ISSUE_QUANTITY);
    }

    @Test
    @DisplayName("쿠폰 발급 - 이미 발급된 쿠폰이 있다면 예외를 반환한다.")
    void test3() {
        long userId = 1;
        Coupon coupon = Coupon.builder()
                .title("Test Coupon")
                .couponType(CouponType.FIRST_COME_FIRST_SERVED)
                .totalQuantity(10)
                .issuedQuantity(0)
                .dateIssueStart(LocalDateTime.now().minusDays(1))
                .dateIssueEnd(LocalDateTime.now().plusDays(1))
                .build();
        couponJpaRepository.save(coupon);
        redisTemplate.opsForSet().add(getIssueRequestKey(coupon.getId()), String.valueOf(userId));

        CouponIssueException exception = Assertions.assertThrows(CouponIssueException.class, () ->
                sut.issue(coupon.getId(), userId));

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.DUPLICATE_COUPON_ISSUE);
    }

    @Test
    @DisplayName("쿠폰 발급 - 발급 기한이 유효하지 않다면 예외를 반환한다.")
    void test4() {
        long userId = 1;
        Coupon coupon = Coupon.builder()
                .title("Test Coupon")
                .couponType(CouponType.FIRST_COME_FIRST_SERVED)
                .totalQuantity(10)
                .issuedQuantity(0)
                .dateIssueStart(LocalDateTime.now().minusDays(2))
                .dateIssueEnd(LocalDateTime.now().minusDays(1))
                .build();
        couponJpaRepository.save(coupon);
        redisTemplate.opsForSet().add(getIssueRequestKey(coupon.getId()), String.valueOf(userId));

        CouponIssueException exception = Assertions.assertThrows(CouponIssueException.class, () ->
                sut.issue(coupon.getId(), userId));

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.INVALID_COUPON_ISSUE_DATE);
    }

    @Test
    @DisplayName("쿠폰 발급 - 쿠폰 발급을 기록한다.")
    void test5() {
        long userId = 1;
        Coupon coupon = Coupon.builder()
                .title("Test Coupon")
                .couponType(CouponType.FIRST_COME_FIRST_SERVED)
                .totalQuantity(10)
                .issuedQuantity(0)
                .dateIssueStart(LocalDateTime.now().minusDays(1))
                .dateIssueEnd(LocalDateTime.now().plusDays(1))
                .build();
        couponJpaRepository.save(coupon);

        sut.issue(coupon.getId(), userId);

        Boolean member = redisTemplate.opsForSet()
                .isMember(getIssueRequestKey(coupon.getId()), String.valueOf(userId));
        assertThat(member).isTrue();
    }

    @Test
    @DisplayName("쿠폰 발급 - 쿠폰 발급 요청이 성공하면 쿠폰 발급 큐에 적재한다.")
    void test6() throws JsonProcessingException {
        long userId = 1;
        Coupon coupon = Coupon.builder()
                .title("Test Coupon")
                .couponType(CouponType.FIRST_COME_FIRST_SERVED)
                .totalQuantity(10)
                .issuedQuantity(0)
                .dateIssueStart(LocalDateTime.now().minusDays(1))
                .dateIssueEnd(LocalDateTime.now().plusDays(1))
                .build();
        couponJpaRepository.save(coupon);
        CouponIssueRequest request = new CouponIssueRequest(coupon.getId(), userId);
        String value = objectMapper.writeValueAsString(request);

        sut.issue(coupon.getId(), userId);

        String saved = redisTemplate.opsForList()
                .leftPop(getIssueRequestQueueKey());
        assertThat(saved).isNotNull();
        assertThat(saved).isEqualTo(value);
    }
}