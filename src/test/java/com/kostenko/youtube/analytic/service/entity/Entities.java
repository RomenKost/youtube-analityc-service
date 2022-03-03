package com.kostenko.youtube.analytic.service.entity;

import lombok.experimental.UtilityClass;

import java.util.Date;
import java.util.List;

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
}
