package com.github.gustavoflor.bootelemetry.entity;

import com.github.gustavoflor.bootelemetry.integration.github.GithubUser;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter
@Setter
@Builder(access = AccessLevel.PRIVATE)
public class User extends Entity {
    private String username;
    private String name;
    private String location;
    private String company;
    private String bio;
    private Long githubId;

    public static User of(final GithubUser githubUser) {
        return User.builder()
                .username(githubUser.login())
                .name(githubUser.name())
                .location(githubUser.location())
                .company(githubUser.company())
                .bio(githubUser.bio())
                .githubId(githubUser.id())
                .build();
    }
}
