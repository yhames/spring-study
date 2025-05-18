package com.example.webflux01.controller;

import com.example.webflux01.dto.post.PostCreateRequest;
import com.example.webflux01.dto.post.PostResponseV2;
import com.example.webflux01.dto.post.PostUpdateRequest;
import com.example.webflux01.service.PostServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/posts")
public class PostControllerV2 {

    private final PostServiceV2 postService;

    @PostMapping("/posts")
    public Mono<PostResponseV2> getPostById(@RequestBody PostCreateRequest request) {
        return postService.create(request.getUserId(), request.getTitle(), request.getContent())
                .map(PostResponseV2::of);
    }

    @PutMapping("/posts/{id}")
    public Mono<ResponseEntity<PostResponseV2>> updatePost(@PathVariable Long id, @RequestBody PostUpdateRequest request) {
        return postService.update(id, request.getUserId(), request.getTitle(), request.getContent())
                .map(post -> ResponseEntity.ok(PostResponseV2.of(post)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/posts")
    public Flux<PostResponseV2> getPosts() {
        return postService.findAll()
                .map(PostResponseV2::of);
    }

    @GetMapping("/posts/{id}")
    public Mono<ResponseEntity<PostResponseV2>> getPosts(@PathVariable Long id) {
        return postService.findById(id)
                .map(post -> ResponseEntity.ok(PostResponseV2.of(post)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/posts/{id}")
    public Mono<ResponseEntity<Void>> deletePostById(@PathVariable Long id) {
        return postService.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

}
