package com.example.webflux01.repository;

import com.example.webflux01.entity.User;
import org.springframework.data.domain.Limit;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface R2dbcUserRepository extends ReactiveCrudRepository<User, Long> {

    Flux<User> findByName(String name);

    Flux<User> findByNameOrderByIdDesc(String name, Limit limit);

    @Modifying  // for update/delete
    @Query("DELETE FROM users WHERE name = :name")
    Mono<Void> deleteByName(String name);
}
