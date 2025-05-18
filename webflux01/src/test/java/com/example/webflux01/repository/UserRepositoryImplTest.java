package com.example.webflux01.repository;

import com.example.webflux01.entity.User;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryImplTest {

    private final UserRepository userRepository = new UserRepositoryImpl();

    @Test
    void save() {
        User user = User.builder().name("test").email("test@test.com").build();
        StepVerifier.create(userRepository.save(user))
                .assertNext(u -> {
                    assertEquals("test", u.getName());
                    assertEquals("test@test.com", u.getEmail());
                })
                .verifyComplete();
    }

    @Test
    void findAll() {
        User user1 = User.builder().name("test1").email("test1@test.com").build();
        User user2 = User.builder().name("test2").email("test2@test.com").build();
        User user3 = User.builder().name("test3").email("test3@test.com").build();

        userRepository.save(user1).block();
        userRepository.save(user2).block();
        userRepository.save(user3).block();

        StepVerifier.create(userRepository.findAll())
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void findById() {
        User user1 = User.builder().name("test1").email("test1@test.com").build();
        User user2 = User.builder().name("test2").email("test2@test.com").build();

        userRepository.save(user1).block();
        userRepository.save(user2).block();

        StepVerifier.create(userRepository.findById(2L))
                .assertNext(user -> {
                    assertEquals("test2", user.getName());
                    assertEquals("test2@test.com", user.getEmail());
                })
                .verifyComplete();
    }

    @Test
    void deleteById() {
        User user1 = User.builder().name("test1").email("test1@test.com").build();
        User user2 = User.builder().name("test2").email("test2@test.com").build();

        userRepository.save(user1).block();
        userRepository.save(user2).block();

        StepVerifier.create(userRepository.deleteById(2L))
                .expectNextCount(1)
                .verifyComplete();
    }
}