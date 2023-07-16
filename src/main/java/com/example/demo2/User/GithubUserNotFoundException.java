package com.example.demo2.User;

import org.springframework.http.HttpStatus;

public class GithubUserNotFoundException extends GithubApiException {
    public GithubUserNotFoundException(String requestUrl) {
        super(HttpStatus.NOT_FOUND, requestUrl);
    }
}
