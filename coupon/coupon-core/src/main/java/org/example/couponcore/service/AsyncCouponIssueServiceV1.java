package org.example.couponcore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.couponcore.component.DistributeLockExecutor;
import org.example.couponcore.execption.CouponIssueException;
import org.example.couponcore.repository.redis.RedisRepository;
import org.example.couponcore.repository.redis.dto.CouponIssueRequest;
import org.example.couponcore.repository.redis.dto.CouponRedisEntity;

import static org.example.couponcore.execption.ErrorCode.FAILED_COUPON_ISSUE_REQUEST;
import static org.example.couponcore.utils.CouponRedisUtils.getIssueRequestKey;
import static org.example.couponcore.utils.CouponRedisUtils.getIssueRequestQueueKey;

@RequiredArgsConstructor
public class AsyncCouponIssueServiceV1 implements AsyncCouponIssueService {

    private final RedisRepository redisRepository;
    private final CouponIssueRedisService couponIssueRedisService;
    private final CouponCacheService couponCacheService;
    private final DistributeLockExecutor distributeLockExecutor;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void issue(long couponId, long userId) {
        CouponRedisEntity coupon = couponCacheService.getCouponFromCache(couponId);
        coupon.checkIssuableCoupon();

        // 동시성 문제를 방지하기 위해 Redis 분산 락 사용
        distributeLockExecutor.execute("lock_%s".formatted(couponId), 3000, 3000, () -> {
            couponIssueRedisService.checkCouponIssueQuantity(coupon, userId);
            issueRequest(couponId, userId);
        });
    }

    private void issueRequest(long couponId, long userId) {
        CouponIssueRequest request = new CouponIssueRequest(couponId, userId);
        try {
            String value = objectMapper.writeValueAsString(request);
            redisRepository.sAdd(getIssueRequestKey(couponId), String.valueOf(userId));
            redisRepository.rPush(getIssueRequestQueueKey(), value);
        } catch (JsonProcessingException e) {
            throw new CouponIssueException(FAILED_COUPON_ISSUE_REQUEST,
                    "input: %s, error: %s".formatted(request, e.getMessage()));
        }
    }
}
