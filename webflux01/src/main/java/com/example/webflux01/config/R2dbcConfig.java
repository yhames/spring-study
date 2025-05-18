package com.example.webflux01.config;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.core.DatabaseClient;

@Slf4j
@Configuration
@EnableR2dbcRepositories
@EnableR2dbcAuditing
@RequiredArgsConstructor
public class R2dbcConfig implements ApplicationListener<ApplicationReadyEvent> {

    private final DatabaseClient databaseClient;

    @Override
    public void onApplicationEvent(@Nonnull ApplicationReadyEvent event) {
        databaseClient.sql("SELECT 1").fetch().one().subscribe(
                success -> {
                    log.info("Initialize r2dbc database connection");
                }, error -> {
                    log.error("Failed to initialize r2dbc database connection");
                    SpringApplication.exit(event.getApplicationContext(), () -> -110);
                });
    }
}
