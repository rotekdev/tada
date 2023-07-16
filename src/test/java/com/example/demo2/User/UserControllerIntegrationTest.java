package com.example.demo2.User;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@WireMockTest(httpPort = 8080)
class UserControllerIntegrationTest {

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
        String givenLogin = "octocat";
        stubFor(get(WireMock.urlPathMatching("/users/" + givenLogin))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                   "login": "octocat",
                                   "id": 1,
                                   "avatar_url": "https://avatars.githubusercontent.com/u/583231?v=4",
                                   "type": "User",
                                   "name": "The Octocat",
                                   "public_repos": 8,
                                   "followers": 9786,
                                   "created_at": "2011-01-25T18:44:36Z"
                                 }
                                """)));

        webTestClient.get().uri("/users/" + givenLogin).exchange()
                .expectStatus().isOk()
                .expectBody().json("""
                {
                    "id": 1,
                    "login": "octocat",
                    "name": "The Octocat",
                    "type": "User",
                    "avatarUrl": "https://avatars.githubusercontent.com/u/583231?v=4",
                    "createdAt": "2011-01-25T18:44:36Z",
                    "calculations": "0.01"
                }
                """);

        User expectedUser = new User(givenLogin, 1L);
        Optional<User> actualUser = userRepository.findById(givenLogin);
        then(actualUser).isPresent().get().usingRecursiveComparison().isEqualTo(expectedUser);
    }

    @Test
    void shouldReturnUserWhenItAlreadyExistInDB() {
        String givenLogin = "octocat";
        userRepository.saveAndFlush(new User(givenLogin, 1L));
        stubFor(get(WireMock.urlPathMatching("/users/" + givenLogin))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                   "login": "octocat",
                                   "id": 1,
                                   "avatar_url": "https://avatars.githubusercontent.com/u/583231?v=4",
                                   "type": "User",
                                   "name": "The Octocat",
                                   "public_repos": 8,
                                   "followers": 0,
                                   "created_at": "2011-01-25T18:44:36Z"
                                 }
                                """)));

        webTestClient.get().uri("/users/" + givenLogin).exchange()
                .expectStatus().isOk()
                .expectBody().json("""
                 {
                    "id": 1,
                    "login": "octocat",
                    "name": "The Octocat",
                    "type": "User",
                    "avatarUrl": "https://avatars.githubusercontent.com/u/583231?v=4",
                    "createdAt": "2011-01-25T18:44:36Z",
                    "calculations": "undefined"
                }
                """);

        User expectedUser = new User(givenLogin, 2L);
        Optional<User> actualUser = userRepository.findById(givenLogin);
        then(actualUser).isPresent().get().usingRecursiveComparison().isEqualTo(expectedUser);
    }

    @Test
    void shouldReturn500AndCountRequestWhenGithubRespondsWIth500() {
        String givenLogin = "octocat";
        givenGithubApiRespondsWithError(givenLogin, 500);

        webTestClient.get().uri("/users/" + givenLogin).exchange()
                .expectStatus().isEqualTo(500);

        User expectedUser = new User(givenLogin, 1L);
        Optional<User> actualUser = userRepository.findById(givenLogin);
        then(actualUser).isPresent().get().usingRecursiveComparison().isEqualTo(expectedUser);
    }
    @Test
    void shouldReturn404AndCountRequestWhenGithubRespondsWIth404() {
        String givenLogin = "octocat";
        givenGithubApiRespondsWithError(givenLogin, 404);

        webTestClient.get().uri("/users/" + givenLogin).exchange()
                .expectStatus().isEqualTo(404);

        User expectedUser = new User(givenLogin, 1L);
        Optional<User> actualUser = userRepository.findById(givenLogin);
        then(actualUser).isPresent().get().usingRecursiveComparison().isEqualTo(expectedUser);
    }


    private static void givenGithubApiRespondsWithError(String givenLogin, int httpCode) {
        stubFor(get(WireMock.urlPathMatching("/users/" + givenLogin))
                .willReturn(WireMock.aResponse()
                        .withStatus(httpCode)));
    }

}