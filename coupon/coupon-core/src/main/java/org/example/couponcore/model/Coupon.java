package org.example.couponcore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.couponcore.execption.CouponIssueException;

import java.time.LocalDateTime;

import static org.example.couponcore.execption.ErrorCode.INVALID_COUPON_ISSUE_DATE;
import static org.example.couponcore.execption.ErrorCode.INVALID_COUPON_ISSUE_QUANTITY;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coupon")
public class Coupon extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "coupon_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponType couponType;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @Column(name = "issued_quantity", nullable = false)
    private int issuedQuantity;

    @Column(name = "discount_amount", nullable = false)
    private int discountAmount;

    @Column(name = "min_available_amount", nullable = false)
    private int minAvailableAmount;

    @Column(name = "date_issue_start", nullable = false)
    private LocalDateTime dateIssueStart;

    @Column(name = "date_issue_end", nullable = false)
    private LocalDateTime dateIssueEnd;

    public boolean availableIssueQuantity() {
        if (totalQuantity == null) {
            return true; // No limit on total quantity, so always available
        }
        return totalQuantity > issuedQuantity;
    }

    public boolean availableIssueDate() {
        LocalDateTime now = LocalDateTime.now();
        return dateIssueStart.isBefore(now) && dateIssueEnd.isAfter(now);
    }

    public boolean isIssueCompleted() {
        LocalDateTime now = LocalDateTime.now();
        return dateIssueEnd.isBefore(now) || !availableIssueQuantity();
    }

    public void issue() {
        if (!availableIssueQuantity()) {
            throw new CouponIssueException(INVALID_COUPON_ISSUE_QUANTITY,
                    "발급 가능한 수량을 초과합니다. total: %d, issued: %d"
                            .formatted(totalQuantity, issuedQuantity));
        }
        if (!availableIssueDate()) {
            throw new CouponIssueException(INVALID_COUPON_ISSUE_DATE,
                    "발급 기간이 아닙니다. request: %s, issueStart: %s, issueEnd: %s"
                            .formatted(LocalDateTime.now(), dateIssueStart, dateIssueEnd));
        }
        issuedQuantity++;
    }
}
