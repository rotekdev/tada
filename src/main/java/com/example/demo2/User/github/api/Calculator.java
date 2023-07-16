package com.example.demo2.User.github.api;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.TWO;

public class Calculator {
    public static String calculate(GithubUser githubUser) {
        if (githubUser.followers() == null || githubUser.followers() == 0 || githubUser.publicRepos() == null)
            return "undefined";
        BigDecimal publicRepos = BigDecimal.valueOf(githubUser.publicRepos());
        BigDecimal followers = BigDecimal.valueOf(githubUser.followers());
        return TWO.add(publicRepos)
                .multiply(BigDecimal.valueOf(6))
                .divide(followers, 2, RoundingMode.HALF_EVEN)
                .toString();
    }
}
