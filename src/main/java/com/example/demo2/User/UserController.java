package com.example.demo2.User;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users/{login}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String login) {
        return ResponseEntity.ok(userService.getUser(login));
    }
}
