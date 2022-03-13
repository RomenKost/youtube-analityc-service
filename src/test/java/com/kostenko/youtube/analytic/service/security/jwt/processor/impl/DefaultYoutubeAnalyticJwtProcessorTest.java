package com.kostenko.youtube.analytic.service.security.jwt.processor.impl;

import com.kostenko.youtube.analytic.exception.security.YoutubeAnalyticAccountUnavailableException;
import com.kostenko.youtube.analytic.exception.security.YoutubeAnalyticInvalidUserCredentialsException;
import com.kostenko.youtube.analytic.model.Models;
import com.kostenko.youtube.analytic.model.security.YoutubeAnalyticJwt;
import com.kostenko.youtube.analytic.model.security.YoutubeAnalyticUser;
import com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserStatus;
import com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserRole;
import com.kostenko.youtube.analytic.service.security.user.processor.YoutubeAnalyticUserProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DefaultYoutubeAnalyticJwtProcessorTest {
    private YoutubeAnalyticUserProcessor userProcessor;
    private DefaultYoutubeAnalyticJwtProcessor jwtProcessor;

    private YoutubeAnalyticUserRole userRole;
    private YoutubeAnalyticUserStatus userStatus;

    @BeforeAll
    void initialize() {
        userProcessor = mock(YoutubeAnalyticUserProcessor.class);
        jwtProcessor = spy(new DefaultYoutubeAnalyticJwtProcessor(
                userProcessor,
                12345,
                "world's securest really long secret key for testing"));

        userRole = mock(YoutubeAnalyticUserRole.class);
        userStatus = mock(YoutubeAnalyticUserStatus.class);
    }

    @AfterEach
    void clear() {
        reset(userProcessor, jwtProcessor, userStatus, userRole);
    }

    @Test
    void getJwtTest() {
        YoutubeAnalyticJwt excepted = Models.getJwt(userRole);
        YoutubeAnalyticUser user = Models.getUser(userRole, userStatus);
        when(userProcessor.getUser("any username"))
                .thenReturn(user);
        doReturn("any token")
                .when(jwtProcessor)
                .getToken(user);

        YoutubeAnalyticJwt actual = jwtProcessor.getJwt("any username", "any password");

        assertEquals(excepted, actual);
    }

    @Test
    void getJwtWhenCredentialsIsIncorrectThrowYoutubeAnalyticInvalidUserCredentialsExceptionTest() {
        YoutubeAnalyticInvalidUserCredentialsException excepted = new YoutubeAnalyticInvalidUserCredentialsException("any username");
        YoutubeAnalyticUser user = Models.getUser(userRole, userStatus);
        when(userProcessor.getUser("any username"))
                .thenReturn(user);

        YoutubeAnalyticInvalidUserCredentialsException actual = assertThrows(
                YoutubeAnalyticInvalidUserCredentialsException.class,
                () -> jwtProcessor.getJwt("any username", "incorrect password")
        );

        assertEquals(excepted.getUsername(), actual.getUsername());
        assertEquals(excepted.getMessage(), actual.getMessage());
    }

    @Test
    void getJwtWhenAccountIsUnavailableThrowYoutubeAnalyticAccountUnavailableExceptionTest() {
        when(userStatus.isDisabled())
                .thenReturn(true);
        YoutubeAnalyticAccountUnavailableException excepted = new YoutubeAnalyticAccountUnavailableException(
                "any username", userStatus
        );
        YoutubeAnalyticUser user = Models.getUser(userRole, userStatus);
        when(userProcessor.getUser("any username"))
                .thenReturn(user);

        YoutubeAnalyticAccountUnavailableException actual = assertThrows(
                YoutubeAnalyticAccountUnavailableException.class,
                () -> jwtProcessor.getJwt("any username", "any password")
        );

        assertEquals(excepted.getReason(), actual.getReason());
        assertEquals(excepted.getUsername(), actual.getUsername());
        assertEquals(excepted.getMessage(), actual.getMessage());
    }
}
