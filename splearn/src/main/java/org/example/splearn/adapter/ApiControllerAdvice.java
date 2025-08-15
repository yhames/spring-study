package org.example.splearn.adapter;

import org.example.splearn.domain.member.DuplicateEmailException;
import org.example.splearn.domain.member.DuplicateProfileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * ProblemDetail은 Spring 6.0부터 지원되는 HTTP API의 표준(RFC 9457) 오류 응답 형식입니다.
 */
@ControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({DuplicateEmailException.class, DuplicateProfileException.class})
    public ProblemDetail handleException(DuplicateEmailException ex) {
        return generateProfileDetail(HttpStatus.CONFLICT, ex);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception ex) {
        return generateProfileDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    private static ProblemDetail generateProfileDetail(HttpStatus status, Exception exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, exception.getMessage());
        problemDetail.setProperty("exception", exception.getClass().getSimpleName());
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }
}
