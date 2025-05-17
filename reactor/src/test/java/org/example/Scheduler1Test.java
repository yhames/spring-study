package org.example;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class Scheduler1Test {

    private final Scheduler1 scheduler = new Scheduler1();

    @Test
    void fluxMapWithSubscribeOn() {
        StepVerifier.create(scheduler.fluxMapWithSubscribeOn())
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    void fluxMapWithPublishOn() {
        StepVerifier.create(scheduler.fluxMapWithPublishOn())
                .expectNextCount(10)
                .verifyComplete();
    }
}