package com.kostenko.youtube.analytic.service.security.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum YoutubeAnalyticUserStatus {
    ACTIVE(false, false, false, false);

    private final boolean accountExpired;
    private final boolean accountLocked;
    private final boolean credentialsExpired;
    private final boolean disabled;
}
