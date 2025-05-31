package com.fastcampus.flow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

import static com.fastcampus.flow.execption.ErrorCode.QUEUE_ALREADY_REGISTERED_USER;

@Service
@RequiredArgsConstructor
public class UserQueueService {

    private final ReactiveRedisTemplate<String, String> redisTemplate;

    private static final String USER_QUEUE_WAIT_KEY = "user:queue:%s:wait";

    public Mono<Long> registerWaitQueue(final String queue, final Long userId) {
        long now = Instant.now().getEpochSecond();
        String key = USER_QUEUE_WAIT_KEY.formatted(queue);
        return redisTemplate.opsForZSet().add(key, userId.toString(), now)
                .filter(i -> i) // true if the user was added successfully
                .switchIfEmpty(Mono.error(QUEUE_ALREADY_REGISTERED_USER.build()))  // If the user is already registered, throw an error
                .flatMap(i -> redisTemplate.opsForZSet().rank(key, userId.toString())) // Get the rank of the user in the queue
                .map(i -> i + 1); // Return the rank (1-based index) of the user in the queue
    }
}
