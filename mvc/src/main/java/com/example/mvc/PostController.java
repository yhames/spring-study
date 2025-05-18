package com.example.mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PostController {

    @GetMapping("/posts/{id}")
    public Map<String, String> getPostById(@PathVariable Long id) throws InterruptedException {
        Thread.sleep(300);
        if (id > 10) {
            throw new IllegalArgumentException("id must be less than or equal to 10");
        }
        return Map.of("id", id.toString(), "content", String.format("This is the post %s", id));
    }
}
