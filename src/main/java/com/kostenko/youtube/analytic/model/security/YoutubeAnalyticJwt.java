package com.kostenko.youtube.analytic.model.security;

import com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserRole;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class YoutubeAnalyticJwt {
    private final String token;
    private final String username;
    private final long expiration;
    private final YoutubeAnalyticUserRole role;
}