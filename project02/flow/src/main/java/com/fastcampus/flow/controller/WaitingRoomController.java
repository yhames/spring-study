package com.fastcampus.flow.controller;

import com.fastcampus.flow.service.UserQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class WaitingRoomController {

    private final UserQueueService userQueueService;

    @GetMapping("/waiting-room")
    public Mono<Rendering> waitingRoomPage(@RequestParam(name = "queue", defaultValue = "default") String queue,
                                           @RequestParam(name = "user_id") Long userId,
                                           @RequestParam(name = "redirect_url") String redirectUrl,
                                           ServerWebExchange exchange) {
        HttpCookie cookie = exchange.getRequest().getCookies().getFirst("user-queue-%s-%d".formatted(queue, userId));
        String token = cookie != null ? cookie.getValue() : "";
        return userQueueService.isAllowedByToken(queue, userId, token)
                .filter(allowed -> allowed) // Check if the user is allowed to proceed;
                .flatMap(allowed -> Mono.just(Rendering.redirectTo(redirectUrl).build()))   // If allowed, redirect to the specified URL
                .switchIfEmpty(
                        userQueueService.registerWaitQueue(queue, userId)    // Register the user in the wait queue
                                .onErrorResume(ex -> userQueueService.getRank(queue, userId))   // If registration fails (e.g., user already registered), get the user's rank
                                .map(rank -> Rendering.view("waiting-room") // Render the waiting room view
                                        .modelAttribute("number", rank)
                                        .modelAttribute("userId", userId)
                                        .modelAttribute("queue", queue)
                                        .build())   // Return the waiting room view with the user's rank and other attributes
                );
    }
}
