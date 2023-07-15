package com.example.demo2.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class UserControllerIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getUser() {
        webTestClient.get().uri("/users/login").exchange().expectStatus().isOk().expectBody().json("""
                {
                "id": "...",
                "login": "...",
                "name": "…",
                "type": "...",
                "avatarUrl": "...",
                "createdAt": "...",
                "calculations": "..."
                }
                """);
    }

}