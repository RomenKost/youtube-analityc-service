package com.kostenko.youtube.analytic.mapper.security.user.impl;

import com.kostenko.youtube.analytic.entity.security.UserEntity;
import com.kostenko.youtube.analytic.mapper.security.user.UserMapper;
import com.kostenko.youtube.analytic.model.security.YoutubeAnalyticUser;
import com.kostenko.youtube.analytic.security.encoder.PasswordHashEncoder;
import com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserStatus;
import com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class DefaultUserMapper implements UserMapper {
    private PasswordHashEncoder hashEncoder;

    @Override
    public YoutubeAnalyticUser userEntityToYoutubeAnalyticUser(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        String username = userEntity.getUsername();
        String password = hashEncoder.encode(userEntity.getPassword());
        YoutubeAnalyticUserRole role = userEntity.getRole();
        YoutubeAnalyticUserStatus status = userEntity.getStatus();
        return new YoutubeAnalyticUser(username, password, role, status);
    }
}
