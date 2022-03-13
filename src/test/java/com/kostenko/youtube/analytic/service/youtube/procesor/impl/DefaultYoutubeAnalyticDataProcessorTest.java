package com.kostenko.youtube.analytic.service.youtube.procesor.impl;

import com.kostenko.youtube.analytic.entity.youtube.ChannelIdEntity;
import com.kostenko.youtube.analytic.mapper.youtube.channel.YoutubeChannelMapper;
import com.kostenko.youtube.analytic.mapper.youtube.video.YoutubeVideoMapper;
import com.kostenko.youtube.analytic.model.youtube.Channel;
import com.kostenko.youtube.analytic.model.Models;
import com.kostenko.youtube.analytic.model.youtube.Video;
import com.kostenko.youtube.analytic.repository.youtube.ChannelRepository;
import com.kostenko.youtube.analytic.repository.youtube.VideosInfoRepository;
import com.kostenko.youtube.analytic.entity.youtube.ChannelEntity;
import com.kostenko.youtube.analytic.entity.Entities;
import com.kostenko.youtube.analytic.entity.youtube.VideoEntity;
import com.kostenko.youtube.analytic.exception.youtube.YoutubeChannelNotFoundException;
import com.kostenko.youtube.analytic.exception.youtube.YoutubeVideosNotFoundException;
import com.kostenko.youtube.analytic.mapper.youtube.channel.id.ChannelIdMapper;
import com.kostenko.youtube.analytic.repository.youtube.ChannelsInfoRepository;
import com.kostenko.youtube.analytic.service.youtube.procesor.YoutubeAnalyticDataProcessor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = DefaultYoutubeAnalyticDataProcessor.class)
class DefaultYoutubeAnalyticDataProcessorTest {
    @MockBean
    private ChannelsInfoRepository channelsInfoRepository;
    @MockBean
    private VideosInfoRepository videosInfoRepository;
    @MockBean
    private ChannelRepository channelRepository;

    @MockBean
    private ChannelIdMapper channelIdMapper;
    @MockBean
    private YoutubeChannelMapper channelMapper;
    @MockBean
    private YoutubeVideoMapper videoMapper;

    @Autowired
    private YoutubeAnalyticDataProcessor youtubeAnalyticDataProcessor;

    DefaultYoutubeAnalyticDataProcessorTest() {
    }

    @Test
    void getChannelTest() {
        Channel expected = Models.getChannel();
        ChannelEntity channelEntity = Entities.getChannelEntity();

        when(channelsInfoRepository.findById("any id"))
                .thenReturn(Optional.of(channelEntity));
        when(channelMapper.channelEntityToChannel(channelEntity))
                .thenReturn(expected);

        Channel actual = youtubeAnalyticDataProcessor.getChannel("any id");

        assertEquals(expected, actual);
    }

    @Test
    void getVideosTest() {
        List<Video> expected = Models.getVideos();
        ChannelEntity channelEntity = Entities.getChannelEntity();
        List<VideoEntity> videoEntities = Entities.getVideoEntities();

        when(channelIdMapper.stringToChannelEntity("any id"))
                .thenReturn(channelEntity);
        when(videosInfoRepository.findAllByChannelId(channelEntity))
                .thenReturn(videoEntities);
        when(videoMapper.videoEntitiesToVideos(videoEntities))
                .thenReturn(expected);

        List<Video> actual = youtubeAnalyticDataProcessor.getVideos("any id");

        assertEquals(expected, actual);
    }

    @Test
    void getChannelWhenChannelEntityIsEmptyThrowYoutubeChannelNotFoundException() {
        YoutubeChannelNotFoundException expected = new YoutubeChannelNotFoundException("any id");

        when(channelsInfoRepository.findById("any id"))
                .thenReturn(Optional.empty());

        YoutubeChannelNotFoundException actual = assertThrows(
                YoutubeChannelNotFoundException.class,
                () -> youtubeAnalyticDataProcessor.getChannel("any id")
        );

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getMessage(), expected.getMessage());
    }

    @Test
    void getVideosWhenVideoEntitiesIsEmptyThrowYoutubeVideosNotFoundException() {
        YoutubeVideosNotFoundException expected = new YoutubeVideosNotFoundException("any id");
        ChannelEntity channelEntity = Entities.getChannelEntity();

        when(channelIdMapper.stringToChannelEntity("any id"))
                .thenReturn(channelEntity);
        when(videosInfoRepository.findAllByChannelId(channelEntity))
                .thenReturn(List.of());

        YoutubeVideosNotFoundException actual = assertThrows(
                YoutubeVideosNotFoundException.class,
                () -> youtubeAnalyticDataProcessor.getVideos("any id")
        );

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getMessage(), expected.getMessage());
    }


    @Test
    void getChannelIdsTest() {
        List<String> expected = List.of("any_id", "another_id");
        List<ChannelIdEntity> channelIdEntities = Entities.getChannelIdEntities();
        when(channelRepository.findAll())
                .thenReturn(channelIdEntities);
        when(channelIdMapper.channelIdEntitiesToStrings(channelIdEntities))
                .thenReturn(expected);

        List<String> actual = youtubeAnalyticDataProcessor.getChannelIds();

        assertEquals(expected, actual);
    }

    @Test
    void saveReportTest() {
        ChannelEntity expected = Entities.getChannelEntity();
        List<VideoEntity> videoEntities = Entities.getVideoEntities();

        videoEntities.forEach(videoEntity -> videoEntity.setChannelId(expected));
        expected.setVideoEntities(new HashSet<>(videoEntities));

        when(channelMapper.channelToChannelEntity(Models.getChannel()))
                .thenReturn(Entities.getChannelEntity());
        when(videoMapper.videosToVideoEntities(Models.getVideos()))
                .thenReturn(Entities.getVideoEntities());

        youtubeAnalyticDataProcessor.saveReport(Models.getChannel(), Models.getVideos());

        Mockito.verify(channelsInfoRepository).save(expected);
    }
}
