package com.example.webflux01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SimpleController {

    @GetMapping("/sample/hello")
    public Mono<String> getHello() {
        return Mono.just("Hello rest controller with webflux!");
    }
}
