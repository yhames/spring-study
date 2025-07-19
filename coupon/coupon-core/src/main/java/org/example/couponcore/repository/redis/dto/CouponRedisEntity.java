package org.example.couponcore.repository.redis.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.example.couponcore.execption.CouponIssueException;
import org.example.couponcore.execption.ErrorCode;
import org.example.couponcore.model.Coupon;
import org.example.couponcore.model.CouponType;

import java.time.LocalDateTime;

public record CouponRedisEntity(
        Long id,

        CouponType type,

        Integer totalQuantity,

        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        LocalDateTime dateIssueStart,

        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        LocalDateTime dateIssueEnd
) {
    public CouponRedisEntity(Coupon coupon) {
        this(
                coupon.getId(),
                coupon.getCouponType(),
                coupon.getTotalQuantity(),
                coupon.getDateIssueStart(),
                coupon.getDateIssueEnd()
        );
    }

    private boolean availableIssueDate() {
        LocalDateTime now = LocalDateTime.now();
        return dateIssueStart.isBefore(now) && dateIssueEnd.isAfter(now);
    }

    public void checkIssuableCoupon() {
        if (!availableIssueDate()) {
            throw new CouponIssueException(ErrorCode.INVALID_COUPON_ISSUE_DATE,
                    "쿠폰 발급 기간이 유효하지 않습니다. couponId: %s, issueStart: %s, issueEnd: %s"
                            .formatted(id, dateIssueStart, dateIssueEnd));
        }
    }
}
