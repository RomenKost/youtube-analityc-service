package com.kostenko.youtube.analytic.model.security;

import com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserStatus;
import com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserRole;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class YoutubeAnalyticUser {
    private final String username;
    private final String password;
    private final YoutubeAnalyticUserRole role;
    private final YoutubeAnalyticUserStatus status;
}
