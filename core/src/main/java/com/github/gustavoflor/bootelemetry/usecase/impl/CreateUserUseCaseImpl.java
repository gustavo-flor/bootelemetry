package com.github.gustavoflor.bootelemetry.usecase.impl;

import com.github.gustavoflor.bootelemetry.entity.User;
import com.github.gustavoflor.bootelemetry.exception.UsernameAlreadyInUseException;
import com.github.gustavoflor.bootelemetry.integration.github.GithubClient;
import com.github.gustavoflor.bootelemetry.integration.github.GithubUser;
import com.github.gustavoflor.bootelemetry.mapping.UseCase;
import com.github.gustavoflor.bootelemetry.repository.UserRepository;
import com.github.gustavoflor.bootelemetry.usecase.CreateUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;

@UseCase
@RequiredArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final GithubClient githubClient;
    private final UserRepository userRepository;
    private final StreamBridge streamBridge;

    @Override
    public Output execute(final Input input) {
        final GithubUser githubUser = githubClient.findByUsername(input.username());
        if (userRepository.existsByGithubId(githubUser.id())) {
            streamBridge.send("AlreadyInUseUsername-out-0", githubUser);
            throw new UsernameAlreadyInUseException();
        }
        final User user = userRepository.save(User.of(githubUser));
        return new Output(user);
    }
}
