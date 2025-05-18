package com.example.webflux01.client;

import com.example.webflux01.dto.post.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class PostClient {

    private static final String BASE_URL = "http://localhost:8090";
    private final WebClient webClient;

    public Mono<PostResponse> getPostById(Long id) {
        URI uri = UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/posts/{id}")
                .buildAndExpand(id)
                .toUri();
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(PostResponse.class);
    }
}
