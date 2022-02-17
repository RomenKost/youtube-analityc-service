package com.kostenko.youtubeanalyticservice.service.analyticclient.impl.youtubeanalyticclient;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.kostenko.youtubeanalyticservice.dto.DTOs;
import com.kostenko.youtubeanalyticservice.dto.ChannelsDto;
import com.kostenko.youtubeanalyticservice.dto.VideosDto;
import com.kostenko.youtubeanalyticservice.logger.LoggerChecker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class YoutubeClientTest {
    private RestTemplate restTemplate;
    private YoutubeAnalyticClient client;

    private final String apiKey = "api_key";
    private final String apiChannelUrl = "api_channel_url";
    private final String apiVideosUrl = "api_videos_url";

    private ListAppender<ILoggingEvent> listAppender;

    @BeforeAll
    public void initialize() {
        restTemplate = mock(RestTemplate.class);
        client = new YoutubeAnalyticClient(restTemplate, apiKey, apiChannelUrl, apiVideosUrl);

        Logger logger = (Logger) LoggerFactory.getLogger(YoutubeAnalyticClient.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @AfterEach
    public void clear() {
        Mockito.reset(restTemplate);
        listAppender.list.clear();
    }

    @Test
    void getChannelsDtoTest() {
        ChannelsDto expected = DTOs.getChannels();
        Mockito.when(restTemplate.getForObject(apiChannelUrl, ChannelsDto.class, getChannelsParameters()))
                .thenReturn(expected);
        ChannelsDto actual = client.getChannelsDto("channel_id");

        assertEquals(expected, actual);

        Iterator<ILoggingEvent> eventIterator = listAppender.list.listIterator();
        LoggerChecker.checkLog(eventIterator, Level.INFO,
                "Loading channelsDto for channel with id = channel_id..."
        );
        LoggerChecker.checkLog(
                eventIterator, Level.INFO,
                "ChannelsDto for channel with id = channel_id was loaded."
        );
        assertFalse(eventIterator.hasNext());
    }

    @Test
    void getYoutubeVideoDtoTest() {
        VideosDto expected = DTOs.getVideos();
        Mockito.when(restTemplate.getForObject(apiVideosUrl, VideosDto.class, getVideosParameters()))
                .thenReturn(expected);
        VideosDto actual = client.getVideosDto("channel_id");

        assertEquals(expected, actual);

        Iterator<ILoggingEvent> eventIterator = listAppender.list.listIterator();
        LoggerChecker.checkLog(
                eventIterator, Level.INFO,
                "Loading videosDto for channel with id = channel_id..."
        );
        LoggerChecker.checkLog(
                eventIterator, Level.INFO,
                "VideosDto for channel with id = channel_id was loaded."
        );
        assertFalse(eventIterator.hasNext());
    }

    @Test
    void getChannelsDtoDisconnectedClientTest() {
        RestClientException expected = new RestClientException("Server disconnected.");
        Mockito.when(restTemplate.getForObject(apiChannelUrl, ChannelsDto.class, getChannelsParameters()))
                .thenThrow(expected);
        assertNull(client.getChannelsDto("channel_id"));

        Iterator<ILoggingEvent> eventIterator = listAppender.list.listIterator();
        LoggerChecker.checkLog(
                eventIterator, Level.INFO,
                "Loading channelsDto for channel with id = channel_id..."
        );
        LoggerChecker.checkErrorLog(eventIterator, "Youtube api server is unavailable.", expected);
        assertFalse(eventIterator.hasNext());
    }

    @Test
    void getVideosDtoDisconnectedClientTest() {
        RestClientException expected = new RestClientException("Server disconnected.");
        Mockito.when(restTemplate.getForObject(apiVideosUrl, VideosDto.class, getVideosParameters()))
                .thenThrow(expected);
        assertNull(client.getVideosDto("channel_id"));

        Iterator<ILoggingEvent> eventIterator = listAppender.list.listIterator();
        LoggerChecker.checkLog(
                eventIterator, Level.INFO,
                "Loading videosDto for channel with id = channel_id..."
        );
        LoggerChecker.checkErrorLog(eventIterator, "Youtube api server is unavailable.", expected);
        assertFalse(eventIterator.hasNext());
    }

    private Map<String, String> getChannelsParameters() {
        Map<String, String> parameters = new HashMap<>();

        parameters.put(UrlParameters.KEY.getKey(), apiKey);
        parameters.put(UrlParameters.PART.getKey(), UrlParameters.PART.getValue());
        parameters.put(UrlParameters.MAX_RESULTS_CHANNELS.getKey(), UrlParameters.MAX_RESULTS_CHANNELS.getValue());
        parameters.put(UrlParameters.ID_FILTER.getKey(), "channel_id");

        return parameters;
    }

    private Map<String, String> getVideosParameters() {
        Map<String, String> parameters = new HashMap<>();

        parameters.put(UrlParameters.KEY.getKey(), apiKey);
        parameters.put(UrlParameters.PART.getKey(), UrlParameters.PART.getValue());
        parameters.put(UrlParameters.MAX_RESULTS_VIDEOS.getKey(), UrlParameters.MAX_RESULTS_VIDEOS.getValue());
        parameters.put(UrlParameters.CHANNEL_ID.getKey(), "channel_id");
        parameters.put(UrlParameters.CONTENT_TYPE.getKey(), UrlParameters.CONTENT_TYPE.getValue());

        return parameters;
    }
}