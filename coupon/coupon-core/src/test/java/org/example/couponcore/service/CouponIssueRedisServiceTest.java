package org.example.couponcore.service;

import org.assertj.core.api.Assertions;
import org.example.couponcore.TestConfig;
import org.example.couponcore.utils.CouponRedisUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.stream.IntStream;

class CouponIssueRedisServiceTest extends TestConfig {

    @Autowired
    CouponIssueRedisService sut;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @AfterEach
    void clear() {
        Collection<String> keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
    }

    @Test
    @DisplayName("쿠폰 수량 검증 - 발급 가능 수량이 존재하면 true를 반환한다.")
    void test() {
        int totalIssueQuantity = 10;
        long couponId = 1L;

        boolean result = sut.availableTotalIssueQuantity(totalIssueQuantity, couponId);

        Assertions.assertThat(result).isTrue();
    }

    @Test
    @DisplayName("쿠폰 수량 검증 - 발급 가능 수량이 모두 소진되었다면 false를 반환한다.")
    void test2() {
        int totalIssueQuantity = 10;
        long couponId = 1L;
        IntStream.range(0, totalIssueQuantity).forEach(i ->
                redisTemplate.opsForSet()
                        .add(CouponRedisUtils.getIssueRequestKey(couponId), String.valueOf(i)));

        boolean result = sut.availableTotalIssueQuantity(totalIssueQuantity, couponId);

        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("쿠폰 중복 발급 검증 - 발급된 내역에 유저가 존재하지 않으면 true를 반환한다.")
    void test3() {
        long couponId = 1L;
        long userId = 1L;

        boolean result = sut.availableUserIssueQuantity(couponId, userId);

        Assertions.assertThat(result).isTrue();
    }

    @Test
    @DisplayName("쿠폰 중복 발급 검증 - 발급된 내역에 유저가 존재하면 false를 반환한다.")
    void test4() {
        long couponId = 1L;
        long userId = 1L;
        redisTemplate.opsForSet()
                .add(CouponRedisUtils.getIssueRequestKey(couponId), String.valueOf(userId));

        boolean result = sut.availableUserIssueQuantity(couponId, userId);

        Assertions.assertThat(result).isFalse();
    }

}