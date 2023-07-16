package com.example.demo2.User;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class UserControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = GithubApiException.class)
    protected ResponseEntity<Object> handleGithubApiError(GithubApiException ex) {
        log.error("Exception handled", ex);
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(value = GithubUserNotFoundException.class)
    protected ResponseEntity<Object> handleGithubApiNotFoundError(GithubApiException ex) {
        log.error("Exception handled", ex);
        return ResponseEntity.notFound().build();
    }
}
