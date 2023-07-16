package com.example.demo2.User.github.api;

import org.springframework.http.HttpStatus;

public class GithubApiException extends Exception {
    public GithubApiException(HttpStatus responseStatus, String requestUrl) {
        super("Github returned with " + responseStatus + "on url " + requestUrl);
    }
}
