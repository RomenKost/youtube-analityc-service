package com.kostenko.youtube.analytic.service.service.database.reader.client.impl;

import com.kostenko.youtube.analytic.service.entity.ChannelEntity;
import com.kostenko.youtube.analytic.service.entity.Entities;
import com.kostenko.youtube.analytic.service.entity.VideoEntity;
import com.kostenko.youtube.analytic.service.exception.YoutubeChannelNotFoundException;
import com.kostenko.youtube.analytic.service.exception.YoutubeVideosNotFoundException;
import com.kostenko.youtube.analytic.service.mapper.database.manager.ChannelIdMapper;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeChannelMapper;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeVideoMapper;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Models;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;
import com.kostenko.youtube.analytic.service.repository.ChannelsInfoRepository;
import com.kostenko.youtube.analytic.service.repository.VideosInfoRepository;
import com.kostenko.youtube.analytic.service.service.database.reader.client.DatabaseReaderClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = SQLYoutubeDatabaseReaderClient.class)
class SQLYoutubeDatabaseReaderClientTest {
    @MockBean
    private ChannelsInfoRepository channelsInfoRepository;
    @MockBean
    private VideosInfoRepository videosInfoRepository;
    @MockBean
    private ChannelIdMapper channelIdMapper;
    @MockBean
    private YoutubeChannelMapper channelMapper;
    @MockBean
    private YoutubeVideoMapper videoMapper;

    @Autowired
    private DatabaseReaderClient databaseReaderClient;

    @Test
    void getChannelTest() {
        Channel expected = Models.getChannel();
        ChannelEntity channelEntity = Entities.getChannelEntity();

        Mockito.when(channelsInfoRepository.findById("any id"))
                .thenReturn(Optional.of(channelEntity));
        Mockito.when(channelMapper.channelEntityToChannel(channelEntity))
                .thenReturn(expected);

        Channel actual = databaseReaderClient.getChannel("any id");

        assertEquals(expected, actual);
    }

    @Test
    void getVideosTest() {
        List<Video> expected = Models.getVideos();
        ChannelEntity channelEntity = Entities.getChannelEntity();
        List<VideoEntity> videoEntities = Entities.getVideoEntities();

        Mockito.when(channelIdMapper.stringToChannelEntity("any id"))
                .thenReturn(channelEntity);
        Mockito.when(videosInfoRepository.findAllByChannelId(channelEntity))
                .thenReturn(videoEntities);
        Mockito.when(videoMapper.videoEntitiesToVideos(videoEntities))
                .thenReturn(expected);

        List<Video> actual = databaseReaderClient.getVideos("any id");

        assertEquals(expected, actual);
    }

    @Test
    void getChannelWhenChannelEntityIsEmptyThrowYoutubeChannelNotFoundException() {
        YoutubeChannelNotFoundException expected = new YoutubeChannelNotFoundException("any id");

        Mockito.when(channelsInfoRepository.findById("any id"))
                .thenReturn(Optional.empty());

        YoutubeChannelNotFoundException actual = assertThrows(
                YoutubeChannelNotFoundException.class,
                () -> databaseReaderClient.getChannel("any id")
        );

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getMessage(), expected.getMessage());
    }

    @Test
    void getVideosWhenVideoEntitiesIsEmptyThrowYoutubeVideosNotFoundException() {
        YoutubeVideosNotFoundException expected = new YoutubeVideosNotFoundException("any id");
        ChannelEntity channelEntity = Entities.getChannelEntity();

        Mockito.when(channelIdMapper.stringToChannelEntity("any id"))
                .thenReturn(channelEntity);
        Mockito.when(videosInfoRepository.findAllByChannelId(channelEntity))
                .thenReturn(List.of());

        YoutubeVideosNotFoundException actual = assertThrows(
                YoutubeVideosNotFoundException.class,
                () -> databaseReaderClient.getVideos("any id")
        );

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getMessage(), expected.getMessage());
    }
}
