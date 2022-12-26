package com.kostenko.youtube.analytic.model.youtube;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.kostenko.youtube.analytic.util.TestConstants.*;

@UtilityClass
public class MockedYoutubeModelsProvider {
    public List<Channel> getMockedChannelList() {
        return List.of(getMockedChannel());
    }

    public Channel getMockedChannel() {
        Channel channel = new Channel();
        channel.setId(TEST_CHANNEL_ID);
        channel.setCountry(TEST_COUNTRY);
        channel.setTitle(TEST_TEXT_FIELD);
        channel.setDescription(TEST_TEXT_FIELD);
        channel.setPublishedAt(TEST_DATE);
        return channel;
    }

    public Video getMockedVideo() {
        Video video = new Video();
        video.setVideoId(TEST_VIDEO_ID);
        video.setDescription(TEST_TEXT_FIELD);
        video.setTitle(TEST_TEXT_FIELD);
        video.setPublishedAt(TEST_DATE);
        return video;
    }

    public List<Video> getMockedVideoList() {
        return getMockedVideoList(3);
    }

    public List<Video> getMockedVideoList(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> getMockedVideo())
                .collect(Collectors.toList());
    }
}
