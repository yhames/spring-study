package com.example.webflux01.repository;

import com.example.webflux01.entity.Post;
import com.example.webflux01.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class R2dbcCustomPostRepositoryImpl implements R2dbcCustomPostRepository {

    private final DatabaseClient databaseClient;

    public Flux<Post> findAllByUserId(Long userId) {
        var sql = """
                SELECT p.id AS post_id,
                       p.title AS post_title,
                       p.content AS post_content,
                       p.created_at AS post_created_at,
                       p.updated_at AS post_updated_at,
                       u.id AS user_id,
                       u.name AS user_name,
                       u.email AS user_email,
                       u.created_at AS user_created_at,
                       u.updated_at AS user_updated_at
                FROM posts p
                LEFT JOIN users u ON p.user_id = u.id
                WHERE p.user_id = :userId
                """;
        return databaseClient.sql(sql)
                .bind("userId", userId)
                .fetch()
                .all()
                .map(row -> Post.builder()
                        .id((Long) row.get("post_id"))
                        .userId((Long) row.get("user_id"))
                        .title((String) row.get("post_title"))
                        .content((String) row.get("post_content"))
                        .createdAt((LocalDateTime) row.get("post_created_at"))
                        .updatedAt((LocalDateTime) row.get("post_updated_at"))
                        .user(User.builder()
                                .id((Long) row.get("user_id"))
                                .name((String) row.get("user_name"))
                                .email((String) row.get("user_email"))
                                .createdAt((LocalDateTime) row.get("user_created_at"))
                                .updatedAt((LocalDateTime) row.get("user_updated_at"))
                                .build())
                        .build());
    }
}
