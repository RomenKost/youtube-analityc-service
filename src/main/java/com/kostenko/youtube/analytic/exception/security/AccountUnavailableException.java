package com.kostenko.youtube.analytic.exception.security;

import com.kostenko.youtube.analytic.model.security.user.UserStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class AccountUnavailableException extends RuntimeException {
    public AccountUnavailableException(String username, UserStatus status) {
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
