package com.kostenko.youtube.analytic.exception.response;

import com.kostenko.youtube.analytic.exception.security.AccountUnavailableException;
import com.kostenko.youtube.analytic.exception.security.IncorrectJwtException;
import com.kostenko.youtube.analytic.exception.security.InvalidUserCredentialsException;
import com.kostenko.youtube.analytic.exception.security.UserNotFoundException;
import com.kostenko.youtube.analytic.exception.youtube.ChannelNotFoundException;
import com.kostenko.youtube.analytic.exception.youtube.VideosNotFoundException;
import lombok.experimental.UtilityClass;

import static com.kostenko.youtube.analytic.util.TestConstants.*;
import static com.kostenko.youtube.analytic.util.security.TestSecurityConstants.*;

@UtilityClass
public class MockedExceptionResponseProvider {
    public AnalyticExceptionResponse getMockedChannelNotFoundExceptionResponse() {
        AnalyticExceptionResponse analyticExceptionResponse = new AnalyticExceptionResponse();
        analyticExceptionResponse.setChannelId(TEST_CHANNEL_ID);
        analyticExceptionResponse.setExceptionClassName(ChannelNotFoundException.class.getSimpleName());
        analyticExceptionResponse.setMessage("Channel with id = " + TEST_CHANNEL_ID + " wasn't found.");
        return analyticExceptionResponse;
    }

    public AnalyticExceptionResponse getMockedVideosNotFoundExceptionResponse() {
        AnalyticExceptionResponse analyticExceptionResponse = new AnalyticExceptionResponse();
        analyticExceptionResponse.setChannelId(TEST_CHANNEL_ID);
        analyticExceptionResponse.setExceptionClassName(VideosNotFoundException.class.getSimpleName());
        analyticExceptionResponse.setMessage("Videos of channel with id = " + TEST_CHANNEL_ID + " weren't found.");
        return analyticExceptionResponse;
    }

    public JwtExceptionResponse getMockedJwtExceptionResponse() {
        JwtExceptionResponse jwtExceptionResponse = new JwtExceptionResponse();
        jwtExceptionResponse.setJwt(TEST_TOKEN);
        jwtExceptionResponse.setExpiration(TEST_DATE);
        jwtExceptionResponse.setExceptionClassName(IncorrectJwtException.class.getSimpleName());
        jwtExceptionResponse.setMessage("JWT is stitched (expiration = 2020-09-13T12:26:40.000Z).");
        return jwtExceptionResponse;
    }

    public AuthorizationExceptionResponse getMockedAccountUnavailableExceptionResponse() {
        AuthorizationExceptionResponse authorizationExceptionResponse = new AuthorizationExceptionResponse();
        authorizationExceptionResponse.setExceptionClassName(AccountUnavailableException.class.getSimpleName());
        authorizationExceptionResponse.setUsername(TEST_USERNAME);
        authorizationExceptionResponse.setReason("account expired");
        authorizationExceptionResponse.setMessage("Account with username = " + TEST_USERNAME + " is unavailable.");
        return authorizationExceptionResponse;
    }

    public AuthorizationExceptionResponse getMockedInvalidUserCredentialsExceptionResponse() {
        AuthorizationExceptionResponse authorizationExceptionResponse = new AuthorizationExceptionResponse();
        authorizationExceptionResponse.setExceptionClassName(InvalidUserCredentialsException.class.getSimpleName());
        authorizationExceptionResponse.setUsername(TEST_USERNAME);
        authorizationExceptionResponse.setMessage("Invalid credentials for username = " + TEST_USERNAME + ".");
        return authorizationExceptionResponse;
    }

    public AuthorizationExceptionResponse getMockedUserNotFoundExceptionResponse() {
        AuthorizationExceptionResponse authorizationExceptionResponse = new AuthorizationExceptionResponse();
        authorizationExceptionResponse.setExceptionClassName(UserNotFoundException.class.getSimpleName());
        authorizationExceptionResponse.setUsername(TEST_USERNAME);
        authorizationExceptionResponse.setMessage("User with username = " + TEST_USERNAME + " wasn't found.");
        return authorizationExceptionResponse;
    }
}
