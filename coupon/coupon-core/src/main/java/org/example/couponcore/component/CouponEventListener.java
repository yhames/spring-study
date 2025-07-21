package org.example.couponcore.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.couponcore.model.event.CouponIssueCompleteEvent;
import org.example.couponcore.service.CouponCacheService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponEventListener {

    private final CouponCacheService couponCacheService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void issueComplete(CouponIssueCompleteEvent event) {
        log.info("issue complete. cache refresh start couponId: {}", event.couponId());
        couponCacheService.putCouponToCache(event.couponId());
        couponCacheService.putCouponToLocalCache(event.couponId());
        log.info("issue complete. cache refresh end couponId: {}", event.couponId());
    }
}
