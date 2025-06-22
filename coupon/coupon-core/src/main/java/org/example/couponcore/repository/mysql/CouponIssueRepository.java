package org.example.couponcore.repository.mysql;

import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.couponcore.model.CouponIssue;
import org.example.couponcore.model.QCouponIssue;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CouponIssueRepository {

    private final JPQLQueryFactory jpqlQueryFactory;

    /**
     * &#064;query:  SELECT * FROM coupon_issue WHERE coupon_id = ? AND user_id = ? LIMIT 1
     */
    public Optional<CouponIssue> findFirstCouponIssue(long couponId, long userId) {
        CouponIssue couponIssue = jpqlQueryFactory
                .selectFrom(QCouponIssue.couponIssue)
                .where(QCouponIssue.couponIssue.couponId.eq(couponId))
                .where(QCouponIssue.couponIssue.userId.eq(userId))
                .fetchFirst();
        return Optional.ofNullable(couponIssue);

    }
}
