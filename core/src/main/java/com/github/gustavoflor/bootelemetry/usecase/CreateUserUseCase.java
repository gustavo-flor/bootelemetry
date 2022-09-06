package com.github.gustavoflor.bootelemetry.usecase;

import com.github.gustavoflor.bootelemetry.entity.User;

public interface CreateUserUseCase {
    Output execute(Input input);

    record Input(String username) {
    }

    record Output(User user) {
    }
}
