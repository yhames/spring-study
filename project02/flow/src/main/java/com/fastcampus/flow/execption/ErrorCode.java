package com.fastcampus.flow.execption;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ErrorCode {

    QUEUE_ALREADY_REGISTERED_USER(HttpStatus.CONFLICT, "UQ-0001", "Already registered in queue");

    private final HttpStatus status;
    private final String code;
    private final String message;

    public ApplicationException build() {
        return new ApplicationException(status, code, message);
    }

    public ApplicationException build(Object... args) {
        return new ApplicationException(status, code, message.formatted(args));
    }
}
