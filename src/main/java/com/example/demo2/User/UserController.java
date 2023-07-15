package com.example.demo2.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/users/{login}")
    public String getUser(@PathVariable String login) {
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
