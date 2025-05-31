package com.fastcampus.flow.controller;

import com.fastcampus.flow.dto.AllowUserResponse;
import com.fastcampus.flow.dto.AllowedUserResponse;
import com.fastcampus.flow.dto.RegisterUserResponse;
import com.fastcampus.flow.service.UserQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/queue")
@RequiredArgsConstructor
public class UserQueueController {

    private final UserQueueService userQueueService;

    @PostMapping("/register")
    public Mono<RegisterUserResponse> registerUser(@RequestParam(value = "queue", defaultValue = "default") String queue,
                                                   @RequestParam(value = "user_id") Long userId) {
        return userQueueService.registerWaitQueue(queue, userId)
                .map(RegisterUserResponse::new);
    }

    @PostMapping("/allow")
    public Mono<AllowUserResponse> allowUser(@RequestParam(value = "queue", defaultValue = "default") String queue,
                                             @RequestParam(value = "count") Long count) {
        return userQueueService.allowUser(queue, count)
                .map(allowed -> new AllowUserResponse(count, allowed));
    }

    @GetMapping("")
    public Mono<AllowedUserResponse> isAllowedUser(@RequestParam(value = "queue", defaultValue = "default") String queue,
                                                   @RequestParam(value = "user_id") Long userId) {
        return userQueueService.isAllowed(queue, userId)
                .map(AllowedUserResponse::new);
    }
}
