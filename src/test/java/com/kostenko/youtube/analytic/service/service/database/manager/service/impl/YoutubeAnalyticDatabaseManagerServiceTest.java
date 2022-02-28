package com.kostenko.youtube.analytic.service.service.database.manager.service.impl;

import com.kostenko.youtube.analytic.service.entity.ChannelIdEntity;
import com.kostenko.youtube.analytic.service.entity.Entities;
import com.kostenko.youtube.analytic.service.exception.handler.DatabaseManagerExceptionHandler;
import com.kostenko.youtube.analytic.service.mapper.database.manager.ChannelIdMapper;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Models;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;
import com.kostenko.youtube.analytic.service.service.database.manager.client.DatabaseClient;
import com.kostenko.youtube.analytic.service.service.youtube.analytic.service.AnalyticService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.TaskScheduler;

import java.util.List;

import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest(classes = YoutubeAnalyticDatabaseManagerService.class)
class YoutubeAnalyticDatabaseManagerServiceTest {
    @MockBean
    private DatabaseClient databaseClient;
    @MockBean
    private AnalyticService analyticService;
    @MockBean
    private ChannelIdMapper channelIdMapper;
    @MockBean
    private DatabaseManagerExceptionHandler exceptionHandler;

    @Autowired
    private YoutubeAnalyticDatabaseManagerService databaseManagerService;

    @BeforeEach
    void clear() {
        Mockito.reset(databaseClient, analyticService, channelIdMapper, exceptionHandler);
        Mockito.clearInvocations(databaseClient, analyticService, channelIdMapper, exceptionHandler);
    }

    @Test
    void processChannelsTest() {
        List<ChannelIdEntity> channelIdEntities = Entities.getChannelIdEntities();
        Channel channel = Models.getChannel();
        List<Video> videos = Models.getVideos();

        Mockito.when(databaseClient.getChannelIds())
                .thenReturn(channelIdEntities);
        Mockito.when(channelIdMapper.channelIdEntitiesToStrings(channelIdEntities))
                .thenReturn(List.of("any id", "another id"));
        Mockito.when(analyticService.getChannel(or(eq("any id"), eq("another id"))))
                .thenReturn(channel);
        Mockito.when(analyticService.getVideos(or(eq("any id"), eq("another id"))))
                .thenReturn(videos);

        databaseManagerService.processChannels();

        Mockito.verify(databaseClient, Mockito.times(2))
                .saveReport(channel, videos);
    }

    @Test
    void processChannelsWhenExceptionIsThrownTest() {
        RuntimeException excepted = new RuntimeException("excepted exception");

        Mockito.when(databaseClient.getChannelIds())
                .thenThrow(excepted);

        databaseManagerService.processChannels();

        Mockito.verify(exceptionHandler)
                .processThrowable(excepted);
    }
}
