package com.example.demo2.User;

import com.example.demo2.User.github.api.GithubUser;
import com.example.demo2.User.github.api.GithubUserApiClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final GithubUserApiClient githubUserApiClient;

    @Transactional
    public UserResponse getUser(String login) {
        User user = userRepository.findById(login).orElse(new User(login, 0L));
        user.increment();
        userRepository.save(user);
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
                0L);
    }
}
