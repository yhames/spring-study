package org.example;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

public class Publisher {

    Flux<Integer> startFlux() {
        return Flux.range(1, 5).log();
    }

    Flux<String> startFluxString() {
        return Flux.fromIterable(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h"));
    }

    Mono<Integer> startMono() {
        return Mono.just(1).log();
    }

    Mono<?> startMonoEmpty() {
        return Mono.empty().log();
    }

    Mono<?> startMonoError() {
        return Mono.error(new Exception("Error")).log();
    }
}
