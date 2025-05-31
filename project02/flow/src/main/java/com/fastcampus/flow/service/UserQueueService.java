package com.fastcampus.flow.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.time.Instant;

import static com.fastcampus.flow.execption.ErrorCode.QUEUE_ALREADY_REGISTERED_USER;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserQueueService {

    private final ReactiveRedisTemplate<String, String> redisTemplate;

    private static final String USER_QUEUE_WAIT_KEY = "user:queue:%s:wait";
    private static final String USER_QUEUE_PROCEED_KEY = "user:queue:%s:proceed";

    private static final String USER_QUEUE_WAIT_KEY_FOR_SCAN = "user:queue:*:wait";

    @Value("${scheduler.enabled}")
    private Boolean scheduled = false;

    public Mono<Long> registerWaitQueue(final String queue, final Long userId) {
        long now = Instant.now().getEpochSecond();
        String key = USER_QUEUE_WAIT_KEY.formatted(queue);
        return redisTemplate.opsForZSet().add(key, userId.toString(), now)
                .filter(i -> i) // true if the user was added successfully
                .switchIfEmpty(Mono.error(QUEUE_ALREADY_REGISTERED_USER.build()))  // If the user is already registered, throw an error
                .flatMap(i -> redisTemplate.opsForZSet().rank(key, userId.toString())) // Get the rank of the user in the queue
                .map(i -> i + 1); // Return the rank (1-based index) of the user in the queue
    }

    public Mono<Long> allowUser(final String queue, final Long count) {
        return redisTemplate.opsForZSet().popMin(USER_QUEUE_WAIT_KEY.formatted(queue), count)
                .flatMap(member -> redisTemplate.opsForZSet()
                        .add(USER_QUEUE_PROCEED_KEY.formatted(queue), member.getValue(), Instant.now().getEpochSecond()))
                .count();
    }

    public Mono<Boolean> isAllowed(final String queue, final Long userId) {
        return redisTemplate.opsForZSet().rank(USER_QUEUE_PROCEED_KEY.formatted(queue), userId.toString())
                .defaultIfEmpty(-1L)
                .map(rank -> rank >= 0);  // If the rank is -1, the user is not in the queue
    }

    public Mono<Long> getRank(final String queue, final Long userId) {
        return redisTemplate.opsForZSet().rank(USER_QUEUE_WAIT_KEY.formatted(queue), userId.toString())
                .defaultIfEmpty(-1L)
                .map(rank -> rank >= 0 ? rank + 1 : rank);

    }

    @Scheduled(initialDelay = 5000, fixedDelay = 3000)   // After 5 seconds, repeat every 3 seconds
    public void scheduledAllowUser() {
        if (!scheduled) {
            log.info("Scheduler is disabled, skipping UserQueueService.scheduledAllowUser");
            return;
        }

        var maxAllowUserCount = 10L;
        redisTemplate.scan(ScanOptions.scanOptions().match(USER_QUEUE_WAIT_KEY_FOR_SCAN).count(100).build())
                .map(key -> key.split(":")[2])
                .flatMap(queue -> allowUser(queue, maxAllowUserCount)
                        .map(allowed -> Tuples.of(queue, allowed)))
                .doOnNext(tuple -> log.info("Tried allow {} and allowed {} users of {} queue", maxAllowUserCount, tuple.getT2(), tuple.getT1()))
                .subscribe();
    }
}
