package org.example.couponcore.utils;

public class CouponRedisUtils {

    public static String getIssueRequestKey(long couponId) {
        return String.format("issue.request.couponId=%s", couponId);
    }

    public static String getIssueRequestQueueKey() {
        return "issue.request";
    }
}
