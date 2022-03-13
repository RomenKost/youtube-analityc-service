package com.kostenko.youtube.analytic.exception.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.lang.DateFormats;
import lombok.Getter;

import java.util.Date;

@Getter
public class YoutubeAnalyticIncorrectJwtException extends RuntimeException {
    private final String jwt;
    private final Date expiration;

    public YoutubeAnalyticIncorrectJwtException(JwtException jwtException, String jwt) {
        super("JWT is incorrect (jwt = " + jwt + ").", jwtException);
        this.expiration = null;
        this.jwt = jwt;
    }

    public YoutubeAnalyticIncorrectJwtException(Date expiration) {
        super("JWT is stitched (expiration = " + DateFormats.formatIso8601(expiration) + ").");
        this.expiration = expiration;
        this.jwt = null;
    }
}
