package com.kostenko.youtube.analytic.repository.youtube.entity;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.kostenko.youtube.analytic.util.TestConstants.*;

@UtilityClass
public class MockedYoutubeEntityProvider {
    public List<VideoEntity> getMockedVideoEntityList() {
        return getMockedVideoEntityList(3);
    }

    public List<VideoEntity> getMockedVideoEntityList(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> getMockedVideoEntity())
                .collect(Collectors.toList());
    }

    public ChannelEntity getMockedChannelEntity() {
        ChannelEntity channelEntity = new ChannelEntity();
        channelEntity.setPublishedAt(TEST_DATE);
        channelEntity.setDescription(TEST_TEXT_FIELD);
        channelEntity.setTitle(TEST_TEXT_FIELD);
        channelEntity.setCountry(TEST_COUNTRY);
        channelEntity.setId(TEST_CHANNEL_ID);
        channelEntity.setLastCheck(TEST_DATE);
        return channelEntity;
    }

    public VideoEntity getMockedVideoEntity() {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setPublishedAt(TEST_DATE);
        videoEntity.setDescription(TEST_TEXT_FIELD);
        videoEntity.setTitle(TEST_TEXT_FIELD);
        videoEntity.setVideoId(TEST_VIDEO_ID);
        return videoEntity;
    }

    public ChannelIdEntity getMockedChannelIdEntity() {
        ChannelIdEntity channelIdEntity = new ChannelIdEntity();
        channelIdEntity.setId(TEST_CHANNEL_ID);
        return channelIdEntity;
    }

    public List<ChannelIdEntity> getChannelIdEntities() {
        return getChannelIdEntities(3);
    }

    public List<ChannelIdEntity> getChannelIdEntities(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> getMockedChannelIdEntity())
                .collect(Collectors.toList());
    }
}
