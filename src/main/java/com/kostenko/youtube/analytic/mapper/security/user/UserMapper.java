package com.kostenko.youtube.analytic.mapper.security.user;

import com.kostenko.youtube.analytic.entity.security.UserEntity;
import com.kostenko.youtube.analytic.model.security.YoutubeAnalyticUser;

public interface UserMapper {
    YoutubeAnalyticUser userEntityToYoutubeAnalyticUser(UserEntity userEntity);
}
