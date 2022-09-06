package com.github.gustavoflor.bootelemetry.usecase.impl;

import com.github.gustavoflor.bootelemetry.entity.User;
import com.github.gustavoflor.bootelemetry.exception.UsernameAlreadyInUseException;
import com.github.gustavoflor.bootelemetry.integration.github.GithubClient;
import com.github.gustavoflor.bootelemetry.integration.github.GithubUser;
import com.github.gustavoflor.bootelemetry.mapping.UseCase;
import com.github.gustavoflor.bootelemetry.repository.UserRepository;
import com.github.gustavoflor.bootelemetry.usecase.CreateUserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final GithubClient githubClient;
    private final UserRepository userRepository;
    private final Tracer tracer;

    @Override
    public Output execute(final Input input) {
        final Span preUserSave = tracer.nextSpan().name("PreUserSave");
        final GithubUser githubUser;
        try (Tracer.SpanInScope span = tracer.withSpan(preUserSave.start())) {
            githubUser = githubClient.findByUsername(input.username());
            preUserSave.tag("x-github_id", githubUser.id().toString());
            preUserSave.tag("x-username", githubUser.login());
            preUserSave.event("githubUserSearched");
            if (userRepository.existsByGithubId(githubUser.id())) {
                throw new UsernameAlreadyInUseException();
            }
        } catch (UsernameAlreadyInUseException e) {
            preUserSave.error(e);
            throw e;
        } finally {
            preUserSave.end();
        }
        final Span postGithubSearch = tracer.nextSpan().name("PostGithubSearch");
        final User user;
        try (Tracer.SpanInScope span = tracer.withSpan(postGithubSearch.start())) {
            user = userRepository.save(User.of(githubUser));
            postGithubSearch.tag("x-user_id", user.getId());
        } finally {
            postGithubSearch.end();
        }
        return new Output(user);
    }
}
