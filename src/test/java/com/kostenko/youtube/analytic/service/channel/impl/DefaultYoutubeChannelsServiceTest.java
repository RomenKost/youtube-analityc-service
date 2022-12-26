package com.kostenko.youtube.analytic.service.channel.impl;

import com.kostenko.youtube.analytic.config.MockedDataSourceConfiguration;
import com.kostenko.youtube.analytic.exception.youtube.ChannelNotFoundException;
import com.kostenko.youtube.analytic.exception.youtube.VideosNotFoundException;
import com.kostenko.youtube.analytic.service.channel.mapper.channel.YoutubeChannelMapper;
import com.kostenko.youtube.analytic.service.channel.mapper.video.YoutubeVideoMapper;
import com.kostenko.youtube.analytic.model.youtube.Channel;
import com.kostenko.youtube.analytic.model.youtube.Video;
import com.kostenko.youtube.analytic.repository.youtube.ChannelRepository;
import com.kostenko.youtube.analytic.repository.youtube.VideosInfoRepository;
import com.kostenko.youtube.analytic.repository.youtube.entity.ChannelEntity;
import com.kostenko.youtube.analytic.repository.youtube.entity.VideoEntity;
import com.kostenko.youtube.analytic.repository.youtube.ChannelsInfoRepository;
import com.kostenko.youtube.analytic.service.channel.YoutubeChannelsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.kostenko.youtube.analytic.repository.youtube.entity.MockedYoutubeEntityProvider.*;
import static com.kostenko.youtube.analytic.model.youtube.MockedYoutubeModelsProvider.*;
import static com.kostenko.youtube.analytic.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MockedDataSourceConfiguration.class)
class DefaultYoutubeChannelsServiceTest {
    @MockBean
    private ChannelsInfoRepository channelsInfoRepository;
    @MockBean
    private VideosInfoRepository videosInfoRepository;
    @MockBean
    private ChannelRepository channelRepository;

    @MockBean
    private YoutubeChannelMapper channelMapper;
    @MockBean
    private YoutubeVideoMapper videoMapper;

    @Autowired
    private YoutubeChannelsService youtubeChannelsService;

    @Test
    void getChannelTest() {
        Channel expected = getMockedChannel();

        when(channelsInfoRepository.findById(TEST_CHANNEL_ID))
                .thenReturn(Optional.of(getMockedChannelEntity()));
        when(channelMapper.channelEntityToChannel(getMockedChannelEntity()))
                .thenReturn(getMockedChannel());

        Channel actual = youtubeChannelsService.getChannel(TEST_CHANNEL_ID);

        assertEquals(expected, actual);
    }

    @Test
    void getVideosTest() {
        List<Video> expected = getMockedVideoList();

        when(videosInfoRepository.findAllByChannelId(getMockedChannelEntity()))
                .thenReturn(getMockedVideoEntityList());
        when(videoMapper.videoEntitiesToVideos(getMockedVideoEntityList()))
                .thenReturn(expected);

        List<Video> actual = youtubeChannelsService.getVideos(TEST_CHANNEL_ID);

        assertEquals(expected, actual);
    }

    @Test
    void getChannelWhenChannelEntityIsEmptyThrowYoutubeChannelNotFoundException() {
        ChannelNotFoundException expected = new ChannelNotFoundException(TEST_CHANNEL_ID);

        when(channelsInfoRepository.findById(TEST_CHANNEL_ID))
                .thenReturn(Optional.empty());

        ChannelNotFoundException actual = assertThrows(
                ChannelNotFoundException.class,
                () -> youtubeChannelsService.getChannel(TEST_CHANNEL_ID)
        );

        assertEquals(expected, actual);
    }

    @Test
    void getVideosWhenVideoEntitiesIsEmptyThrowYoutubeVideosNotFoundException() {
        VideosNotFoundException expected = new VideosNotFoundException(TEST_CHANNEL_ID);

        when(videosInfoRepository.findAllByChannelId(getMockedChannelEntity()))
                .thenReturn(List.of());

        VideosNotFoundException actual = assertThrows(
                VideosNotFoundException.class,
                () -> youtubeChannelsService.getVideos(TEST_CHANNEL_ID)
        );

        assertEquals(expected, actual);
    }


    @Test
    void getChannelIdsTest() {
        List<String> expected = List.of(TEST_CHANNEL_ID, TEST_CHANNEL_ID, TEST_CHANNEL_ID);

        when(channelRepository.findAll())
                .thenReturn(getChannelIdEntities());

        List<String> actual = youtubeChannelsService.getChannelIds();

        assertEquals(expected, actual);
    }

    @Test
    void saveReportTest() {
        ChannelEntity expected = getMockedChannelEntity();
        List<VideoEntity> videoEntities = getMockedVideoEntityList();

        videoEntities.forEach(videoEntity -> videoEntity.setChannelId(expected));
        expected.setVideoEntities(new HashSet<>(videoEntities));

        when(channelMapper.channelToChannelEntity(getMockedChannel()))
                .thenReturn(getMockedChannelEntity());
        when(videoMapper.videosToVideoEntities(getMockedVideoList()))
                .thenReturn(getMockedVideoEntityList());

        youtubeChannelsService.saveChannel(getMockedChannel(), getMockedVideoList());

        verify(channelsInfoRepository).save(expected);
    }
}
