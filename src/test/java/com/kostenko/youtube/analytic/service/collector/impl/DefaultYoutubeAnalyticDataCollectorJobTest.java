package com.kostenko.youtube.analytic.service.collector.impl;

import com.kostenko.youtube.analytic.model.Channel;
import com.kostenko.youtube.analytic.model.Models;
import com.kostenko.youtube.analytic.model.Video;
import com.kostenko.youtube.analytic.service.client.AnalyticClient;
import com.kostenko.youtube.analytic.service.procesor.YoutubeAnalyticDataProcessor;
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

@SpringBootTest(classes = DefaultYoutubeAnalyticDataCollectorJob.class)
class DefaultYoutubeAnalyticDataCollectorJobTest {
    @MockBean
    private YoutubeAnalyticDataProcessor dataProcessor;
    @MockBean
    private AnalyticClient analyticClient;

    @Autowired
    private DefaultYoutubeAnalyticDataCollectorJob dataCollectorJob;

    @BeforeEach
    void clear() {
        Mockito.reset(dataProcessor, analyticClient);
        Mockito.clearInvocations(dataProcessor, analyticClient);
    }

    @Test
    void processChannelsTest() {
        List<String> channelIds = List.of("any id", "another id");
        Channel channel = Models.getChannel();
        List<Video> videos = Models.getVideos();

        Mockito.when(dataProcessor.getChannelIds())
                .thenReturn(channelIds);
        Mockito.when(analyticClient.getChannel(or(eq("any id"), eq("another id"))))
                .thenReturn(Optional.of(channel));
        Mockito.when(analyticClient.getVideos(or(eq("any id"), eq("another id")), eq("")))
                .thenReturn(videos);

        dataCollectorJob.processChannels();

        Mockito.verify(dataProcessor, Mockito.times(2))
                .saveReport(channel, videos);
    }
}
