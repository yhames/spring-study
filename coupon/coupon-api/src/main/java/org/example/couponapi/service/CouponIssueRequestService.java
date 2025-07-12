package org.example.couponapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.couponapi.controller.dto.CouponIssueRequestDto;
import org.example.couponcore.component.DistributeLockExecutor;
import org.example.couponcore.service.CouponIssueService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponIssueRequestService {

    private final CouponIssueService couponIssueService;
    private final DistributeLockExecutor distributeLockExecutor;

    public void issueCoupon(CouponIssueRequestDto request) {
        distributeLockExecutor.execute("lock_" + request.couponId(), 10000, 10000,
                () -> couponIssueService.issue(request.couponId(), request.userId()));
        log.info("쿠폰 발급 완료: couponId={}, userId={}", request.couponId(), request.userId());
    }
}
