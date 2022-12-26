package com.kostenko.youtube.analytic.service.collector.impl;

import com.kostenko.youtube.analytic.config.MockedDataSourceConfiguration;
import com.kostenko.youtube.analytic.service.youtube.api.YoutubeApiClient;
import com.kostenko.youtube.analytic.service.channel.YoutubeChannelsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static com.kostenko.youtube.analytic.model.youtube.MockedYoutubeModelsProvider.*;
import static com.kostenko.youtube.analytic.util.TestConstants.*;
import static org.mockito.Mockito.*;

@SpringBootTest(
        classes = MockedDataSourceConfiguration.class,
        properties = "youtube.analytic.collector.enabled = true"
)
class DefaultYoutubeDataCollectorJobTest {
    @MockBean
    private YoutubeChannelsService dataProcessor;
    @MockBean
    private YoutubeApiClient analyticClient;

    @Autowired
    private DefaultYoutubeDataCollectorJob dataCollectorJob;

    @Test
    void processChannelsTest() {
        when(dataProcessor.getChannelIds())
                .thenReturn(List.of(TEST_CHANNEL_ID, TEST_CHANNEL_ID, TEST_CHANNEL_ID));
        when(analyticClient.getChannel(TEST_CHANNEL_ID))
                .thenReturn(Optional.of(getMockedChannel()));
        when(analyticClient.getVideos(TEST_CHANNEL_ID))
                .thenReturn(getMockedVideoList());

        dataCollectorJob.collectYoutubeData();

        verify(dataProcessor, times(3))
                .saveChannel(getMockedChannel(), getMockedVideoList());
    }
}
