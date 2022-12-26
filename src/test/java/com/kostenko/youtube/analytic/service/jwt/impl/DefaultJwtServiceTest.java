package com.kostenko.youtube.analytic.service.jwt.impl;

import com.kostenko.youtube.analytic.config.MockedDataSourceConfiguration;
import com.kostenko.youtube.analytic.exception.security.AccountUnavailableException;
import com.kostenko.youtube.analytic.exception.security.InvalidUserCredentialsException;
import com.kostenko.youtube.analytic.model.security.jwt.Jwt;
import com.kostenko.youtube.analytic.model.security.user.User;
import com.kostenko.youtube.analytic.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static com.kostenko.youtube.analytic.model.security.MockedSecurityModelsProvider.*;
import static com.kostenko.youtube.analytic.util.security.TestSecurityConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MockedDataSourceConfiguration.class)
class DefaultJwtServiceTest {
    @MockBean
    private UserService userProcessor;

    @SpyBean
    private DefaultJwtService jwtProcessor;

    @Test
    void getJwtTest() {
        Jwt excepted = getMockedJwt();
        User user = getMockedUser();
        when(userProcessor.getUser(TEST_USERNAME))
                .thenReturn(user);
        doReturn(TEST_TOKEN)
                .when(jwtProcessor)
                .getToken(user);

        Jwt actual = jwtProcessor.getJwt(TEST_USERNAME, TEST_PASSWORD);

        assertEquals(excepted, actual);
    }

    @Test
    void getJwtWhenCredentialsIsIncorrectThrowYoutubeAnalyticInvalidUserCredentialsExceptionTest() {
        InvalidUserCredentialsException excepted = new InvalidUserCredentialsException(TEST_USERNAME);

        when(userProcessor.getUser(TEST_USERNAME))
                .thenReturn(getMockedUser());

        InvalidUserCredentialsException actual = assertThrows(
                InvalidUserCredentialsException.class,
                () -> jwtProcessor.getJwt(TEST_USERNAME, TEST_INCORRECT_PASSWORD)
        );

        assertEquals(excepted, actual);
    }

    @Test
    void getJwtWhenAccountIsUnavailableThrowYoutubeAnalyticAccountUnavailableExceptionTest() {
        when(TEST_USER_STATUS.isDisabled())
                .thenReturn(true);

        AccountUnavailableException excepted = new AccountUnavailableException(TEST_USERNAME, TEST_USER_STATUS);

        when(userProcessor.getUser(TEST_USERNAME))
                .thenReturn(getMockedUser());

        AccountUnavailableException actual = assertThrows(
                AccountUnavailableException.class,
                () -> jwtProcessor.getJwt(TEST_USERNAME, TEST_PASSWORD)
        );

        assertEquals(excepted, actual);
    }
}
