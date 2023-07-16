package com.example.demo2.User;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class GithubApiClientConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new GithubApiErrorDecoder();
    }
}
