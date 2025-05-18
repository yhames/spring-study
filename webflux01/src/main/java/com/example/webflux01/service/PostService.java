package com.example.webflux01.service;

import com.example.webflux01.client.PostClient;
import com.example.webflux01.dto.post.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostClient postClient;

    public Mono<PostResponse> getPostById(Long id) {
        return postClient.getPostById(id)
                .onErrorResume(error -> Mono.just(PostResponse.builder()
                        .id(id.toString())
                        .content("Fallback data %d".formatted(id))
                        .build()));
    }

    public Flux<PostResponse> getMultiplePostContents(List<Long> idList) {
        return Flux.fromIterable(idList)
                .flatMap(this::getPostById)
                .log();
    }

    public Flux<PostResponse> getParallelMultiplePostContents(List<Long> idList) {
        return Flux.fromIterable(idList)
                .parallel()
                .runOn(Schedulers.parallel())
                .log()
                .flatMap(this::getPostById)
                .sequential();
    }
}
