package com.example.demo2.User;

import com.example.demo2.User.github.api.Calculator;
import com.example.demo2.User.github.api.GithubUser;
import com.example.demo2.User.github.api.GithubUserApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final GithubUserApiClient githubUserApiClient;

    public UserResponse getUser(String login) {
        userRepository.upsertIncrementCount(login);
        GithubUser githubUser = githubUserApiClient.getUser(login);
        return toUserResponse(githubUser);
    }

    private UserResponse toUserResponse(GithubUser githubUser) {
        return new UserResponse(githubUser.id(),
                githubUser.login(),
                githubUser.name(),
                githubUser.type(),
                githubUser.avatarUrl(),
                githubUser.createdAt(),
                Calculator.calculate(githubUser));
    }
}
