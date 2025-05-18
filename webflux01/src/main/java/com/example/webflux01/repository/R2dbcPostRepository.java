package com.example.webflux01.repository;

import com.example.webflux01.entity.Post;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface R2dbcPostRepository extends ReactiveCrudRepository<Post, Long>, R2dbcCustomPostRepository {

}
