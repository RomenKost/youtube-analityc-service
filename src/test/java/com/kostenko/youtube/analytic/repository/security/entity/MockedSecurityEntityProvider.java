package com.kostenko.youtube.analytic.repository.security.entity;

import lombok.experimental.UtilityClass;

import static com.kostenko.youtube.analytic.util.TestConstants.*;
import static com.kostenko.youtube.analytic.util.security.TestSecurityConstants.*;

@UtilityClass
public class MockedSecurityEntityProvider {
    public UserEntity getMockedUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(TEST_USERNAME);
        userEntity.setPassword(TEST_HASHED_PASSWORD);
        userEntity.setRole(TEST_ROLE);
        userEntity.setStatus(TEST_USER_STATUS);
        userEntity.setCreated(TEST_DATE);
        userEntity.setUpdated(TEST_DATE);
        return userEntity;
    }
}
