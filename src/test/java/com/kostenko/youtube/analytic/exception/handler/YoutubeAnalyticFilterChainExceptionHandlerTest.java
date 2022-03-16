package com.kostenko.youtube.analytic.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kostenko.youtube.analytic.exception.response.YoutubeAnalyticJwtHTTPExceptionResponse;
import com.kostenko.youtube.analytic.exception.security.YoutubeAnalyticIncorrectJwtException;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(classes = YoutubeAnalyticFilterChainExceptionHandler.class)
class YoutubeAnalyticFilterChainExceptionHandlerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private YoutubeAnalyticFilterChainExceptionHandler exceptionHandler;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;

    @Test
    void handleYoutubeAnalyticIncorrectJwtExceptionTest() throws ServletException, IOException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        doThrow(new YoutubeAnalyticIncorrectJwtException( new JwtException(""), "any token"))
                .when(filterChain)
                .doFilter(request, response);
        when(response.getWriter())
                .thenReturn(printWriter);

        exceptionHandler.doFilterInternal(
                request, response, filterChain
        );

        verify(response, times(1))
                .setStatus(SC_UNAUTHORIZED);
        verify(response, times(1))
                .setHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE);

        YoutubeAnalyticJwtHTTPExceptionResponse actual = objectMapper.readValue(
                stringWriter.toString(),
                YoutubeAnalyticJwtHTTPExceptionResponse.class
        );
        YoutubeAnalyticJwtHTTPExceptionResponse expected = YoutubeAnalyticJwtHTTPExceptionResponse
                .builder()
                .exceptionClassName(YoutubeAnalyticIncorrectJwtException.class.getSimpleName())
                .message("JWT is incorrect (jwt = any token).")
                .jwt("any token")
                .build();

        assertEquals(expected, actual);
    }
}
