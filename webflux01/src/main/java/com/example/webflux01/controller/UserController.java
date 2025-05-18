package com.example.webflux01.controller;

import com.example.webflux01.dto.user.UserCreateRequest;
import com.example.webflux01.dto.user.UserPostResponse;
import com.example.webflux01.dto.user.UserResponse;
import com.example.webflux01.dto.user.UserUpdateRequest;
import com.example.webflux01.entity.Post;
import com.example.webflux01.service.PostServiceV2;
import com.example.webflux01.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PostServiceV2 postService;

    // Create a new user
    @PostMapping("")
    public Mono<UserResponse> createUser(@RequestBody UserCreateRequest request) {
        return userService.create(request.getName(), request.getEmail())
                .map(UserResponse::of);
    }

    // Update an existing user
    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        return userService.update(id, request.getName(), request.getEmail())
                .map(user -> ResponseEntity.ok(UserResponse.of(user)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));   // 해당 유저가 없는 경우 404 NotFound
    }

    // Get all users
    @GetMapping("")
    public Flux<UserResponse> getAllUsers() {
        return userService.findAll()
                .map(UserResponse::of);
    }

    // Get a user by ID
    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(UserResponse.of(user)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    // Delete a user by ID with no content (204) response
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUserById(@PathVariable Long id) {
        return userService.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    // Delete a user by Name with no content (204) response
    @DeleteMapping("")
    public Mono<ResponseEntity<Void>> deleteUserById(@RequestParam String name) {
        return userService.deleteByName(name)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @GetMapping("/{id}/posts")
    public Flux<UserPostResponse> getUserPosts(@PathVariable Long id) {
        return postService.findAllByUserId(id)
                .map(UserPostResponse::of);
    }
}
