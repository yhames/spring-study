package org.example.couponapi.controller;

import lombok.RequiredArgsConstructor;
import org.example.couponapi.controller.dto.CouponIssueRequestDto;
import org.example.couponapi.controller.dto.CouponIssueResponseDto;
import org.example.couponapi.service.CouponIssueRequestService;
import org.example.couponcore.service.AsyncCouponIssueService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CouponIssueController {

    private final CouponIssueRequestService couponIssueRequestService;
    private final AsyncCouponIssueService asyncCouponIssueService;

    @PostMapping("/v1/issue")
    public CouponIssueResponseDto issueCoupon(@RequestBody CouponIssueRequestDto request) {
        couponIssueRequestService.issueCoupon(request);
        return new CouponIssueResponseDto(true, null);
    }

    @PostMapping("/v1/issue-async")
    public CouponIssueResponseDto issueCouponAsync(@RequestBody CouponIssueRequestDto request) {
        asyncCouponIssueService.issue(request.couponId(), request.userId());
        return new CouponIssueResponseDto(true, null);
    }
}
