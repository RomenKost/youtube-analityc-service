package com.kostenko.youtube.analytic.service.security.user.processor.impl;

import com.kostenko.youtube.analytic.entity.Entities;
import com.kostenko.youtube.analytic.entity.security.UserEntity;
import com.kostenko.youtube.analytic.exception.security.YoutubeAnalyticUserNotFoundException;
import com.kostenko.youtube.analytic.mapper.security.user.UserMapper;
import com.kostenko.youtube.analytic.model.Models;
import com.kostenko.youtube.analytic.model.security.YoutubeAnalyticUser;
import com.kostenko.youtube.analytic.repository.security.UsersRepository;
import com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserRole;
import com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = DefaultYoutubeAnalyticUserProcessor.class)
class DefaultYoutubeAnalyticUserProcessorTest {
    @MockBean
    private UsersRepository usersRepository;
    @MockBean
    private UserMapper userMapper;

    @Mock
    private YoutubeAnalyticUserRole userRole;
    @Mock
    private YoutubeAnalyticUserStatus userStatus;

    @Autowired
    private DefaultYoutubeAnalyticUserProcessor userProcessor;

    @Test
    void getUserTest() {
        UserEntity userEntity = Entities.getUserEntity(userRole, userStatus);
        YoutubeAnalyticUser expected = Models.getUser(userRole, userStatus);
        when(usersRepository.findById("any username"))
                .thenReturn(Optional.of(userEntity));
        when(userMapper.userEntityToYoutubeAnalyticUser(userEntity))
                .thenReturn(expected);

        YoutubeAnalyticUser actual = userProcessor.getUser("any username");

        assertEquals(expected, actual);
    }

    @Test
    void getUserWhenUserIsNotExistThrowTest() {
        YoutubeAnalyticUserNotFoundException expected = new YoutubeAnalyticUserNotFoundException("any username");
        when(usersRepository.findById("any username"))
                .thenReturn(Optional.empty());

        YoutubeAnalyticUserNotFoundException actual = assertThrows(
                YoutubeAnalyticUserNotFoundException.class,
                () -> userProcessor.getUser("any username")
        );

        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getMessage(), actual.getMessage());
    }
}
