package com.kostenko.youtube.analytic.exception.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class InvalidUserCredentialsException extends RuntimeException {
    private final String username;

    public InvalidUserCredentialsException(String username) {
        super("Invalid credentials for username = " + username + ".");
        this.username = username;
    }
}
