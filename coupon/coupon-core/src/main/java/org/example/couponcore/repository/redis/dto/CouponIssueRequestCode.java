package org.example.couponcore.repository.redis.dto;

import lombok.RequiredArgsConstructor;
import org.example.couponcore.execption.CouponIssueException;
import org.example.couponcore.execption.ErrorCode;

@RequiredArgsConstructor
public enum CouponIssueRequestCode {
    SUCCESS(1),
    DUPLICATED_COUPON_ISSUE(2),
    INVALID_COUPON_ISSUE_QUANTITY(3);

    private final int code;

    public static CouponIssueRequestCode of(String code) {
        return switch (code) {
            case "1" -> SUCCESS;
            case "2" -> DUPLICATED_COUPON_ISSUE;
            case "3" -> INVALID_COUPON_ISSUE_QUANTITY;
            default -> throw new IllegalArgumentException("존재하지 않는 코드입니다. %s".formatted(code));
        };
    }

    public static void checkRequestResult(CouponIssueRequestCode code) {
        if (code == INVALID_COUPON_ISSUE_QUANTITY)
            throw new CouponIssueException(ErrorCode.INVALID_COUPON_ISSUE_QUANTITY, "쿠폰 발급 수량이 초과되었습니다.");
        else if (code == DUPLICATED_COUPON_ISSUE) {
            throw new CouponIssueException(ErrorCode.DUPLICATE_COUPON_ISSUE, "이미 발급 요청이 처리되었습니다.");
        }
    }
}
