package com.fastcampus.flow.service;

import com.fastcampus.flow.EmbeddedRedis;
import com.fastcampus.flow.execption.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.ReactiveRedisConnection;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

@SpringBootTest
@Import(EmbeddedRedis.class)
@ActiveProfiles("test")
class UserQueueServiceTest {

    @Autowired
    private UserQueueService userQueueService;

    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;

    @BeforeEach
    void setUp() {
        ReactiveRedisConnection conn = redisTemplate.getConnectionFactory().getReactiveConnection();
        conn.serverCommands().flushAll().subscribe();
    }

    @Test
    void registerWaitQueue() {
        StepVerifier.create(userQueueService.registerWaitQueue("default", 100L))
                .expectNext(1L)
                .verifyComplete();
        StepVerifier.create(userQueueService.registerWaitQueue("default", 101L))
                .expectNext(2L)
                .verifyComplete();
        StepVerifier.create(userQueueService.registerWaitQueue("default", 102L))
                .expectNext(3L)
                .verifyComplete();
    }

    @Test
    void alreadyRegisterWaitQueue() {
        StepVerifier.create(userQueueService.registerWaitQueue("default", 100L))
                .expectNext(1L)
                .verifyComplete();
        StepVerifier.create(userQueueService.registerWaitQueue("default", 100L))
                .expectError(ApplicationException.class)
                .verify();
    }

    @Test
    void emptyAllowUser() {
        StepVerifier.create(userQueueService.allowUser("default", 3L))
                .expectNext(0L)
                .verifyComplete();
    }

    @Test
    void allowUser() {
        StepVerifier.create(userQueueService.registerWaitQueue("default", 100L)
                        .then(userQueueService.registerWaitQueue("default", 101L))
                        .then(userQueueService.registerWaitQueue("default", 102L))
                        .then(userQueueService.allowUser("default", 2L)))
                .expectNext(2L)
                .verifyComplete();
    }

    @Test
    void allowUserWithAllowCountGreaterThanUserCount() {
        StepVerifier.create(userQueueService.registerWaitQueue("default", 100L)
                        .then(userQueueService.registerWaitQueue("default", 101L))
                        .then(userQueueService.registerWaitQueue("default", 102L))
                        .then(userQueueService.allowUser("default", 5L)))
                .expectNext(3L)
                .verifyComplete();
    }

    @Test
    void allowUserAfterRegisterWaitQueue() {
        StepVerifier.create(userQueueService.registerWaitQueue("default", 100L)
                        .then(userQueueService.registerWaitQueue("default", 101L))
                        .then(userQueueService.registerWaitQueue("default", 102L))
                        .then(userQueueService.allowUser("default", 3L))
                        .then(userQueueService.registerWaitQueue("default", 200L)))
                .expectNext(1L)
                .verifyComplete();
    }

    @Test
    void isNotAllowed() {
        StepVerifier.create(userQueueService.isAllowed("default", 100L))
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void isNotAllowed2() {
        StepVerifier.create(userQueueService.registerWaitQueue("default", 100L)
                        .then(userQueueService.allowUser("default", 100L))
                        .then(userQueueService.isAllowed("default", 101L)))
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void isAllowed() {
        StepVerifier.create(userQueueService.registerWaitQueue("default", 100L)
                        .then(userQueueService.allowUser("default", 3L))
                        .then(userQueueService.isAllowed("default", 100L)))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void getRank() {
        StepVerifier.create(userQueueService.registerWaitQueue("default", 100L)
                        .then(userQueueService.getRank("default", 100L)))
                .expectNext(1L)
                .verifyComplete();
        StepVerifier.create(userQueueService.registerWaitQueue("default", 101L)
                        .then(userQueueService.getRank("default", 101L)))
                .expectNext(2L)
                .verifyComplete();
        StepVerifier.create(userQueueService.getRank("default", 100L))
                .expectNext(1L)
                .verifyComplete();
    }

    @Test
    void emptyRank() {
        StepVerifier.create(userQueueService.getRank("default", 100L))
                .expectNext(-1L)
                .verifyComplete();
    }

    @Test
    void isAllowedByToken() {
        StepVerifier.create(userQueueService.isAllowedByToken("default", 100L, ""))
                .expectNext(false)
                .verifyComplete();
        String sha256 = "d333a5d4eb24f3f5cdd767d79b8c01aad3cd73d3537c70dec430455d37afe4b8";
        StepVerifier.create(userQueueService.isAllowedByToken("default", 100L, sha256))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void generateToken() {
        String sha256 = "d333a5d4eb24f3f5cdd767d79b8c01aad3cd73d3537c70dec430455d37afe4b8";
        StepVerifier.create(userQueueService.generateToken("default", 100L))
                .expectNext(sha256) // Default token for empty queue
                .verifyComplete();
    }
}