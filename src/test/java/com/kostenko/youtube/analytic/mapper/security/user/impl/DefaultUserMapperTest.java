package com.kostenko.youtube.analytic.mapper.security.user.impl;

import com.kostenko.youtube.analytic.entity.Entities;
import com.kostenko.youtube.analytic.entity.security.UserEntity;
import com.kostenko.youtube.analytic.model.Models;
import com.kostenko.youtube.analytic.model.security.YoutubeAnalyticUser;
import com.kostenko.youtube.analytic.security.encoder.PasswordHashEncoder;
import com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserStatus;
import com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserRole;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = DefaultUserMapper.class)
class DefaultUserMapperTest {
    @MockBean
    private PasswordHashEncoder hashEncoder;

    @Mock
    private YoutubeAnalyticUserRole userRole;
    @Mock
    private YoutubeAnalyticUserStatus userStatus;

    @Autowired
    private DefaultUserMapper mapper;

    @Test
    void userEntityToYoutubeAnalyticUserTest() {
        UserEntity userEntity = Entities.getUserEntity(userRole, userStatus);
        YoutubeAnalyticUser expected = Models.getUser(userRole, userStatus);
        when(hashEncoder.encode(userEntity.getPassword()))
                .thenReturn(expected.getPassword());

        YoutubeAnalyticUser actual = mapper.userEntityToYoutubeAnalyticUser(userEntity);

        assertEquals(expected, actual);
    }

    @Test
    void userEntityToYoutubeAnalyticUserTestWhenEntityIsNullReturnNullTest() {
        assertNull(mapper.userEntityToYoutubeAnalyticUser(null));
    }
}
