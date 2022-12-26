package com.kostenko.youtube.analytic.exception.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.lang.DateFormats;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;

@Getter
@EqualsAndHashCode(callSuper = false)
public class IncorrectJwtException extends RuntimeException {
    private final String jwt;
    private final Date expiration;

    public IncorrectJwtException(JwtException jwtException, String jwt) {
        super("JWT is incorrect (jwt = " + jwt + ").", jwtException);
        this.expiration = null;
        this.jwt = jwt;
    }

    public IncorrectJwtException(Date expiration, String jwt) {
        super("JWT is stitched (expiration = " + DateFormats.formatIso8601(expiration) + ").");
        this.expiration = expiration;
        this.jwt = jwt;
    }
}
