package com.kostenko.youtube.analytic.service.service.database.manager.service.impl;

import com.kostenko.youtube.analytic.service.exception.YoutubeChannelNotFoundException;
import com.kostenko.youtube.analytic.service.exception.handler.DatabaseManagerExceptionHandler;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Models;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;
import com.kostenko.youtube.analytic.service.service.database.manager.client.DatabaseClient;
import com.kostenko.youtube.analytic.service.service.youtube.analytic.service.AnalyticService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest(classes = YoutubeAnalyticDatabaseManagerServiceTest.class)
class YoutubeAnalyticDatabaseManagerServiceTest {
    @MockBean
    private DatabaseClient databaseClient;
    @MockBean
    private AnalyticService analyticService;
    @MockBean
    private DatabaseManagerExceptionHandler exceptionHandler;

    @BeforeEach
    void clear() {
        Mockito.reset(databaseClient, analyticService, exceptionHandler);
        Mockito.clearInvocations(databaseClient, analyticService, exceptionHandler);
    }

    @Test
    void processChannelsTest() {
        List<String> channelIds = List.of("any id", "another id");
        Channel channel = Models.getChannel();
        List<Video> videos = Models.getVideos();

        Mockito.when(databaseClient.getChannelIds())
                .thenReturn(channelIds);
        Mockito.when(analyticService.getChannel(or(eq("any id"), eq("another id"))))
                .thenReturn(channel);
        Mockito.when(analyticService.getVideos(or(eq("any id"), eq("another id"))))
                .thenReturn(videos);

        getDatabaseManagerService(true).processChannels();

        Mockito.verify(databaseClient, Mockito.times(2))
                .saveReport(channel, videos);
    }

    @Test
    void processChannelsWhenExceptionIsThrownTest() {
        RuntimeException excepted = new RuntimeException("excepted exception");

        Mockito.when(databaseClient.getChannelIds())
                .thenThrow(excepted);

        getDatabaseManagerService(true).processChannels();

        Mockito.verify(exceptionHandler)
                .processThrowable(excepted);
    }

    @Test
    void processChannelsWhenEnabledIsFalse() {
        getDatabaseManagerService(false).processChannels();

        Mockito.verify(databaseClient, Mockito.times(0))
                .getChannelIds();
    }

    @Test
    void getChannelsTest() {
        Channel expected = Models.getChannel();
        Mockito.when(databaseClient.getChannel("any id"))
                .thenReturn(expected);

        Channel actual = getDatabaseManagerService(false).getChannel("any id");

        assertEquals(expected, actual);
    }

    @Test
    void getChannelsWhenChannelsIsNullThrowYoutubeChannelNotFoundException() {
        YoutubeChannelNotFoundException excepted = new YoutubeChannelNotFoundException("any id");
        YoutubeAnalyticDatabaseManagerService databaseManagerService = getDatabaseManagerService(false);

        YoutubeChannelNotFoundException actual = assertThrows(
                YoutubeChannelNotFoundException.class,
                () -> databaseManagerService.getChannel("any id")
        );

        assertEquals(excepted.getId(), actual.getId());
        assertEquals(excepted.getMessage(), excepted.getMessage());
    }

    @Test
    void getVideosTest() {
        List<Video> expected = Models.getVideos();
        Mockito.when(databaseClient.getVideos("any id"))
                .thenReturn(expected);

        List<Video> actual = getDatabaseManagerService(false).getVideos("any id");

        assertEquals(expected, actual);
    }

    @Test
    void getVideosTestWhenVideosListIsEmptyThrowYoutubeChannelNotFoundException() {
        YoutubeChannelNotFoundException excepted = new YoutubeChannelNotFoundException("any id");
        YoutubeAnalyticDatabaseManagerService databaseManagerService = getDatabaseManagerService(false);
        Mockito.when(databaseClient.getVideos("any id"))
                .thenReturn(List.of());
        
        YoutubeChannelNotFoundException actual = assertThrows(
                YoutubeChannelNotFoundException.class,
                () -> databaseManagerService.getVideos("any id")
        );

        assertEquals(excepted.getId(), actual.getId());
        assertEquals(excepted.getMessage(), excepted.getMessage());
    }

    private YoutubeAnalyticDatabaseManagerService getDatabaseManagerService(boolean enabled) {
        return new YoutubeAnalyticDatabaseManagerService(
                databaseClient,
                analyticService,
                exceptionHandler,
                enabled
        );
    }
}
