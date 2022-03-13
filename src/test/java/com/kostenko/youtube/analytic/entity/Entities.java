package com.kostenko.youtube.analytic.entity;

import com.kostenko.youtube.analytic.entity.security.UserEntity;
import com.kostenko.youtube.analytic.entity.youtube.ChannelEntity;
import com.kostenko.youtube.analytic.entity.youtube.ChannelIdEntity;
import com.kostenko.youtube.analytic.entity.youtube.VideoEntity;
import com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserStatus;
import com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserRole;
import lombok.experimental.UtilityClass;

import java.util.Date;
import java.util.List;

import static com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserStatus.*;
import static com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserRole.*;

@UtilityClass
public class Entities {
    public List<VideoEntity> getVideoEntities() {
        VideoEntity firstEntity = getVideoEntity();
        VideoEntity secondEntity = new VideoEntity();

        secondEntity.setPublishedAt(new Date(1_700_000_000_000L));
        secondEntity.setDescription("another description");
        secondEntity.setTitle("another title");
        secondEntity.setVideoId("another id");

        return List.of(firstEntity, secondEntity);
    }

    public ChannelEntity getChannelEntity() {
        ChannelEntity channelEntity = new ChannelEntity();

        channelEntity.setPublishedAt(new Date(1_600_000_000_000L));
        channelEntity.setDescription("any description");
        channelEntity.setTitle("any title");
        channelEntity.setCountry("ac");
        channelEntity.setId("any id");

        return channelEntity;
    }

    public VideoEntity getVideoEntity() {
        VideoEntity videoEntity = new VideoEntity();

        videoEntity.setPublishedAt(new Date(1_600_000_000_000L));
        videoEntity.setDescription("any description");
        videoEntity.setTitle("any title");
        videoEntity.setVideoId("any id");

        return videoEntity;
    }

    public List<ChannelIdEntity> getChannelIdEntities() {
        ChannelIdEntity firstChannelIdEntity = new ChannelIdEntity();
        ChannelIdEntity secondChannelIdEntity = new ChannelIdEntity();

        firstChannelIdEntity.setId("any id");
        secondChannelIdEntity.setId("another id");

        return List.of(firstChannelIdEntity, secondChannelIdEntity);
    }

    public UserEntity getUserEntity(YoutubeAnalyticUserRole userRole, YoutubeAnalyticUserStatus userStatus) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("any username");
        userEntity.setPassword("any hashed password");
        userEntity.setRole(userRole);
        userEntity.setStatus(userStatus);
        userEntity.setCreated(new Date(1_600_000_000_000L));
        userEntity.setUpdated(new Date(1_700_000_000_000L));
        return userEntity;
    }
}
