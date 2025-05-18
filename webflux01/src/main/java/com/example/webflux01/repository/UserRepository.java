package com.example.webflux01.repository;

import com.example.webflux01.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {

    // create, update

    Mono<User> save(User user);

    // read

    Flux<User> findAll();

    Mono<User> findById(Long id);

    // delete

    Mono<Integer> deleteById(Long id);
}
