package com.kostenko.youtube.analytic.service.jwt.impl;

import com.kostenko.youtube.analytic.exception.security.AccountUnavailableException;
import com.kostenko.youtube.analytic.exception.security.InvalidUserCredentialsException;
import com.kostenko.youtube.analytic.model.security.jwt.Jwt;
import com.kostenko.youtube.analytic.model.security.user.User;
import com.kostenko.youtube.analytic.service.jwt.JwtService;
import com.kostenko.youtube.analytic.model.security.user.UserStatus;
import com.kostenko.youtube.analytic.service.user.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

import static com.kostenko.youtube.analytic.exception.message.ErrorMessages.*;
import static com.kostenko.youtube.analytic.util.security.JwtConstants.*;

@Slf4j
@Service
public class DefaultJwtService implements JwtService {
    private final UserService userProcessor;

    private final long expiration;
    private final String secretKey;

    public DefaultJwtService(UserService userService,
                             @Value("${youtube.analytic.security.jwt.expiration}") long expiration,
                             @Value("${youtube.analytic.security.jwt.key}") String secretKey) {
        this.userProcessor = userService;
        this.expiration = expiration;
        this.secretKey = secretKey;
    }

    @Override
    public Jwt getJwt(String username, String password) {
        log.info("Creating jwt for user with username = " + username + ".");
        User user = userProcessor.getUser(username);

        if (!Objects.equals(user.getPassword(), password)) {
            log.error(INVALID_CREDENTIALS, username);
            throw new InvalidUserCredentialsException(username);
        }

        UserStatus status = user.getStatus();
        if (status.isDisabled()
                || status.isAccountExpired()
                || status.isAccountLocked()
                || status.isCredentialsExpired()) {
            AccountUnavailableException exception = new AccountUnavailableException(username, status);
            log.error(ACCOUNT_UNAVAILABLE, username, exception.getReason());
            throw exception;
        }

        log.info("Jwt for use with username = " + username + " was created.");

        Jwt result = new Jwt();
        result.setExpiration(expiration);
        result.setRole(user.getRole());
        result.setToken(getToken(user));
        result.setUsername(username);
        return result;
    }

    String getToken(User user) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim(AUTHORITIES, user.getRole().getPermissions())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }
}
