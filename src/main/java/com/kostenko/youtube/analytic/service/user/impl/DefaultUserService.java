package com.kostenko.youtube.analytic.service.user.impl;

import com.kostenko.youtube.analytic.exception.security.UserNotFoundException;
import com.kostenko.youtube.analytic.service.user.mapper.UserMapper;
import com.kostenko.youtube.analytic.model.security.user.User;
import com.kostenko.youtube.analytic.repository.security.UsersRepository;
import com.kostenko.youtube.analytic.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.kostenko.youtube.analytic.exception.message.ErrorMessages.*;

@Slf4j
@Service
@AllArgsConstructor
public class DefaultUserService implements UserService {
    private final UsersRepository usersRepository;
    private final UserMapper userMapper;

    @Override
    public User getUser(String username) {
        log.info("Loading information about user with username = " + username + "...");
        Optional<User> userOptional = usersRepository.findById(username)
                .map(userMapper::userEntityToYoutubeAnalyticUser);
        if (userOptional.isEmpty()) {
            UserNotFoundException exception = new UserNotFoundException(username);
            log.error(USER_NOT_FOUND, username, exception);
            throw exception;
        }
        log.info("Information about user with username = " + username + " was loaded.");
        return userOptional.get();
    }
}
