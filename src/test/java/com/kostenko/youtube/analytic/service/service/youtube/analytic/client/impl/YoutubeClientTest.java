package com.kostenko.youtube.analytic.service.service.youtube.analytic.client.impl;

import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.V3ApiVideosDto;
import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.YoutubeV3ApiChannelsDto;
import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.YoutubeV3ApiVideosDto;
import com.kostenko.youtube.analytic.service.exception.YoutubeServiceUnavailableException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = YoutubeAnalyticClient.class)
class YoutubeClientTest {
    @MockBean
    private RestTemplate restTemplate;
    @Autowired
    private YoutubeAnalyticClient client;

    @AfterEach
    public void clear() {
        Mockito.reset(restTemplate);
    }

    @Test
    void getChannelsDtoTest() {
        YoutubeV3ApiChannelsDto expected = V3ApiVideosDto.getChannelDto();
        Mockito.when(restTemplate.getForObject("channels_url", YoutubeV3ApiChannelsDto.class, getUrlsParameters()))
                .thenReturn(expected);
        YoutubeV3ApiChannelsDto actual = client.getChannelsDto("channel_id");

        assertEquals(expected, actual);
    }

    @Test
    void getYoutubeVideoDtoTest() {
        YoutubeV3ApiVideosDto expected = V3ApiVideosDto.getVideoDTOs();
        Map<String, String> parameters = getUrlsParameters();
        parameters.put("pageToken", "token");

        Mockito.when(restTemplate.getForObject("videos_url", YoutubeV3ApiVideosDto.class, parameters))
                .thenReturn(expected);
        YoutubeV3ApiVideosDto actual = client.getVideosDto("channel_id", "token");

        assertEquals(expected, actual);
    }

    @Test
    void getChannelsDtoDisconnectedClientTest() {
        YoutubeServiceUnavailableException expected = new YoutubeServiceUnavailableException(
                "channel_id", new RestClientException("Server disconnected.")
        );

        Mockito.when(restTemplate.getForObject("channels_url", YoutubeV3ApiChannelsDto.class, getUrlsParameters()))
                .thenThrow(expected);

        YoutubeServiceUnavailableException actual = assertThrows(
                YoutubeServiceUnavailableException.class,
                () -> client.getChannelsDto("channel_id")
        );

        assertEquals(expected, actual);
    }

    @Test
    void getVideosDtoDisconnectedClientTest() {
        YoutubeServiceUnavailableException expected = new YoutubeServiceUnavailableException(
                "channel_id", new RestClientException("Server disconnected.")
        );

        Map<String, String> parameters = getUrlsParameters();
        parameters.put("pageToken", "token");
        Mockito.when(restTemplate.getForObject("videos_url", YoutubeV3ApiVideosDto.class, parameters))
                .thenThrow(expected);

        YoutubeServiceUnavailableException actual = assertThrows(
                YoutubeServiceUnavailableException.class,
                () -> client.getVideosDto("channel_id", "token")
        );

        assertEquals(expected, actual);
    }

    private Map<String, String> getUrlsParameters() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(UrlParameters.KEY.getKey(), "api_key");
        parameters.put(UrlParameters.CHANNEL_ID.getKey(), "channel_id");
        return parameters;
    }
}
