package com.kostenko.youtube.analytic.service.user.mapper.impl;

import com.kostenko.youtube.analytic.config.MockedDataSourceConfiguration;
import com.kostenko.youtube.analytic.repository.security.entity.UserEntity;
import com.kostenko.youtube.analytic.model.security.user.User;
import com.kostenko.youtube.analytic.service.security.encoder.PasswordHashEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.kostenko.youtube.analytic.model.security.MockedSecurityModelsProvider.*;
import static com.kostenko.youtube.analytic.repository.security.entity.MockedSecurityEntityProvider.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MockedDataSourceConfiguration.class)
class DefaultUserMapperTest {
    @MockBean
    private PasswordHashEncoder hashEncoder;

    @Autowired
    private DefaultUserMapper mapper;

    @Test
    void userEntityToYoutubeAnalyticUserTest() {
        UserEntity userEntity = getMockedUserEntity();
        User expected = getMockedUser();
        when(hashEncoder.encode(userEntity.getPassword()))
                .thenReturn(expected.getPassword());

        User actual = mapper.userEntityToYoutubeAnalyticUser(userEntity);

        assertEquals(expected, actual);
    }

    @Test
    void userEntityToYoutubeAnalyticUserTestWhenEntityIsNullReturnNullTest() {
        assertNull(mapper.userEntityToYoutubeAnalyticUser(null));
    }
}
