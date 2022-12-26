package com.kostenko.youtube.analytic.exception.handler;

import com.kostenko.youtube.analytic.controller.SecurityController;
import com.kostenko.youtube.analytic.exception.mapper.ExceptionResponseMapper;
import com.kostenko.youtube.analytic.exception.response.AuthorizationExceptionResponse;
import com.kostenko.youtube.analytic.exception.security.AccountUnavailableException;
import com.kostenko.youtube.analytic.exception.security.InvalidUserCredentialsException;
import com.kostenko.youtube.analytic.exception.security.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice(basePackageClasses = SecurityController.class)
public class SecurityExceptionHandler {
    private final ExceptionResponseMapper exceptionResponseMapper;

    @ResponseBody
    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(InvalidUserCredentialsException.class)
    public AuthorizationExceptionResponse handleInvalidUserCredentialsException(InvalidUserCredentialsException exception) {
        log.error(exception.getMessage(), exception);
        return exceptionResponseMapper.mapExceptionToExceptionResponse(exception);
    }

    @ResponseBody
    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(AccountUnavailableException.class)
    public AuthorizationExceptionResponse handleAccountUnavailableException(AccountUnavailableException exception) {
        log.error(exception.getMessage(), exception);
        return exceptionResponseMapper.mapExceptionToExceptionResponse(exception);
    }

    @ResponseBody
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public AuthorizationExceptionResponse handleUserNotFoundException(UserNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return exceptionResponseMapper.mapExceptionToExceptionResponse(exception);
    }
}
