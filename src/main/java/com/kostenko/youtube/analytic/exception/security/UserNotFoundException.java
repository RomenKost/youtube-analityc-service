package com.kostenko.youtube.analytic.exception.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class UserNotFoundException extends RuntimeException {
    private final String username;

    public UserNotFoundException(String username) {
        super("User with username = " + username + " wasn't found.");
        this.username = username;
    }
}
