package com.kostenko.youtube.analytic.model.security;

import com.kostenko.youtube.analytic.model.security.jwt.Jwt;
import com.kostenko.youtube.analytic.model.security.user.User;
import lombok.experimental.UtilityClass;

import static com.kostenko.youtube.analytic.util.security.TestSecurityConstants.*;

@UtilityClass
public class MockedSecurityModelsProvider {
    public Jwt getMockedJwt() {
        Jwt jwt = new Jwt();
        jwt.setUsername(TEST_USERNAME);
        jwt.setToken(TEST_TOKEN);
        jwt.setRole(TEST_ROLE);
        jwt.setExpiration(TEST_EXPIRATION);
        return jwt;
    }

    public User getMockedUser() {
        User user = new User();
        user.setUsername(TEST_USERNAME);
        user.setPassword(TEST_PASSWORD);
        user.setRole(TEST_ROLE);
        user.setStatus(TEST_USER_STATUS);
        return user;
    }
}
