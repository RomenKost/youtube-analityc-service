package com.kostenko.youtube.analytic.exception.security;

import lombok.Getter;

@Getter
public class YoutubeAnalyticUserNotFoundException extends RuntimeException {
    private final String username;

    public YoutubeAnalyticUserNotFoundException(String username) {
        super("User with username = " + username + " wasn't found.");
        this.username = username;
    }
}
