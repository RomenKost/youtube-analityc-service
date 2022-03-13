package com.kostenko.youtube.analytic.dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserRole;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@ToString
@Builder
@Jacksonized
@EqualsAndHashCode
public class YoutubeAnalyticJwtDto {
    @JsonProperty("username")
    private final String username;

    @JsonProperty("token")
    private final String token;

    @JsonProperty("expiration")
    private final long expiration;

    @JsonProperty("role")
    private final YoutubeAnalyticUserRole role;
}
