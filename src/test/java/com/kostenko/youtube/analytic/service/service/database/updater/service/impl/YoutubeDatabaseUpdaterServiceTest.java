package com.kostenko.youtube.analytic.service.service.database.updater.service.impl;

import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Models;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;
import com.kostenko.youtube.analytic.service.service.database.updater.client.DatabaseUpdaterClient;
import com.kostenko.youtube.analytic.service.service.youtube.analytic.client.AnalyticClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest(classes = YoutubeDatabaseUpdaterService.class)
class YoutubeDatabaseUpdaterServiceTest {
    @MockBean
    private DatabaseUpdaterClient databaseClient;
    @MockBean
    private AnalyticClient analyticClient;

    @Autowired
    private YoutubeDatabaseUpdaterService databaseUpdaterService;

    @BeforeEach
    void clear() {
        Mockito.reset(databaseClient, analyticClient);
        Mockito.clearInvocations(databaseClient, analyticClient);
    }

    @Test
    void processChannelsTest() {
        List<String> channelIds = List.of("any id", "another id");
        Channel channel = Models.getChannel();
        List<Video> videos = Models.getVideos();

        Mockito.when(databaseClient.getChannelIds())
                .thenReturn(channelIds);
        Mockito.when(analyticClient.getChannel(or(eq("any id"), eq("another id"))))
                .thenReturn(Optional.of(channel));
        Mockito.when(analyticClient.getVideos(or(eq("any id"), eq("another id")), eq("")))
                .thenReturn(videos);

        databaseUpdaterService.processChannels();

        Mockito.verify(databaseClient, Mockito.times(2))
                .saveReport(channel, videos);
    }
}
