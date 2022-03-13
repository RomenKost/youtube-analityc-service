package com.kostenko.youtube.analytic.exception.security;

import lombok.Getter;

@Getter
public class YoutubeAnalyticInvalidUserCredentialsException extends RuntimeException {
    private final String username;

    public YoutubeAnalyticInvalidUserCredentialsException(String username) {
        super("Invalid credentials for username = " + username + ".");
        this.username = username;
    }
}
