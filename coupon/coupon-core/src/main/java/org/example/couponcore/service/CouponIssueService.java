package org.example.couponcore.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.couponcore.execption.CouponIssueException;
import org.example.couponcore.model.Coupon;
import org.example.couponcore.model.CouponIssue;
import org.example.couponcore.repository.mysql.CouponIssueJpaRepository;
import org.example.couponcore.repository.mysql.CouponIssueRepository;
import org.example.couponcore.repository.mysql.CouponJpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.example.couponcore.execption.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CouponIssueService {

    private final CouponJpaRepository couponJpaRepository;
    private final CouponIssueRepository couponIssueRepository;
    private final CouponIssueJpaRepository couponIssueJpaRepository;

    @Transactional
    public void issue(long couponId, long userId) {
        Coupon coupon = findCouponByIdWithLock(couponId);
        coupon.issue();
        saveCouponIssue(couponId, userId);
    }

    @Transactional
    public Coupon findCouponById(long couponId) {
        return couponJpaRepository.findById(couponId)
                .orElseThrow(() -> new CouponIssueException(COUPON_NOT_EXIST,
                        "쿠폰 정책이 존재하지 않습니다. %s".formatted(couponId)));
    }

    @Transactional
    public Coupon findCouponByIdWithLock(long couponId) {
        return couponJpaRepository.findCouponByIdWithLock(couponId)
                .orElseThrow(() -> new CouponIssueException(COUPON_NOT_EXIST,
                        "쿠폰 정책이 존재하지 않습니다. %s".formatted(couponId)));
    }

    @Transactional
    public CouponIssue saveCouponIssue(long couponId, long userId) {
        checkAlreadyIssued(couponId, userId);
        CouponIssue couponIssue = CouponIssue.builder()
                .couponId(couponId)
                .userId(userId)
                .build();
        return couponIssueJpaRepository.save(couponIssue);
    }

    private void checkAlreadyIssued(long couponId, long userId) {
        Optional<CouponIssue> couponIssue = couponIssueRepository.findFirstCouponIssue(couponId, userId);
        if (couponIssue.isPresent()) {
            throw new CouponIssueException(COUPON_ALREADY_ISSUED,
                    "이미 발급된 쿠폰입니다. user_id: %s, coupon_id: %s".formatted(userId, couponId));
        }
    }
}
