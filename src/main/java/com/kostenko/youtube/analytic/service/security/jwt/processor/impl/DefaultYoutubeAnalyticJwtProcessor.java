package com.kostenko.youtube.analytic.service.security.jwt.processor.impl;

import com.kostenko.youtube.analytic.exception.security.YoutubeAnalyticAccountUnavailableException;
import com.kostenko.youtube.analytic.exception.security.YoutubeAnalyticInvalidUserCredentialsException;
import com.kostenko.youtube.analytic.model.security.YoutubeAnalyticJwt;
import com.kostenko.youtube.analytic.model.security.YoutubeAnalyticUser;
import com.kostenko.youtube.analytic.service.security.jwt.processor.YoutubeAnalyticJwtProcessor;
import com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserStatus;
import com.kostenko.youtube.analytic.service.security.user.processor.YoutubeAnalyticUserProcessor;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

import static com.kostenko.youtube.analytic.exception.message.ErrorMessages.*;
import static com.kostenko.youtube.analytic.security.jwt.YoutubeAnalyticJwtKey.*;

@Slf4j
@Service
public class DefaultYoutubeAnalyticJwtProcessor implements YoutubeAnalyticJwtProcessor {
    private final YoutubeAnalyticUserProcessor userProcessor;

    private final long expiration;
    private final String secretKey;

    public DefaultYoutubeAnalyticJwtProcessor(YoutubeAnalyticUserProcessor userService,
                                              @Value("${youtube.analytic.security.jwt.expiration}") long expiration,
                                              @Value("${youtube.analytic.security.jwt.key}") String secretKey) {
        this.userProcessor = userService;
        this.expiration = expiration;
        this.secretKey = secretKey;
    }

    @Override
    public YoutubeAnalyticJwt getJwt(String username, String password) {
        log.info("Creating jwt for user with username = " + username + ".");
        YoutubeAnalyticUser user = userProcessor.getUser(username);

        if (!Objects.equals(user.getPassword(), password)) {
            YoutubeAnalyticInvalidUserCredentialsException exception = new YoutubeAnalyticInvalidUserCredentialsException(username);
            log.error(String.format(INVALID_CREDENTIALS, username), exception);
            throw exception;
        }

        YoutubeAnalyticUserStatus status = user.getStatus();
        if (status.isDisabled()
                || status.isAccountExpired()
                || status.isAccountLocked()
                || status.isCredentialsExpired()) {
            YoutubeAnalyticAccountUnavailableException exception = new YoutubeAnalyticAccountUnavailableException(username, status);
            log.error(String.format(ACCOUNT_UNAVAILABLE, username, exception.getReason()));
            throw exception;
        }

            log.info("Jwt for use with username = " + username + " was created.");
        return new YoutubeAnalyticJwt(
                getToken(user),
                user.getUsername(),
                expiration,
                user.getRole()
        );
    }

    String getToken(YoutubeAnalyticUser user) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim(AUTHORITIES.getKey(), user.getRole().getPermissions())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }
}
