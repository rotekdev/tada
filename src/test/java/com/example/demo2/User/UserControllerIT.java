package com.example.demo2.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class UserControllerIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void cleanUpDB() {
        userRepository.deleteAll();
    }

    @Test
    void shouldReturnUserWhenItDoesNotExistInDB() {
        String givenLogin = "login";

        webTestClient.get().uri("/users/" + givenLogin).exchange().expectStatus().isOk().expectBody().json("""
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

        User expectedUser = new User(givenLogin, 1L);
        Optional<User> actualUser = userRepository.findById(givenLogin);
        then(actualUser).isPresent().get().usingRecursiveComparison().isEqualTo(expectedUser);
    }

    @Test
    void shouldReturnUserWhenItAlreadyExistInDB() {
        String givenLogin = "login";
        userRepository.saveAndFlush(new User(givenLogin, 1L));

        webTestClient.get().uri("/users/" + givenLogin).exchange().expectStatus().isOk().expectBody().json("""
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

        User expectedUser = new User(givenLogin, 2L);
        Optional<User> actualUser = userRepository.findById(givenLogin);
        then(actualUser).isPresent().get().usingRecursiveComparison().isEqualTo(expectedUser);
    }

}