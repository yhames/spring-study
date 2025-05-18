package com.example.webflux01.service;

import com.example.webflux01.repository.R2dbcUserRepository;
import com.example.webflux01.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserService {

    //    private final UserRepository userRepository;
    private final R2dbcUserRepository userRepository;
    private final ReactiveRedisTemplate<String, User> redisTemplate;

    public Mono<User> create(String name, String email) {
        User newUser = User.builder().name(name).email(email).build();
        return userRepository.save(newUser);
    }

    public Mono<User> update(Long id, String name, String email) {
        return userRepository.findById(id)
                .flatMap(user -> {
                    user.setName(name);
                    user.setEmail(email);
                    return userRepository.save(user);
                })
                .flatMap(user -> redisTemplate.unlink(getUserCacheKey(id))  // delete cache
                        .then(Mono.just(user))
                );
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> findById(Long id) {
        return redisTemplate.opsForValue()  // get cache
                .get(getUserCacheKey(id))
                .switchIfEmpty(userRepository.findById(id)  // if cache miss
                        .flatMap(user -> redisTemplate.opsForValue()    // set cache
                                .set(getUserCacheKey(id), user, Duration.ofSeconds(30))
                                .then(Mono.just(user)))
                );
    }

    public Mono<Void> deleteById(Long id) {
        return userRepository.deleteById(id)
                .then(redisTemplate.unlink(getUserCacheKey(id)))    // delete cache
                .then(Mono.empty());
    }

    public Mono<Void> deleteByName(String name) {
        return userRepository.deleteByName(name);
    }

    private String getUserCacheKey(Long id) {
        return "user:%d".formatted(id);
    }
}
