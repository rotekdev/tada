package com.example.demo2.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public String getUser(String login) {
        User user = userRepository.findById(login).orElse(new User(login, 0L));
        user.increment();
        userRepository.save(user);
        return """
                {
                "id": "...",
                "login": "...",
                "name": "…",
                "type": "...",
                "avatarUrl": "...",
                "createdAt": "...",
                "calculations": "..."
                }
                """;
    }
}
