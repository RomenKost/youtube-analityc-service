package com.kostenko.youtube.analytic.service.service.database.manager.client.impl;

import com.kostenko.youtube.analytic.service.entity.ChannelEntity;
import com.kostenko.youtube.analytic.service.entity.ChannelIdEntity;
import com.kostenko.youtube.analytic.service.entity.Entities;
import com.kostenko.youtube.analytic.service.entity.VideoEntity;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeChannelMapper;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeVideoMapper;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Models;
import com.kostenko.youtube.analytic.service.repository.ChannelRepository;
import com.kostenko.youtube.analytic.service.repository.ChannelsInfoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = SQLDatabaseClient.class)
class SQLDatabaseClientTest {
    @MockBean
    private ChannelRepository channelRepository;
    @MockBean
    private ChannelsInfoRepository channelsInfoRepository;
    @MockBean
    private YoutubeChannelMapper channelMapper;
    @MockBean
    private YoutubeVideoMapper videoMapper;

    @Autowired
    private SQLDatabaseClient databaseClient;

    @Test
    void getChannelIdsTest() {
        List<ChannelIdEntity> expected = Entities.getChannelIdEntities();
        Mockito.when(channelRepository.findAll())
                .thenReturn(expected);

        List<ChannelIdEntity> actual = databaseClient.getChannelIds();

        assertEquals(expected, actual);
    }

    @Test
    void saveReportTest() {
        ChannelEntity expected = Entities.getChannelEntity();
        List<VideoEntity> videoEntities = Entities.getVideoEntities();

        videoEntities.forEach(videoEntity -> videoEntity.setChannelId(expected));
        expected.setVideoEntities(new HashSet<>(videoEntities));

        Mockito.when(channelMapper.channelToChannelEntity(Models.getChannel()))
                .thenReturn(Entities.getChannelEntity());
        Mockito.when(videoMapper.videosToVideoEntities(Models.getVideos()))
                .thenReturn(Entities.getVideoEntities());

        databaseClient.saveReport(Models.getChannel(), Models.getVideos());

        Mockito.verify(channelsInfoRepository).save(expected);
    }
}
