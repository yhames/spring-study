package org.example;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class Operator1 {

    // map

    public Flux<Integer> fluxMap() {
        return Flux.range(1, 5)
                .map(i -> i * 2)
                .log();
    }


    // filter

    public Flux<Integer> fluxFilter() {
        return Flux.range(1, 10)
                .filter(i -> i % 2 == 0)
                .log();
    }

    // take

    public Flux<Integer> fluxTake() {
        return Flux.range(1, 10)
                .filter(i -> i % 2 == 0)
                .take(3)
                .log();
    }

    // flatMap

    public Flux<Integer> fluxFlatMap() {
        return Flux.range(1, 10)
                .flatMap(i -> Flux.range(i * 10, 10)
                        .delayElements(Duration.ofMillis(100)))
                .log();
    }

    public Flux<Integer> fluxFlatMap2() {
        return Flux.range(1, 9)
                .flatMap(i -> Flux.range(1, 9)
                        .map(j -> {
                            var sum = i * j;
                            System.out.printf("%d * %d = %d\n", i, j, sum);
                            return sum;
                        }))
                .log();
    }
}
