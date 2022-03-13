package com.kostenko.youtube.analytic.service.security.user.processor.impl;

import com.kostenko.youtube.analytic.exception.security.YoutubeAnalyticUserNotFoundException;
import com.kostenko.youtube.analytic.mapper.security.user.UserMapper;
import com.kostenko.youtube.analytic.model.security.YoutubeAnalyticUser;
import com.kostenko.youtube.analytic.repository.security.UsersRepository;
import com.kostenko.youtube.analytic.service.security.user.processor.YoutubeAnalyticUserProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.kostenko.youtube.analytic.exception.message.ErrorMessages.*;

@Slf4j
@Service
@AllArgsConstructor
public class DefaultYoutubeAnalyticUserProcessor implements YoutubeAnalyticUserProcessor {
    private final UsersRepository usersRepository;
    private final UserMapper userMapper;

    @Override
    public YoutubeAnalyticUser getUser(String username) {
        log.info("Loading information about user with username = " + username + "...");
        Optional<YoutubeAnalyticUser> userOptional = usersRepository.findById(username)
                .map(userMapper::userEntityToYoutubeAnalyticUser);
        if (userOptional.isEmpty()) {
            YoutubeAnalyticUserNotFoundException exception = new YoutubeAnalyticUserNotFoundException(username);
            log.error(String.format(USER_NOT_FOUND, username), exception);
            throw exception;
        }
        log.info("Information about user with username = " + username + " was loaded.");
        return userOptional.get();
    }
}
