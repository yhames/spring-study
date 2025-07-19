package org.example.couponcore.service;

import lombok.RequiredArgsConstructor;
import org.example.couponcore.model.Coupon;
import org.example.couponcore.repository.redis.dto.CouponRedisEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponCacheService {

    private final CouponIssueService couponIssueService;

    @Cacheable(cacheNames = "coupon")
    public CouponRedisEntity getCouponFromCache(long couponId) {
        Coupon coupon = couponIssueService.findCouponById(couponId);
        return new CouponRedisEntity(coupon);
    }
}
