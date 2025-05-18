package com.example.webflux01.repository;

import com.example.webflux01.entity.User;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final Map<Long, User> users = new ConcurrentHashMap<>();

    private final AtomicLong seq = new AtomicLong(1L);

    @Override
    public Mono<User> save(User user) {
        var now = LocalDateTime.now();
        if (user.getId() == null) {
            user.setId(seq.getAndIncrement());
            user.setCreatedAt(now);
        }
        user.setUpdatedAt(now);
        users.put(user.getId(), user);
        return Mono.just(user);
    }

    @Override
    public Flux<User> findAll() {
        return Flux.fromIterable(users.values());
    }

    @Override
    public Mono<User> findById(Long id) {
        return Mono.just(users.getOrDefault(id, null));
    }

    @Override
    public Mono<Integer> deleteById(Long id) {
        User user = users.getOrDefault(id, null);
        if (user == null) {
            return Mono.just(0);
        }
        users.remove(id, user);
        return Mono.just(1);
    }
}
