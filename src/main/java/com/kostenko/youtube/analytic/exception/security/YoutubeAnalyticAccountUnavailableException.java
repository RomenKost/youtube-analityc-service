package com.kostenko.youtube.analytic.exception.security;

import com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserStatus;
import lombok.Getter;

@Getter
public class YoutubeAnalyticAccountUnavailableException extends RuntimeException {
    public YoutubeAnalyticAccountUnavailableException(String username, YoutubeAnalyticUserStatus status) {
        super("Account with username = " + username + " is unavailable.");
        this.username = username;

        if (status.isAccountExpired()) {
            reason = "account expired";
        } else if (status.isCredentialsExpired()) {
            reason = "credential expired";
        } else if (status.isDisabled()) {
            reason = "account disable";
        } else if (status.isAccountLocked()) {
            reason = "account locked";
        } else {
            reason = null;
        }
    }

    private final String username;
    private final String reason;
}
