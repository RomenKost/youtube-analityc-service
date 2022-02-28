package com.kostenko.youtube.analytic.service.entity;

import lombok.experimental.UtilityClass;

import java.util.Date;
import java.util.List;

@UtilityClass
public class Entities {
    public List<VideoEntity> getVideoEntities() {
        VideoEntity firstEntity = getVideoEntity();
        VideoEntity secondEntity = VideoEntity.builder()
                .publishedAt(new Date(1_700_000_000_000L))
                .description("another description")
                .title("another title")
                .videoId("another id")
                .build();
        return List.of(firstEntity, secondEntity);
    }

    public ChannelEntity getChannelEntity() {
        return ChannelEntity.builder()
                .publishedAt(new Date(1_600_000_000_000L))
                .description("any description")
                .title("any title")
                .country("ac")
                .id("any id")
                .build();
    }

    public VideoEntity getVideoEntity() {
        return VideoEntity.builder()
                .publishedAt(new Date(1_600_000_000_000L))
                .description("any description")
                .title("any title")
                .videoId("any id")
                .build();
    }

    public List<ChannelIdEntity> getChannelIdEntities() {
        return List.of(new ChannelIdEntity("any id"), new ChannelIdEntity("another id"));
    }
}
