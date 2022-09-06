package com.github.gustavoflor.bootelemetry.integration.github;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "github-client", url = "https://api.github.com")
public interface GithubClient {
    @GetMapping("/users/{username}")
    GithubUser findByUsername(@PathVariable String username);
}
