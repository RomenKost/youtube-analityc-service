package com.kostenko.youtube.analytic.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum YoutubeAnalyticJwtKey {
    AUTHORITY("authority"),
    AUTHORITIES("authorities");

    private final String key;
}
