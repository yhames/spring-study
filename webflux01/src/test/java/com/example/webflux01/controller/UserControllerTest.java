package com.example.webflux01.controller;

import com.example.webflux01.dto.user.UserCreateRequest;
import com.example.webflux01.dto.user.UserResponse;
import com.example.webflux01.dto.user.UserUpdateRequest;
import com.example.webflux01.entity.User;
import com.example.webflux01.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebFluxTest(UserController.class)
@AutoConfigureWebTestClient
class UserControllerTest {

    @Autowired
    private WebTestClient client;

    @MockitoBean
    private UserService userService;

    @Test
    void createUser() {
        var name = "John Doe";
        var email = "johndoe@webflux.com";
        when(userService.create(any(), any())).thenReturn(Mono.just(
                User.builder()
                        .id(1L)
                        .name(name)
                        .email(email)
                        .build()));
        UserCreateRequest request = UserCreateRequest.builder()
                .name(name)
                .email(email)
                .build();

        client.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(UserResponse.class)
                .value(res -> {
                    assertEquals(name, res.getName());
                    assertEquals(email, res.getEmail());
                });
    }

    @Test
    void updateUser() {
        var name = "John Doe";
        var email = "johndoe1@webflux.com";
        when(userService.update(eq(1L), any(), any())).thenReturn(Mono.just(
                User.builder()
                        .id(1L)
                        .name(name)
                        .email(email)
                        .updatedAt(LocalDateTime.now())
                        .createdAt(LocalDateTime.now())
                        .build()));
        UserUpdateRequest request = UserUpdateRequest.builder()
                .name(name)
                .email(email)
                .build();

        client.put().uri("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponse.class)
                .value(res -> {
                    assertEquals(name, res.getName());
                    assertEquals(email, res.getEmail());
                });
    }

    @Test
    void getAllUsers() {
        when(userService.findAll()).thenReturn(
                Flux.just(
                        User.builder().id(1L).name("John Doe 1").email("johndoe1@webflux.com").build(),
                        User.builder().id(1L).name("John Doe 2").email("johndoe2@webflux.com").build(),
                        User.builder().id(1L).name("John Doe 3").email("johndoe3@webflux.com").build()
                ));

        client.get().uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserResponse.class)
                .hasSize(3);
    }

    @Test
    void getUserById() {
        var name = "John Doe";
        var email = "johndoe1@webflux.com";
        when(userService.findById(1L)).thenReturn(Mono.just(User.builder()
                .id(1L)
                .name(name)
                .email(email)
                .build()));

        client.get().uri("/users/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponse.class)
                .value(res -> {
                    assertEquals(name, res.getName());
                    assertEquals(email, res.getEmail());
                });
    }

    @Test
    void getUserByIdNotFound() {
        when(userService.findById(1L)).thenReturn(Mono.empty());

        client.get().uri("/users/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void deleteUserById() {
        when(userService.deleteById(1L)).thenReturn(Mono.empty());

        client.delete().uri("/users/1")
                .exchange()
                .expectStatus().isNoContent();
    }
}