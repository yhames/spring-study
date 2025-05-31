package com.fastcampus.flow.execption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApplicationException extends RuntimeException {

    private HttpStatus status;
    private String code;
    private String message;

}

