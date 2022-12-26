package com.kostenko.youtube.analytic.exception.mapper;

import com.kostenko.youtube.analytic.config.MockedDataSourceConfiguration;
import com.kostenko.youtube.analytic.exception.response.AuthorizationExceptionResponse;
import com.kostenko.youtube.analytic.exception.response.AnalyticExceptionResponse;
import com.kostenko.youtube.analytic.exception.response.JwtExceptionResponse;
import com.kostenko.youtube.analytic.exception.security.AccountUnavailableException;
import com.kostenko.youtube.analytic.exception.security.IncorrectJwtException;
import com.kostenko.youtube.analytic.exception.security.InvalidUserCredentialsException;
import com.kostenko.youtube.analytic.exception.security.UserNotFoundException;
import com.kostenko.youtube.analytic.exception.youtube.ChannelNotFoundException;
import com.kostenko.youtube.analytic.exception.youtube.VideosNotFoundException;
import com.kostenko.youtube.analytic.model.security.user.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.kostenko.youtube.analytic.exception.response.MockedExceptionResponseProvider.*;
import static com.kostenko.youtube.analytic.util.TestConstants.*;
import static com.kostenko.youtube.analytic.util.security.TestSecurityConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MockedDataSourceConfiguration.class)
class ExceptionResponseMapperTest {
    @Autowired
    private ExceptionResponseMapper exceptionResponseMapper;

    @Test
    void mapChannelNotFoundExceptionTest() {
        ChannelNotFoundException classNotFoundException = new ChannelNotFoundException(TEST_CHANNEL_ID);

        AnalyticExceptionResponse expected = getMockedChannelNotFoundExceptionResponse();
        AnalyticExceptionResponse actual = exceptionResponseMapper.mapExceptionToExceptionResponse(classNotFoundException);

        assertEquals(expected, actual);
    }

    @Test
    void mapVideosNotFoundExceptionTest() {
        VideosNotFoundException classNotFoundException = new VideosNotFoundException(TEST_CHANNEL_ID);

        AnalyticExceptionResponse expected = getMockedVideosNotFoundExceptionResponse();
        AnalyticExceptionResponse actual = exceptionResponseMapper.mapExceptionToExceptionResponse(classNotFoundException);

        assertEquals(expected, actual);
    }

    @Test
    void mapIncorrectJwtExceptionTest() {
        IncorrectJwtException incorrectJwtException = new IncorrectJwtException(TEST_DATE, TEST_TOKEN);

        JwtExceptionResponse excepted = getMockedJwtExceptionResponse();
        JwtExceptionResponse actual = exceptionResponseMapper.mapExceptionToExceptionResponse(incorrectJwtException);

        assertEquals(excepted, actual);
    }

    @Test
    void mapAccountUnavailableExceptionTest() {
        UserStatus mockedUserStatus = mock(UserStatus.class);
        when(mockedUserStatus.isAccountExpired()).thenReturn(true);
        AccountUnavailableException accountUnavailableException = new AccountUnavailableException(TEST_USERNAME, mockedUserStatus);

        AuthorizationExceptionResponse excepted = getMockedAccountUnavailableExceptionResponse();
        AuthorizationExceptionResponse actual = exceptionResponseMapper.mapExceptionToExceptionResponse(accountUnavailableException);

        assertEquals(excepted, actual);
    }

    @Test
    void mapInvalidUserCredentialsExceptionTest() {
        InvalidUserCredentialsException invalidUserCredentialsException = new InvalidUserCredentialsException(TEST_USERNAME);

        AuthorizationExceptionResponse excepted = getMockedInvalidUserCredentialsExceptionResponse();
        AuthorizationExceptionResponse actual = exceptionResponseMapper.mapExceptionToExceptionResponse(invalidUserCredentialsException);

        assertEquals(excepted, actual);
    }

    @Test
    void mapUserNotFoundExceptionTest() {
        UserNotFoundException userNotFoundException = new UserNotFoundException(TEST_USERNAME);

        AuthorizationExceptionResponse excepted = getMockedUserNotFoundExceptionResponse();
        AuthorizationExceptionResponse actual = exceptionResponseMapper.mapExceptionToExceptionResponse(userNotFoundException);

        assertEquals(excepted, actual);
    }
}
