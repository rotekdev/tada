package com.example.demo2.User.github.api;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class CalculatorTest {

    @Test
    public void testCalculate() {
        then(Calculator.calculate(githubUserOf(1L, 2L))).isEqualTo("9.00");
        then(Calculator.calculate(githubUserOf(1L, 5L))).isEqualTo("3.60");
        then(Calculator.calculate(githubUserOf(1L, 0L))).isEqualTo("undefined");
        then(Calculator.calculate(githubUserOf(null, 2L))).isEqualTo("undefined");
        then(Calculator.calculate(githubUserOf(1L, null))).isEqualTo("undefined");
    }

    private static GithubUser githubUserOf(Long publicRepos, Long followers) {
        return new GithubUser(null, null, null, null, null, publicRepos, followers, null);
    }

}