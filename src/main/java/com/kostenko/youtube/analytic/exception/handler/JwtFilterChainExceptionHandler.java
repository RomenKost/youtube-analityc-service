package com.kostenko.youtube.analytic.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kostenko.youtube.analytic.exception.mapper.ExceptionResponseMapper;
import com.kostenko.youtube.analytic.exception.response.JwtExceptionResponse;
import com.kostenko.youtube.analytic.exception.security.IncorrectJwtException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.MediaType.*;

@Component
@RequiredArgsConstructor
public class JwtFilterChainExceptionHandler extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    private final ExceptionResponseMapper exceptionResponseMapper;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (IncorrectJwtException exception) {
            handleIncorrectJwtException(response, exception);
        }
    }

    private void handleIncorrectJwtException(HttpServletResponse response,
                                             IncorrectJwtException exception) throws IOException {
        JwtExceptionResponse jwtExceptionResponse = exceptionResponseMapper.mapExceptionToExceptionResponse(exception);
        String body = objectMapper.writeValueAsString(jwtExceptionResponse);

        response.setStatus(SC_UNAUTHORIZED);
        response.setHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        response.getWriter().print(body);
    }
}
