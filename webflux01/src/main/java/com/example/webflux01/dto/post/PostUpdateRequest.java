package com.example.webflux01.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PostUpdateRequest {
    private Long userId;
    private String title;
    private String content;
}
