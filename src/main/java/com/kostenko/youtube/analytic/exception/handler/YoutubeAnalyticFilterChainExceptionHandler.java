package com.kostenko.youtube.analytic.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kostenko.youtube.analytic.exception.response.YoutubeAnalyticJwtHTTPExceptionResponse;
import com.kostenko.youtube.analytic.exception.security.YoutubeAnalyticIncorrectJwtException;
import lombok.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.MediaType.*;

public class YoutubeAnalyticFilterChainExceptionHandler extends OncePerRequestFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (YoutubeAnalyticIncorrectJwtException exception) {
            handleYoutubeAnalyticIncorrectJwtException(response, exception);
        }
    }

    private void handleYoutubeAnalyticIncorrectJwtException(HttpServletResponse response,
                                                            YoutubeAnalyticIncorrectJwtException exception) throws IOException {
        YoutubeAnalyticJwtHTTPExceptionResponse httpExceptionResponse = YoutubeAnalyticJwtHTTPExceptionResponse
                .builder()
                .exceptionClassName(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .date(new Date())
                .jwt(exception.getJwt())
                .expiration(exception.getExpiration())
                .build();
        String body = objectMapper.writeValueAsString(httpExceptionResponse);

        response.setStatus(SC_UNAUTHORIZED);
        response.setHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        response.getWriter().print(body);
    }
}
