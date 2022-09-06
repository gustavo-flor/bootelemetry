package com.github.gustavoflor.bootelemetry.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsernameAlreadyInUseException extends ResponseStatusException {
    public UsernameAlreadyInUseException() {
        super(HttpStatus.CONFLICT);
    }
}
