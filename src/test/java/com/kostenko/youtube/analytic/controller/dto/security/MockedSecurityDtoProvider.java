package com.kostenko.youtube.analytic.controller.dto.security;

import lombok.experimental.UtilityClass;

import static com.kostenko.youtube.analytic.util.security.TestSecurityConstants.*;

@UtilityClass
public class MockedSecurityDtoProvider {
    public UserCredentialsDto getMockedUserCredentialsDto() {
        UserCredentialsDto userCredentialsDto = new UserCredentialsDto();
        userCredentialsDto.setUsername(TEST_USERNAME);
        userCredentialsDto.setPassword(TEST_PASSWORD);
        return userCredentialsDto;
    }

    public JwtDto getMockedJwtDto() {
        JwtDto jwtDto = new JwtDto();
        jwtDto.setUsername(TEST_USERNAME);
        jwtDto.setRole(TEST_ROLE);
        jwtDto.setExpiration(TEST_EXPIRATION);
        jwtDto.setToken(TEST_TOKEN);
        return jwtDto;
    }
}
