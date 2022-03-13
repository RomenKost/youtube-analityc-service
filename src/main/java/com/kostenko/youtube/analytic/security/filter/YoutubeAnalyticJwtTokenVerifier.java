package com.kostenko.youtube.analytic.security.filter;

import com.kostenko.youtube.analytic.exception.security.YoutubeAnalyticIncorrectJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.DateFormats;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kostenko.youtube.analytic.exception.message.ErrorMessages.*;
import static com.kostenko.youtube.analytic.security.jwt.YoutubeAnalyticJwtKey.*;
import static org.springframework.http.HttpHeaders.*;

@Slf4j
@AllArgsConstructor
public class YoutubeAnalyticJwtTokenVerifier extends OncePerRequestFilter {
    private final String secretKey;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader(AUTHORIZATION);
        if (jwt == null) {
            filterChain.doFilter(request, response);
            return;
        }
        log.info("Verifying jwt = " + jwt + " ...");

        try {
            Claims body = getBody(jwt);
            String username = body.getSubject();

            Set<? extends GrantedAuthority> grantedAuthorities = getAuthorities(body)
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());

            checkExpiration(body.getExpiration());

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("Jwt for user = " + username + " was verified (jwt = " + jwt + ").");
        } catch (JwtException e) {
            YoutubeAnalyticIncorrectJwtException exception = new YoutubeAnalyticIncorrectJwtException(e, jwt);
            log.error(String.format(JWT_IS_INCORRECT, jwt), exception);
            throw exception;
        }
        filterChain.doFilter(request, response);
    }

    @SuppressWarnings("unchecked")
    private List<String> getAuthorities(Claims body) {
        return (List<String>) body.get(AUTHORITIES.getKey());
    }

    Claims getBody(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    void checkExpiration(Date expiration) {
        if (expiration.getTime() < new Date().getTime()) {
            YoutubeAnalyticIncorrectJwtException exception = new YoutubeAnalyticIncorrectJwtException(expiration);
            log.error(String.format(JWT_IS_STITCHED, DateFormats.formatIso8601(expiration)), exception);
            throw exception;
        }
    }
}
