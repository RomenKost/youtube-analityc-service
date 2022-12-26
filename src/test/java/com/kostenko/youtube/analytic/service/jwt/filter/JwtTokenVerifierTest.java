package com.kostenko.youtube.analytic.service.jwt.filter;

import com.kostenko.youtube.analytic.exception.security.IncorrectJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.kostenko.youtube.analytic.util.TestConstants.*;
import static com.kostenko.youtube.analytic.util.security.JwtConstants.*;
import static com.kostenko.youtube.analytic.util.security.TestSecurityConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpHeaders.*;

@ExtendWith(MockitoExtension.class)
class JwtTokenVerifierTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    @Mock
    private Claims body;

    @Spy
    private JwtTokenVerifier tokenVerifier = new JwtTokenVerifier(TEST_SECRET_KEY);

    @AfterEach
    void clear() {
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
                TEST_USERNAME,
                null,
                List.of(
                        new SimpleGrantedAuthority(TEST_AUTHORITY)
                )
        );

        when(request.getHeader(AUTHORIZATION))
                .thenReturn(TEST_TOKEN);
        doReturn(body)
                .when(tokenVerifier)
                .getBody(TEST_TOKEN);
        when(body.getSubject())
                .thenReturn(TEST_USERNAME);
        when(body.get(AUTHORITIES))
                .thenReturn(List.of(TEST_AUTHORITY));
        doNothing()
                .when(tokenVerifier)
                .checkExpiration(any(), eq(TEST_TOKEN));

        tokenVerifier.doFilterInternal(request, response, filterChain);

        verify(tokenVerifier, times(1))
                .checkExpiration(any(), eq(TEST_TOKEN));
        verify(filterChain, times(1))
                .doFilter(request, response);

        UsernamePasswordAuthenticationToken actual = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                .getContext()
                .getAuthentication();

        assertEquals(expected, actual);
    }

    @Test
    void doFilterInternalWhenTokenIsIncorrectThrowYoutubeAnalyticIncorrectJwtExceptionTest() {
        IncorrectJwtException excepted = new IncorrectJwtException(new JwtException(TEST_TOKEN), TEST_TOKEN);

        when(request.getHeader(AUTHORIZATION))
                .thenReturn(TEST_TOKEN);
        doThrow(new JwtException(TEST_TOKEN))
                .when(tokenVerifier)
                .getBody(TEST_TOKEN);

        IncorrectJwtException actual = assertThrows(
                IncorrectJwtException.class,
                () -> tokenVerifier.doFilterInternal(request, response, filterChain)
        );

        assertEquals(excepted, actual);
    }

    @Test
    void checkExpirationWhenExpirationIsStitchedThenDoNothingTest() {
        Date expiration = new Date(new Date().getTime() + 1000);
        assertDoesNotThrow(() -> tokenVerifier.checkExpiration(expiration, TEST_TOKEN));
    }

    @Test
    void checkExpirationWhenExpirationIsStitchedThrowYoutubeAnalyticIncorrectJwtExceptionTest() {
        IncorrectJwtException excepted = new IncorrectJwtException(TEST_DATE, TEST_TOKEN);

        IncorrectJwtException actual = assertThrows(
                IncorrectJwtException.class,
                () -> tokenVerifier.checkExpiration(TEST_DATE, TEST_TOKEN)
        );

        assertEquals(excepted, actual);
    }
}
