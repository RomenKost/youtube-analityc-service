package com.kostenko.youtube.analytic.controller.dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kostenko.youtube.analytic.model.security.user.UserRole;
import lombok.Data;

@Data
public class JwtDto {
    @JsonProperty("username")
    private String username;

    @JsonProperty("token")
    private String token;

    @JsonProperty("expiration")
    private long expiration;

    @JsonProperty("role")
    private UserRole role;
}
