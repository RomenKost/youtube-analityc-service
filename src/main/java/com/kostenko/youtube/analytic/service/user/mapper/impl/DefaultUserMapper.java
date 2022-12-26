package com.kostenko.youtube.analytic.service.user.mapper.impl;

import com.kostenko.youtube.analytic.repository.security.entity.UserEntity;
import com.kostenko.youtube.analytic.service.user.mapper.UserMapper;
import com.kostenko.youtube.analytic.model.security.user.User;
import com.kostenko.youtube.analytic.service.security.encoder.PasswordHashEncoder;
import com.kostenko.youtube.analytic.model.security.user.UserStatus;
import com.kostenko.youtube.analytic.model.security.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultUserMapper implements UserMapper {
    private final PasswordHashEncoder hashEncoder;

    @Override
    public User userEntityToYoutubeAnalyticUser(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        String username = userEntity.getUsername();
        String password = hashEncoder.encode(userEntity.getPassword());
        UserRole role = userEntity.getRole();
        UserStatus status = userEntity.getStatus();

        User youtubeAnalyticUser = new User();
        youtubeAnalyticUser.setPassword(password);
        youtubeAnalyticUser.setUsername(username);
        youtubeAnalyticUser.setRole(role);
        youtubeAnalyticUser.setStatus(status);
        return youtubeAnalyticUser;
    }
}
