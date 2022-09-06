package com.github.gustavoflor.bootelemetry.controller;

import com.github.gustavoflor.bootelemetry.dto.CreateUserPayload;
import com.github.gustavoflor.bootelemetry.entity.User;
import com.github.gustavoflor.bootelemetry.service.UserService;
import com.github.gustavoflor.bootelemetry.usecase.CreateUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private static final int DEFAULT_PAGE_SIZE = 16;

    private final CreateUserUseCase createUserUseCase;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody final CreateUserPayload payload) {
        final var input = new CreateUserUseCase.Input(payload.username());
        return createUserUseCase.execute(input).user();
    }

    @GetMapping
    public Page<User> findAll(@RequestParam(required = false, defaultValue = "0") final Integer page) {
        return userService.findAll(PageRequest.of(page, DEFAULT_PAGE_SIZE));
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable final String id) {
        return userService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
