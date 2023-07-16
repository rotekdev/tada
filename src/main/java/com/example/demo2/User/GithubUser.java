package com.example.demo2.User;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.Instant;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GithubUser(Long id, String login, String avatarUrl, String type, String name, Long publicRepos,
                         Long followers, Instant createdAt) {
}
