package com.github.gustavoflor.bootelemetry.integration.github;

public record GithubUser(Long id,
                         String login,
                         String name,
                         String location,
                         String company,
                         String bio) {
}
