package com.example.demo2.User;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "githubUserApiClient", configuration = GithubApiClientConfig.class)
public interface GithubUserApiClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{login}", produces = "application/json")
    GithubUser getUser(@PathVariable String login);
}
