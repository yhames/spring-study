package com.example.webflux01.repository;

import com.example.webflux01.entity.Post;
import reactor.core.publisher.Flux;

public interface R2dbcCustomPostRepository {

    Flux<Post> findAllByUserId(Long userId);
}
