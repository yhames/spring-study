package com.example.webflux01.service;

import com.example.webflux01.client.PostClient;
import com.example.webflux01.dto.post.PostResponse;
import com.example.webflux01.entity.Post;
import com.example.webflux01.repository.R2dbcPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceV2 {

    private final R2dbcPostRepository postRepository;

    public Mono<Post> create(Long userId, String title, String content) {
        Post newPost = Post.builder()
                .userId(userId)
                .title(title)
                .content(content)
                .build();
        return postRepository.save(newPost);
    }

    public Mono<Post> update(Long id, Long userId, String title, String content) {
        return postRepository.findById(id)
                .flatMap(post -> {
                    post.setUserId(userId);
                    post.setTitle(title);
                    post.setContent(content);
                    return postRepository.save(post);
                });
    }

    public Flux<Post> findAll() {
        return postRepository.findAll();
    }

    public Mono<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public Mono<Void> deleteById(Long id) {
        return postRepository.deleteById(id);
    }

    public Flux<Post> findAllByUserId(Long id) {
        return postRepository.findAllByUserId(id);
    }
}
