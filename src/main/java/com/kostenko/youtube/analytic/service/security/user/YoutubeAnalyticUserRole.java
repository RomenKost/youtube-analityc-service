package com.kostenko.youtube.analytic.service.security.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserPermission.*;

@Getter
@AllArgsConstructor
public enum YoutubeAnalyticUserRole {
    ROLE_CUSTOMER(List.of(FIND_VIDEOS, FIND_CHANNELS));

    private final List<YoutubeAnalyticUserPermission> permissions;
}
