package com.kostenko.youtube.analytic.security.filter;

import com.kostenko.youtube.analytic.exception.security.YoutubeAnalyticIncorrectJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.kostenko.youtube.analytic.security.jwt.YoutubeAnalyticJwtKey.AUTHORITIES;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@ExtendWith(MockitoExtension.class)
class YoutubeAnalyticJwtTokenVerifierTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    @Mock
    private Claims body;

    @Spy
    private YoutubeAnalyticJwtTokenVerifier tokenVerifier = new YoutubeAnalyticJwtTokenVerifier("any secret key");

    @AfterEach
    void clear() {
        reset(request, response, filterChain, body, tokenVerifier);
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternalWhenTokenWasNotPassedThenDoNothingTest() throws ServletException, IOException {
        tokenVerifier.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1))
                .doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternalWhenTokenIsCorrectThenAuthorizeTest() throws ServletException, IOException {
        UsernamePasswordAuthenticationToken expected = new UsernamePasswordAuthenticationToken(
                "any username",
                null,
                List.of(
                        new SimpleGrantedAuthority("any authority"),
                        new SimpleGrantedAuthority("any other authority")
                )
        );

        when(request.getHeader(AUTHORIZATION))
                .thenReturn("any token");
        doReturn(body)
                .when(tokenVerifier)
                .getBody("any token");
        when(body.getSubject())
                .thenReturn("any username");
        when(body.get(AUTHORITIES.getKey()))
                .thenReturn(List.of("any authority", "any other authority"));
        doNothing()
                .when(tokenVerifier)
                .checkExpiration(any());

        tokenVerifier.doFilterInternal(request, response, filterChain);

        verify(tokenVerifier, times(1))
                .checkExpiration(any());
        verify(filterChain, times(1))
                .doFilter(request, response);

        UsernamePasswordAuthenticationToken actual = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                .getContext()
                .getAuthentication();

        assertEquals(expected.getCredentials(), actual.getCredentials());
        assertEquals(expected.getPrincipal(), actual.getPrincipal());
        assertEquals(expected.getAuthorities(), actual.getAuthorities());
    }

    @Test
    void doFilterInternalWhenTokenIsIncorrectThrowYoutubeAnalyticIncorrectJwtExceptionTest() {
        JwtException jwtException = new JwtException("");
        YoutubeAnalyticIncorrectJwtException excepted = new YoutubeAnalyticIncorrectJwtException(jwtException, "any token");
        when(request.getHeader(AUTHORIZATION))
                .thenReturn("any token");
        doThrow(jwtException)
                .when(tokenVerifier)
                .getBody("any token");

        YoutubeAnalyticIncorrectJwtException actual = assertThrows(
                YoutubeAnalyticIncorrectJwtException.class,
                () -> tokenVerifier.doFilterInternal(request, response, filterChain)
        );

        assertEquals(excepted.getJwt(), actual.getJwt());
        assertEquals(excepted.getExpiration(), actual.getExpiration());
        assertEquals(excepted.getMessage(), actual.getMessage());
        assertEquals(excepted.getSuppressed(), actual.getSuppressed());
    }

    @Test
    void checkExpirationWhenExpirationIsStitchedThenDoNothingTest() {
        Date expiration = new Date(new Date().getTime() + 1000);
        assertDoesNotThrow(() -> tokenVerifier.checkExpiration(expiration));
    }

    @Test
    void checkExpirationWhenExpirationIsStitchedThrowYoutubeAnalyticIncorrectJwtExceptionTest() {
        Date expiration = new Date();
        YoutubeAnalyticIncorrectJwtException excepted = new YoutubeAnalyticIncorrectJwtException(expiration);

        YoutubeAnalyticIncorrectJwtException actual = assertThrows(
                YoutubeAnalyticIncorrectJwtException.class,
                () -> tokenVerifier.checkExpiration(expiration)
        );

        assertEquals(excepted.getJwt(), actual.getJwt());
        assertEquals(excepted.getExpiration(), actual.getExpiration());
        assertEquals(excepted.getMessage(), actual.getMessage());
        assertEquals(excepted.getSuppressed(), actual.getSuppressed());
    }
}
