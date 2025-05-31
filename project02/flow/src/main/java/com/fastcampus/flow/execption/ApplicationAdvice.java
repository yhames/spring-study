package com.fastcampus.flow.execption;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class ApplicationAdvice {

    @ExceptionHandler(ApplicationException.class)
    Mono<ResponseEntity<ServerExceptionResponse>> applicationExceptionHandler(ApplicationException e) {
        ResponseEntity<ServerExceptionResponse> response = ResponseEntity
                .status(e.getStatus())
                .body(new ServerExceptionResponse(e.getCode(), e.getMessage()));
        return Mono.just(response);
    }
}
