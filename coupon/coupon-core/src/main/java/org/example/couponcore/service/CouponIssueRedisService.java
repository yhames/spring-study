package org.example.couponcore.service;

import lombok.RequiredArgsConstructor;
import org.example.couponcore.execption.CouponIssueException;
import org.example.couponcore.execption.ErrorCode;
import org.example.couponcore.repository.redis.RedisRepository;
import org.example.couponcore.repository.redis.dto.CouponRedisEntity;
import org.example.couponcore.utils.CouponRedisUtils;
import org.springframework.stereotype.Service;

import static org.example.couponcore.execption.ErrorCode.INVALID_COUPON_ISSUE_QUANTITY;

@Service
@RequiredArgsConstructor
public class CouponIssueRedisService {

    private final RedisRepository redisRepository;

    public void checkCouponIssueQuantity(CouponRedisEntity coupon, long userId) {
        if (!availableTotalIssueQuantity(coupon.totalQuantity(), coupon.id())) {
            throw new CouponIssueException(INVALID_COUPON_ISSUE_QUANTITY,
                    "쿠폰 발급 수량이 초과되었습니다. couponId: %s, totalQuantity: %s"
                            .formatted(coupon.id(), coupon.totalQuantity()));
        }

        if (!availableUserIssueQuantity(coupon.id(), userId)) {
            throw new CouponIssueException(ErrorCode.DUPLICATE_COUPON_ISSUE,
                    "이미 발급 요청이 처리되었습니다. couponId: %s, userId: %s".formatted(coupon.id(), userId));
        }
    }

    public boolean availableTotalIssueQuantity(Integer totalQuantity, long couponId) {
        if (totalQuantity == null || totalQuantity <= 0) {
            return true;    // No limit on total quantity
        }
        String key = CouponRedisUtils.getIssueRequestKey(couponId);
        return redisRepository.sCard(key) < totalQuantity;
    }

    public boolean availableUserIssueQuantity(long couponId, long userId) {
        String key = CouponRedisUtils.getIssueRequestKey(couponId);
        return !redisRepository.sIsMember(key, String.valueOf(userId));
    }
}
