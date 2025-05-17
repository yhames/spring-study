package org.example;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class PublisherTest {

    Publisher publisher = new Publisher();

    @Test
    void startFlux() {
        StepVerifier.create(publisher.startFlux())
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete();
    }

    @Test
    void startFluxString() {
        StepVerifier.create(publisher.startFluxString())
                .expectNext("a", "b", "c", "d", "e", "f", "g", "h")
                .verifyComplete();
    }

    @Test
    void startMono() {
        StepVerifier.create(publisher.startMono())
                .expectNext(1)
                .verifyComplete();
    }

    @Test
    void startMonoEmpty() {
        StepVerifier.create(publisher.startMonoEmpty())
                .verifyComplete();
    }

    @Test
    void startMonoError() {
        StepVerifier.create(publisher.startMonoError())
                .expectError(Exception.class)
                .verify();
    }
}