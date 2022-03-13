package com.kostenko.youtube.analytic.exception.handler;

import com.kostenko.youtube.analytic.controller.YoutubeAnalyticSecurityRestController;
import com.kostenko.youtube.analytic.exception.response.YoutubeAnalyticAccountHTTPExceptionResponse;
import com.kostenko.youtube.analytic.exception.security.YoutubeAnalyticAccountUnavailableException;
import com.kostenko.youtube.analytic.exception.security.YoutubeAnalyticInvalidUserCredentialsException;
import com.kostenko.youtube.analytic.exception.response.YoutubeAnalyticSecurityHTTPExceptionResponse;
import com.kostenko.youtube.analytic.exception.security.YoutubeAnalyticUserNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice(basePackageClasses = YoutubeAnalyticSecurityRestController.class)
public class YoutubeAnalyticSecurityExceptionHandler {
    @ResponseBody
    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(YoutubeAnalyticInvalidUserCredentialsException.class)
    public YoutubeAnalyticSecurityHTTPExceptionResponse handleYoutubeAnalyticInvalidUserCredentialsException(YoutubeAnalyticInvalidUserCredentialsException exception) {
        return YoutubeAnalyticSecurityHTTPExceptionResponse
                .builder()
                .username(exception.getUsername())
                .date(new Date())
                .message(exception.getMessage())
                .exceptionClassName(exception.getClass().getSimpleName())
                .build();
    }

    @ResponseBody
    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(YoutubeAnalyticAccountUnavailableException.class)
    public YoutubeAnalyticAccountHTTPExceptionResponse handleYoutubeAnalyticAccountUnavailableException(YoutubeAnalyticAccountUnavailableException exception) {
        return YoutubeAnalyticAccountHTTPExceptionResponse
                .builder()
                .exceptionClassName(exception.getClass().getSimpleName())
                .username(exception.getUsername())
                .message(exception.getMessage())
                .reason(exception.getReason())
                .date(new Date())
                .build();
    }

    @ResponseBody
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(YoutubeAnalyticUserNotFoundException.class)
    public  YoutubeAnalyticSecurityHTTPExceptionResponse handleYoutubeAnalyticUserNotFoundException(YoutubeAnalyticUserNotFoundException exception) {
        return YoutubeAnalyticSecurityHTTPExceptionResponse
                .builder()
                .exceptionClassName(exception.getClass().getSimpleName())
                .username(exception.getUsername())
                .message(exception.getMessage())
                .date(new Date())
                .build();
    }
}
