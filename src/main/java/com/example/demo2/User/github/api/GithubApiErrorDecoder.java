package com.example.demo2.User.github.api;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class GithubApiErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        String requestUrl = response.request().url();
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());

        if (responseStatus.isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return new GithubUserNotFoundException(requestUrl);
        } else {
            return new GithubApiException(responseStatus, requestUrl);
        }
    }
}