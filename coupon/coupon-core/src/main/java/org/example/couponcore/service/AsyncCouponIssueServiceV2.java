package org.example.couponcore.service;

import lombok.RequiredArgsConstructor;
import org.example.couponcore.repository.redis.RedisRepository;
import org.example.couponcore.repository.redis.dto.CouponRedisEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsyncCouponIssueServiceV2 implements AsyncCouponIssueService {

    private final RedisRepository redisRepository;
    private final CouponCacheService couponCacheService;

    /**
     * redisRepository.sCard(key) < totalQuantity;
     * redisRepository.sIsMember(key, String.valueOf(userId));
     * redisRepository.sAdd(getIssueRequestKey(couponId), String.valueOf(userId));
     * redisRepository.rPush(getIssueRequestQueueKey(), value);
     */
    @Override
    public void issue(long couponId, long userId) {
        CouponRedisEntity coupon = couponCacheService.getCouponFromCache(couponId);
        coupon.checkIssuableCoupon();
        issueRequest(couponId, userId, coupon.totalQuantity());
    }

    private void issueRequest(long couponId, long userId, Integer totalQuantity) {
        if (totalQuantity == null) {
            totalQuantity = Integer.MAX_VALUE; // No limit on total quantity
        }
        redisRepository.issueRequest(couponId, userId, totalQuantity);
    }
}
