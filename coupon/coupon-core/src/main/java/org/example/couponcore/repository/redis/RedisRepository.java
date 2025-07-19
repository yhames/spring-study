package org.example.couponcore.repository.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.couponcore.execption.CouponIssueException;
import org.example.couponcore.execption.ErrorCode;
import org.example.couponcore.repository.redis.dto.CouponIssueRequest;
import org.example.couponcore.repository.redis.dto.CouponIssueRequestCode;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.example.couponcore.repository.redis.dto.CouponIssueRequestCode.checkRequestResult;
import static org.example.couponcore.utils.CouponRedisUtils.getIssueRequestKey;
import static org.example.couponcore.utils.CouponRedisUtils.getIssueRequestQueueKey;

@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final RedisScript<String> issueScript = issueRequestScript();
    private final String issueRequestQueueKey = getIssueRequestQueueKey();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Boolean zAdd(String key, String value, double score) {
        return redisTemplate.opsForZSet().addIfAbsent(key, value, score);
    }

    public Long sAdd(String key, String value) {
        return redisTemplate.opsForSet().add(key, value);
    }

    public Long sCard(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    public Boolean sIsMember(String key, String value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    public Long rPush(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    public void issueRequest(long couponId, long userId, int totalQuantity) {
        CouponIssueRequest couponIssueRequest = new CouponIssueRequest(couponId, userId);
        try {
            String code = redisTemplate.execute(
                    issueScript,
                    List.of(getIssueRequestKey(couponId), issueRequestQueueKey),
                    String.valueOf(userId),
                    String.valueOf(totalQuantity),
                    objectMapper.writeValueAsString(couponIssueRequest)
            );
            checkRequestResult(CouponIssueRequestCode.of(code));
        } catch (JsonProcessingException e) {
            throw new CouponIssueException(ErrorCode.FAILED_COUPON_ISSUE_REQUEST,
                    "input: %s".formatted(couponIssueRequest));
        }
    }

    private RedisScript<String> issueRequestScript() {
        String script = """
                if redis.call('SISMEMBER', KEYS[1], ARGV[1]) == 1 then
                    return '2'
                end
                
                if tonumber(ARGV[2]) <= redis.call('SCARD', KEYS[1]) then
                    return '3'
                end
                
                redis.call('SADD', KEYS[1], ARGV[1])
                redis.call('RPUSH', KEYS[2], ARGV[3])
                return '1'
                """;
        return RedisScript.of(script, String.class);
    }
}
