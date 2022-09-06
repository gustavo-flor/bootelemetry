package com.github.gustavoflor.bootelemetry.service;

import com.github.gustavoflor.bootelemetry.entity.User;
import com.github.gustavoflor.bootelemetry.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Page<User> findAll(final Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @NewSpan("UserService.findById")
    public Optional<User> findById(final String id) {
        return userRepository.findById(id);
    }

    public void deleteById(final String id) {
        userRepository.deleteById(id);
    }
}
