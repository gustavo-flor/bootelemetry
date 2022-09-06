package com.github.gustavoflor.bootelemetry.listener;

import com.github.gustavoflor.bootelemetry.integration.github.GithubUser;
import com.github.gustavoflor.bootelemetry.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component("AlreadyInUseUsername")
@RequiredArgsConstructor
public class AlreadyInUseUsernameListener implements Consumer<GithubUser> {
    private final UserRepository userRepository;

    @Override
    public void accept(GithubUser githubUser) {
        userRepository.findByGithubId(githubUser.id()).ifPresent(user -> {
            log.info("Updating user information for id = {}", user.getId());
            user.setName(githubUser.name());
            user.setBio(githubUser.bio());
            user.setCompany(githubUser.company());
            user.setLocation(githubUser.location());
            user.setUsername(githubUser.login());
            userRepository.save(user);
        });
    }
}
