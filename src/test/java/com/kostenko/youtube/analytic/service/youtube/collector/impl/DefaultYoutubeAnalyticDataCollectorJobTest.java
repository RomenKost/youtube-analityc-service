package com.kostenko.youtube.analytic.service.youtube.collector.impl;

import com.kostenko.youtube.analytic.model.youtube.Channel;
import com.kostenko.youtube.analytic.model.Models;
import com.kostenko.youtube.analytic.model.youtube.Video;
import com.kostenko.youtube.analytic.service.youtube.client.AnalyticClient;
import com.kostenko.youtube.analytic.service.youtube.procesor.YoutubeAnalyticDataProcessor;
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
import static org.mockito.Mockito.*;

@SpringBootTest(
        classes = DefaultYoutubeAnalyticDataCollectorJob.class,
        properties = "youtube.analytic.collector.enabled = true"
)
class DefaultYoutubeAnalyticDataCollectorJobTest {
    @MockBean
    private YoutubeAnalyticDataProcessor dataProcessor;
    @MockBean
    private AnalyticClient analyticClient;

    @Autowired
    private DefaultYoutubeAnalyticDataCollectorJob dataCollectorJob;

    @BeforeEach
    void clear() {
        reset(dataProcessor, analyticClient);
        clearInvocations(dataProcessor, analyticClient);
    }

    @Test
    void processChannelsTest() {
        List<String> channelIds = List.of("any id", "another id");
        Channel channel = Models.getChannel();
        List<Video> videos = Models.getVideos();

        when(dataProcessor.getChannelIds())
                .thenReturn(channelIds);
        when(analyticClient.getChannel(or(eq("any id"), eq("another id"))))
                .thenReturn(Optional.of(channel));
        when(analyticClient.getVideos(or(eq("any id"), eq("another id")), eq("")))
                .thenReturn(videos);

        dataCollectorJob.processChannels();

        Mockito.verify(dataProcessor, Mockito.times(2))
                .saveReport(channel, videos);
    }
}
