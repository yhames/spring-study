package org.example;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class Operator2Test {

    private final Operator2 operator2 = new Operator2();

    @Test
    void fluxConcatMap() {
        StepVerifier.create(operator2.fluxConcatMap())
                .expectNextCount(100)
                .verifyComplete();
    }

    @Test
    void fluxFlatMapMany() {
        StepVerifier.create(operator2.fluxFlatMapMany())
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    void fluxDefaultIfEmpty() {
        StepVerifier.create(operator2.fluxDefaultIfEmpty1())
                .expectNext(42)
                .verifyComplete();
    }

    @Test
    void fluxSwitchIfEmpty() {
        StepVerifier.create(operator2.fluxSwitchIfEmpty1())
                .expectNext(42 * 2)
                .verifyComplete();
    }

    @Test
    void fluxSwitchIfEmpty2() {
        StepVerifier.create(operator2.fluxSwitchIfEmpty2())
                .expectError()
                .verify();
    }

    @Test
    void fluxMerge() {
        StepVerifier.create(operator2.fluxMerge())
                .expectNext("1", "2", "3", "4")
                .verifyComplete();
    }

    @Test
    void monoMerge() {
        StepVerifier.create(operator2.monoMerge())
                .expectNext("1", "2", "3")
                .verifyComplete();
    }

    @Test
    void fluxZip() {
        StepVerifier.create(operator2.fluxZip())
                .expectNext("a-d", "b-e", "c-f")
                .verifyComplete();
    }

    @Test
    void monoZip() {
        StepVerifier.create(operator2.monoZip())
                .expectNext(1 + 2 + 3)
                .verifyComplete();
    }
}