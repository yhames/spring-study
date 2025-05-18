package com.example.webflux01;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class SimpleHandler {

    public Mono<ServerResponse> getString(ServerRequest request) {
        return ServerResponse.ok().bodyValue("Hello, Functional Endpoint!");
    }
}
