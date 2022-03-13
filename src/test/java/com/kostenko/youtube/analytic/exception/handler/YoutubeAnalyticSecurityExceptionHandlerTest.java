package com.kostenko.youtube.analytic.exception.handler;

import com.kostenko.youtube.analytic.exception.response.YoutubeAnalyticAccountHTTPExceptionResponse;
import com.kostenko.youtube.analytic.exception.response.YoutubeAnalyticSecurityHTTPExceptionResponse;
import com.kostenko.youtube.analytic.exception.security.YoutubeAnalyticAccountUnavailableException;

import com.kostenko.youtube.analytic.exception.security.YoutubeAnalyticInvalidUserCredentialsException;
import com.kostenko.youtube.analytic.exception.security.YoutubeAnalyticUserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserStatus.ACTIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = YoutubeAnalyticSecurityExceptionHandler.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class YoutubeAnalyticSecurityExceptionHandlerTest {
    @Autowired
    private YoutubeAnalyticSecurityExceptionHandler handler;

    @Test
    void handleYoutubeAnalyticInvalidUserCredentialsExceptionTest() {
        YoutubeAnalyticSecurityHTTPExceptionResponse expected = YoutubeAnalyticSecurityHTTPExceptionResponse
                .builder()
                .exceptionClassName(YoutubeAnalyticInvalidUserCredentialsException.class.getSimpleName())
                .message("Invalid credentials for username = any username.")
                .username("any username")
                .build();
        YoutubeAnalyticInvalidUserCredentialsException exception = new YoutubeAnalyticInvalidUserCredentialsException("any username");

        YoutubeAnalyticSecurityHTTPExceptionResponse actual = handler.handleYoutubeAnalyticInvalidUserCredentialsException(exception);

        assertEquals(expected, actual);
    }

    @Test
    void handleYoutubeAnalyticAccountUnavailableExceptionTest() {
        YoutubeAnalyticAccountHTTPExceptionResponse expected = YoutubeAnalyticAccountHTTPExceptionResponse
                .builder()
                .exceptionClassName(YoutubeAnalyticAccountUnavailableException.class.getSimpleName())
                .message("Account with username = any username is unavailable.")
                .username("any username")
                .build();
        YoutubeAnalyticAccountUnavailableException exception = new YoutubeAnalyticAccountUnavailableException("any username", ACTIVE);

        YoutubeAnalyticAccountHTTPExceptionResponse actual = handler.handleYoutubeAnalyticAccountUnavailableException(exception);

        assertEquals(expected, actual);
    }

    @Test
    void handleYoutubeAnalyticUserNotFoundExceptionTest() {
        YoutubeAnalyticSecurityHTTPExceptionResponse expected = YoutubeAnalyticSecurityHTTPExceptionResponse
                .builder()
                .exceptionClassName(YoutubeAnalyticUserNotFoundException.class.getSimpleName())
                .message("User with username = any username wasn't found.")
                .username("any username")
                .build();
        YoutubeAnalyticUserNotFoundException exception = new YoutubeAnalyticUserNotFoundException("any username");

        YoutubeAnalyticSecurityHTTPExceptionResponse actual = handler.handleYoutubeAnalyticUserNotFoundException(exception);

        assertEquals(expected, actual);
    }
}
