package org.example.couponcore.model;

import org.example.couponcore.execption.CouponIssueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.couponcore.execption.ErrorCode.INVALID_COUPON_ISSUE_DATE;
import static org.example.couponcore.execption.ErrorCode.INVALID_COUPON_ISSUE_QUANTITY;

class CouponTest {

    @Test
    @DisplayName("발급 수량이 남아있다면 true를 반환한다")
    void availableIssueQuantity_1() {
        // given
        Coupon coupon = Coupon.builder()
                .totalQuantity(100)
                .issuedQuantity(99)
                .build();

        // when
        boolean result = coupon.availableIssueQuantity();

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("발급 수량이 소진되었다면 false를 반환한다")
    void availableIssueQuantity_2() {
        // given
        Coupon coupon = Coupon.builder()
                .totalQuantity(100)
                .issuedQuantity(100)
                .build();

        // when
        boolean result = coupon.availableIssueQuantity();

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("최대 발급 수량이 설정되지 않았다면 true를 반환한다")
    void availableIssueQuantity_3() {
        // given
        Coupon coupon = Coupon.builder()
                .totalQuantity(null)
                .issuedQuantity(100)
                .build();

        // when
        boolean result = coupon.availableIssueQuantity();

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("발급 기간이 시작되지 않았다면, false를 반환한다")
    void availableIssueDate_1() {
        // given
        Coupon coupon = Coupon.builder()
                .dateIssueStart(LocalDateTime.now().plusDays(1))
                .dateIssueEnd(LocalDateTime.now().plusDays(2))
                .build();

        // when
        boolean result = coupon.availableIssueDate();

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("발급 기간에 해당하는 경우 true를 반환한다")
    void availableIssueDate_2() {
        // given
        Coupon coupon = Coupon.builder()
                .dateIssueStart(LocalDateTime.now().minusDays(1))
                .dateIssueEnd(LocalDateTime.now().plusDays(2))
                .build();

        // when
        boolean result = coupon.availableIssueDate();

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("발급 기간이 종료된 경우 false를 반환한다")
    void availableIssueDate_3() {
        // given
        Coupon coupon = Coupon.builder()
                .dateIssueStart(LocalDateTime.now().minusDays(1))
                .dateIssueEnd(LocalDateTime.now().minusDays(2))
                .build();

        // when
        boolean result = coupon.availableIssueDate();

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("발급 수량이 남아있고 발급 기간이 유효하다면 발급에 성공한다.")
    void issueCoupon_1() {
        // given
        Coupon coupon = Coupon.builder()
                .totalQuantity(100)
                .issuedQuantity(99)
                .dateIssueStart(LocalDateTime.now().minusDays(1))
                .dateIssueEnd(LocalDateTime.now().plusDays(2))
                .build();

        // when
        coupon.issue();

        // then
        assertThat(coupon.getIssuedQuantity()).isEqualTo(100);
    }

    @Test
    @DisplayName("발급 수량을 초과하면 예외를 반환한다")
    void issueCoupon_2() {
        // given
        Coupon coupon = Coupon.builder()
                .totalQuantity(100)
                .issuedQuantity(100)
                .dateIssueStart(LocalDateTime.now().minusDays(1))
                .dateIssueEnd(LocalDateTime.now().plusDays(2))
                .build();

        // expected
        CouponIssueException exception = Assertions.assertThrows(CouponIssueException.class, coupon::issue);
        assertThat(exception.getErrorCode()).isEqualTo(INVALID_COUPON_ISSUE_QUANTITY);
    }

    @Test
    @DisplayName("발급 기간이 아니면 예외를 반환한다")
    void issueCoupon_3() {
        // given
        Coupon coupon = Coupon.builder()
                .totalQuantity(100)
                .issuedQuantity(99)
                .dateIssueStart(LocalDateTime.now().plusDays(1))
                .dateIssueEnd(LocalDateTime.now().plusDays(2))
                .build();

        // expected
        CouponIssueException exception = Assertions.assertThrows(CouponIssueException.class, coupon::issue);
        assertThat(exception.getErrorCode()).isEqualTo(INVALID_COUPON_ISSUE_DATE);
    }

    @Test
    @DisplayName("발급 기간이 만료되면 isIssueCompleted가 true를 반환한다")
    void test() {
        Coupon coupon = Coupon.builder()
                .totalQuantity(100)
                .issuedQuantity(99)
                .dateIssueStart(LocalDateTime.now().minusDays(2))
                .dateIssueEnd(LocalDateTime.now().minusDays(1))
                .build();
        boolean issueCompleted = coupon.isIssueCompleted();
        assertThat(issueCompleted).isTrue();
    }

    @Test
    @DisplayName("잔여 발급 수량이 없으면 isIssueCompleted가 true를 반환한다")
    void test2() {
        Coupon coupon = Coupon.builder()
                .totalQuantity(100)
                .issuedQuantity(100)
                .dateIssueStart(LocalDateTime.now().minusDays(2))
                .dateIssueEnd(LocalDateTime.now().plusDays(1))
                .build();
        boolean issueCompleted = coupon.isIssueCompleted();
        assertThat(issueCompleted).isTrue();
    }

    @Test
    @DisplayName("발급 기한과 수량이 유효하면 isIssueCompleted가 false를 반환한다")
    void test3() {
        Coupon coupon = Coupon.builder()
                .totalQuantity(100)
                .issuedQuantity(99)
                .dateIssueStart(LocalDateTime.now().minusDays(2))
                .dateIssueEnd(LocalDateTime.now().plusDays(1))
                .build();
        boolean issueCompleted = coupon.isIssueCompleted();
        assertThat(issueCompleted).isFalse();
    }
}