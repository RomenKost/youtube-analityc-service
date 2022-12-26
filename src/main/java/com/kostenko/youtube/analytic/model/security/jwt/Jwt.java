package com.kostenko.youtube.analytic.model.security.jwt;

import com.kostenko.youtube.analytic.model.security.user.UserRole;

import lombok.Data;

@Data
public class Jwt {
    private String token;
    private String username;
    private long expiration;
    private UserRole role;
}