package org.example.couponcore.execption;

public enum ErrorCode {
    INVALID_COUPON_ISSUE_QUANTITY("발급 가능한 수량을 초과했습니다."),
    INVALID_COUPON_ISSUE_DATE("발급 기간이 유효하지 않습니다."),
    COUPON_NOT_EXIST("쿠폰이 존재하지 않습니다."),
    COUPON_ALREADY_ISSUED("이미 발급된 쿠폰입니다."),
    DUPLICATE_COUPON_ISSUE("이미 발급 요청이 처리되었습니다."),
    FAILED_COUPON_ISSUE_REQUEST("쿠폰 발급에 실패했습니다.");

    public final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
