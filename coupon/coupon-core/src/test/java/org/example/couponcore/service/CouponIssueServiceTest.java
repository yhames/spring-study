package org.example.couponcore.service;

import org.example.couponcore.TestConfig;
import org.example.couponcore.execption.CouponIssueException;
import org.example.couponcore.execption.ErrorCode;
import org.example.couponcore.model.Coupon;
import org.example.couponcore.model.CouponIssue;
import org.example.couponcore.model.CouponType;
import org.example.couponcore.repository.mysql.CouponIssueJpaRepository;
import org.example.couponcore.repository.mysql.CouponIssueRepository;
import org.example.couponcore.repository.mysql.CouponJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CouponIssueServiceTest extends TestConfig {

    @Autowired
    CouponIssueService sut;

    @Autowired
    CouponIssueJpaRepository couponIssueJpaRepository;

    @Autowired
    CouponIssueRepository couponIssueRepository;

    @Autowired
    CouponJpaRepository couponJpaRepository;

    @BeforeEach
    void clean() {
        couponIssueJpaRepository.deleteAllInBatch();
        couponJpaRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("쿠폰 발급 내역이 존재하면 예외를 반환한다.")
    void issue() {
        // given
        CouponIssue couponIssue = CouponIssue.builder()
                .couponId(1L)
                .userId(1L)
                .build();
        couponIssueJpaRepository.save(couponIssue);

        // expected
        CouponIssueException exception = Assertions.assertThrows(CouponIssueException.class, () ->
                sut.saveCouponIssue(couponIssue.getCouponId(), couponIssue.getUserId()));
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.COUPON_ALREADY_ISSUED);
    }

    @Test
    @DisplayName("쿠폰 발급 내역이 존재하지 않는다면 정상적으로 쿠폰을 발급한다.")
    void issue_2() {
        // given
        long couponId = 1L;
        long userId = 1L;

        // when
        CouponIssue couponIssue = sut.saveCouponIssue(couponId, userId);

        // then
        Optional<CouponIssue> findById = couponIssueJpaRepository.findById(couponIssue.getId());
        assertThat(findById).isPresent();
        assertThat(findById.get().getCouponId()).isEqualTo(couponId);
    }

    @Test
    @DisplayName("발급 수량, 기한, 중복 발급 문제가 없다면 쿠폰을 발급한다.")
    void issue_3() {
        // given
        long userId = 1L;
        int issuedQuantity = 0;
        Coupon coupon = Coupon.builder()
                .couponType(CouponType.FIRST_COME_FIRST_SERVED)
                .title("선착순 테스트 쿠폰")
                .totalQuantity(100)
                .issuedQuantity(issuedQuantity)
                .dateIssueStart(LocalDateTime.now().minusDays(1))
                .dateIssueEnd(LocalDateTime.now().plusDays(1))
                .build();
        couponJpaRepository.save(coupon);

        // when
        sut.issue(coupon.getId(), userId);

        // then
        Optional<Coupon> findById = couponJpaRepository.findById(coupon.getId());
        assertThat(findById).isPresent();
        assertThat(findById.get().getIssuedQuantity()).isEqualTo(issuedQuantity + 1);

        Optional<CouponIssue> findCouponIssue = couponIssueRepository.findFirstCouponIssue(coupon.getId(), userId);
        assertThat(findCouponIssue).isPresent();
    }

    @Test
    @DisplayName("발급 수량에 문제가 있다면 예외를 반환한다.")
    void issue_4() {
        // given
        long userId = 1L;
        Coupon coupon = Coupon.builder()
                .couponType(CouponType.FIRST_COME_FIRST_SERVED)
                .title("선착순 테스트 쿠폰")
                .totalQuantity(100)
                .issuedQuantity(100)
                .dateIssueStart(LocalDateTime.now().minusDays(1))
                .dateIssueEnd(LocalDateTime.now().plusDays(1))
                .build();
        couponJpaRepository.save(coupon);

        // expected
        CouponIssueException exception = Assertions.assertThrows(CouponIssueException.class, () ->
                sut.issue(coupon.getId(), userId));
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.INVALID_COUPON_ISSUE_QUANTITY);
    }

    @Test
    @DisplayName("발급 기한에 문제가 있다면 예외를 반환한다.")
    void issue_5() {
        // given
        long userId = 1L;
        Coupon coupon = Coupon.builder()
                .couponType(CouponType.FIRST_COME_FIRST_SERVED)
                .title("선착순 테스트 쿠폰")
                .totalQuantity(100)
                .issuedQuantity(0)
                .dateIssueStart(LocalDateTime.now().minusDays(2))
                .dateIssueEnd(LocalDateTime.now().minusDays(1))
                .build();
        couponJpaRepository.save(coupon);

        // expected
        CouponIssueException exception = Assertions.assertThrows(CouponIssueException.class, () ->
                sut.issue(coupon.getId(), userId));
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.INVALID_COUPON_ISSUE_DATE);
    }

    @Test
    @DisplayName("이미 발급한 쿠폰이 있다면 예외를 반환한다.")
    void issue_6() {
        // given
        long userId = 1L;
        Coupon coupon = Coupon.builder()
                .couponType(CouponType.FIRST_COME_FIRST_SERVED)
                .title("선착순 테스트 쿠폰")
                .totalQuantity(100)
                .issuedQuantity(0)
                .dateIssueStart(LocalDateTime.now().minusDays(1))
                .dateIssueEnd(LocalDateTime.now().plusDays(1))
                .build();
        couponJpaRepository.save(coupon);

        CouponIssue couponIssue = CouponIssue.builder()
                .couponId(coupon.getId())
                .userId(userId)
                .build();
        couponIssueJpaRepository.save(couponIssue);


        // when
        CouponIssueException exception = Assertions.assertThrows(CouponIssueException.class, () ->
                sut.issue(coupon.getId(), userId));
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.COUPON_ALREADY_ISSUED);
    }


    @Test
    @DisplayName("존재하지 않는 쿠폰이면 예외를 반환한다.")
    void issue_7() {
        // given
        long userId = 1L;
        long couponId = 999L; // Non-existing coupon ID

        // when
        CouponIssueException exception = Assertions.assertThrows(CouponIssueException.class, () ->
                sut.issue(couponId, userId));
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.COUPON_NOT_EXIST);
    }
}