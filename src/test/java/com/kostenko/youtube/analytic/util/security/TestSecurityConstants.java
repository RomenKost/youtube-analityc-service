package com.kostenko.youtube.analytic.util.security;

import com.kostenko.youtube.analytic.model.security.user.UserRole;
import com.kostenko.youtube.analytic.model.security.user.UserStatus;
import lombok.experimental.UtilityClass;

import static com.kostenko.youtube.analytic.model.security.user.UserRole.*;
import static org.mockito.Mockito.mock;

@UtilityClass
public class TestSecurityConstants {
    public final String TEST_USERNAME = "username";
    public final String TEST_PASSWORD = "password";

    public final String TEST_HASHED_PASSWORD = "hashed password";
    public final String TEST_INCORRECT_PASSWORD = "incorrect password";

    public final String TEST_TOKEN = "test token";
    public final long TEST_EXPIRATION = 3000;

    public final String TEST_SECRET_KEY = "any secret key";
    public final String TEST_AUTHORITY = "any authority";

    public final UserRole TEST_ROLE = ROLE_CUSTOMER;
    public final UserStatus TEST_USER_STATUS = mock(UserStatus.class);
}
