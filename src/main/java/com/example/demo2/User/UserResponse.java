package com.example.demo2.User;

import java.time.Instant;

public record UserResponse(Long id, String login, String name, String type, String avatarUrl, Instant createdAt,
                           String calculations) {
}
