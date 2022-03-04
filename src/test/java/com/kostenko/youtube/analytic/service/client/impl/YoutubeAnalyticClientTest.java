package com.kostenko.youtube.analytic.service.client.impl;

import com.kostenko.youtube.analytic.dto.youtube.v3.api.YoutubeV3ApiChannelsDto;
import com.kostenko.youtube.analytic.dto.youtube.v3.api.YoutubeV3ApiDTOs;
import com.kostenko.youtube.analytic.dto.youtube.v3.api.YoutubeV3ApiVideosDto;
import com.kostenko.youtube.analytic.mapper.channel.YoutubeChannelMapper;
import com.kostenko.youtube.analytic.mapper.video.YoutubeVideoMapper;
import com.kostenko.youtube.analytic.model.Channel;
import com.kostenko.youtube.analytic.model.Models;
import com.kostenko.youtube.analytic.model.Video;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = YoutubeAnalyticClient.class)
class YoutubeAnalyticClientTest {
    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private YoutubeChannelMapper channelMapper;
    @MockBean
    private YoutubeVideoMapper videoMapper;

    @Autowired
    private YoutubeAnalyticClient client;

    @AfterEach
    public void clear() {
        Mockito.reset(restTemplate);
    }

    @Test
    void getChannelTest() {
        Channel expected = Models.getChannel();
        YoutubeV3ApiChannelsDto v3ApiChannelsDto = YoutubeV3ApiDTOs.getChannelDto();

        Mockito.when(restTemplate.getForObject("channels_url", YoutubeV3ApiChannelsDto.class, getUrlsParameters()))
                .thenReturn(v3ApiChannelsDto);
        Mockito.when(channelMapper.youtubeV3ApiChannelsDtoToChannel(v3ApiChannelsDto))
                .thenReturn(expected);
        Optional<Channel> actual = client.getChannel("channel_id");

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void getYoutubeVideoDtoTest() {
        List<Video> excepted = Models.getVideos();
        YoutubeV3ApiVideosDto v3ApiVideosDto = YoutubeV3ApiDTOs.getVideoDTOs();

        Map<String, String> parameters = getUrlsParameters();
        parameters.put("pageToken", "token");

        Mockito.when(restTemplate.getForObject("videos_url", YoutubeV3ApiVideosDto.class, parameters))
                .thenReturn(v3ApiVideosDto);
        Mockito.when(videoMapper.videoDTOsToVideos(v3ApiVideosDto))
                .thenReturn(excepted);
        List<Video> actual = client.getVideos("channel_id", "token");

        assertEquals(excepted, actual);
    }

    @Test
    void getChannelsDtoWhenRestTemplateDisconnectsReturnEmptyOptionalTest() {
        Mockito.when(restTemplate.getForObject("channels_url", YoutubeV3ApiChannelsDto.class, getUrlsParameters()))
                .thenThrow(new RestClientException(""));

        Optional<Channel> actual = client.getChannel("channel_id");

        assertTrue(actual.isEmpty());
    }

    @Test
    void getVideosDtoWhenRestTemplateDisconnectsReturnEmptyListTest() {
        Map<String, String> parameters = getUrlsParameters();
        parameters.put("pageToken", "token");

        Mockito.when(restTemplate.getForObject("videos_url", YoutubeV3ApiVideosDto.class, parameters))
                .thenThrow(new RestClientException(""));
        List<Video> actual = client.getVideos("channel_id", "token");

        assertTrue(actual.isEmpty());
    }

    @Test
    void getChannelsDtoWhenChannelsWasNotFoundReturnEmptyOptionalTest() {
        Optional<Channel> actual = client.getChannel("channel_id");
        assertTrue(actual.isEmpty());
    }

    @Test
    void getVideosDtoWhenRestTemplateDisconnectsReturnEmptyOptionalTest() {
        List<Video> actual = client.getVideos("channel_id", "token");
        assertTrue(actual.isEmpty());
    }

    private Map<String, String> getUrlsParameters() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(UrlParameters.KEY.getKey(), "api_key");
        parameters.put(UrlParameters.CHANNEL_ID.getKey(), "channel_id");
        return parameters;
    }
}
