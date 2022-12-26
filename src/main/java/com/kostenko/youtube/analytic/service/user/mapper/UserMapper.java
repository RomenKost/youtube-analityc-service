package com.kostenko.youtube.analytic.service.user.mapper;

import com.kostenko.youtube.analytic.repository.security.entity.UserEntity;
import com.kostenko.youtube.analytic.model.security.user.User;

public interface UserMapper {
    User userEntityToYoutubeAnalyticUser(UserEntity userEntity);
}
