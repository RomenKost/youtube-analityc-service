package com.kostenko.youtube.analytic.service.user.impl;

import com.kostenko.youtube.analytic.config.MockedDataSourceConfiguration;
import com.kostenko.youtube.analytic.exception.security.UserNotFoundException;
import com.kostenko.youtube.analytic.service.user.mapper.UserMapper;
import com.kostenko.youtube.analytic.model.security.user.User;
import com.kostenko.youtube.analytic.repository.security.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.kostenko.youtube.analytic.model.security.MockedSecurityModelsProvider.*;
import static com.kostenko.youtube.analytic.repository.security.entity.MockedSecurityEntityProvider.*;
import static com.kostenko.youtube.analytic.util.security.TestSecurityConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MockedDataSourceConfiguration.class)
class DefaultUserServiceTest {
    @Autowired
    private UsersRepository usersRepository;
    @MockBean
    private UserMapper userMapper;

    @Autowired
    private DefaultUserService userService;

    @Test
    void getUserTest() {
        User expected = getMockedUser();

        when(usersRepository.findById(TEST_USERNAME))
                .thenReturn(Optional.of(getMockedUserEntity()));
        when(userMapper.userEntityToYoutubeAnalyticUser(getMockedUserEntity()))
                .thenReturn(expected);

        User actual = userService.getUser(TEST_USERNAME);

        assertEquals(expected, actual);
    }

    @Test
    void getUserWhenUserIsNotExistThrowYoutubeAnalyticUserNotFoundExceptionTest() {
        UserNotFoundException expected = new UserNotFoundException(TEST_USERNAME);

        when(usersRepository.findById(TEST_USERNAME))
                .thenReturn(Optional.empty());

        UserNotFoundException actual = assertThrows(
                UserNotFoundException.class,
                () -> userService.getUser(TEST_USERNAME)
        );

        assertEquals(expected, actual);
    }
}
