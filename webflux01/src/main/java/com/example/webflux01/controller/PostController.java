package com.example.webflux01.controller;

import com.example.webflux01.dto.post.PostResponse;
import com.example.webflux01.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public Mono<PostResponse> getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @GetMapping("/search")
    public Flux<PostResponse> getMultiplePostContents(@RequestParam(name = "ids") List<Long> idList) {
        return postService.getMultiplePostContents(idList);
    }

    @GetMapping("/search/parallel")
    public Flux<PostResponse> getMultiplePostContentsParallel(@RequestParam(name = "ids") List<Long> idList) {
        return postService.getParallelMultiplePostContents(idList);
    }
}
