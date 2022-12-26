package com.kostenko.youtube.analytic.service.youtube.api.impl;

import com.kostenko.youtube.analytic.config.MockedDataSourceConfiguration;
import com.kostenko.youtube.analytic.service.channel.mapper.channel.YoutubeChannelMapper;
import com.kostenko.youtube.analytic.service.channel.mapper.video.YoutubeVideoMapper;
import com.kostenko.youtube.analytic.model.youtube.Channel;
import com.kostenko.youtube.analytic.model.youtube.Video;
import com.kostenko.youtube.analytic.service.youtube.api.dto.channel.YoutubeV3ChannelsDto;
import com.kostenko.youtube.analytic.service.youtube.api.dto.video.YoutubeV3VideosDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.kostenko.youtube.analytic.model.youtube.MockedYoutubeModelsProvider.*;
import static com.kostenko.youtube.analytic.service.youtube.api.dto.channel.MockedYoutubeV3ChannelDtoProvider.*;
import static com.kostenko.youtube.analytic.service.youtube.api.dto.video.MockedYoutubeV3VideoDtoProvider.*;
import static com.kostenko.youtube.analytic.util.TestConstants.*;
import static com.kostenko.youtube.analytic.util.UrlParameters.*;
import static com.kostenko.youtube.analytic.util.security.TestSecurityConstants.*;
import static com.kostenko.youtube.analytic.util.url.TestUrls.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MockedDataSourceConfiguration.class)
class YoutubeV3ApiClientTest {
    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private YoutubeChannelMapper channelMapper;
    @MockBean
    private YoutubeVideoMapper videoMapper;

    @Autowired
    private YoutubeV3ApiClient client;

    @Test
    void getChannelTest() {
        Optional<Channel> expected = Optional.of(getMockedChannel());

        when(restTemplate.getForObject(CHANNEL_URL, YoutubeV3ChannelsDto.class, getUrlsParameters()))
                .thenReturn(getMockedYoutubeV3ChannelsDto());
        when(channelMapper.youtubeV3ChannelsDtoToChannels(getMockedYoutubeV3ChannelDtoList()))
                .thenReturn(getMockedChannelList());

        Optional<Channel> actual = client.getChannel(TEST_CHANNEL_ID);

        assertEquals(expected, actual);
    }

    @Test
    void getYoutubeVideoDtoTest() {
        List<Video> excepted = getMockedVideoList(6);

        when(restTemplate.getForObject(VIDEOS_URL, YoutubeV3VideosDto.class, getUrlsParameters("")))
                .thenReturn(getMockedYoutubeV3VideosDto(TEST_TOKEN));
        when(restTemplate.getForObject(VIDEOS_URL, YoutubeV3VideosDto.class, getUrlsParameters(TEST_TOKEN)))
                .thenReturn(getMockedYoutubeV3VideosDto(null));

        when(videoMapper.youtubeV3VideoDtosToVideos(getMockedYoutubeV3VideoDtoList()))
                .thenReturn(getMockedVideoList());

        List<Video> actual = client.getVideos(TEST_CHANNEL_ID);

        assertEquals(excepted, actual);
    }

    @Test
    void getChannelsDtoWhenRestTemplateDisconnectsReturnEmptyOptionalTest() {
        when(restTemplate.getForObject(CHANNEL_URL, YoutubeV3ChannelsDto.class, getUrlsParameters()))
                .thenThrow(new RestClientException(""));

        Optional<Channel> actual = client.getChannel(TEST_CHANNEL_ID);

        assertTrue(actual.isEmpty());
    }

    @Test
    void getVideosDtoWhenRestTemplateDisconnectsReturnEmptyListTest() {
        Map<String, String> parameters = getUrlsParameters();
        parameters.put(PAGE_TOKEN, "");

        when(restTemplate.getForObject(VIDEOS_URL, YoutubeV3VideosDto.class, parameters))
                .thenThrow(new RestClientException(""));
        List<Video> actual = client.getVideos(TEST_CHANNEL_ID);

        assertTrue(actual.isEmpty());
    }

    @Test
    void getChannelsDtoWhenChannelsWasNotFoundReturnEmptyOptionalTest() {
        Optional<Channel> actual = client.getChannel(TEST_CHANNEL_ID);
        assertTrue(actual.isEmpty());
    }

    @Test
    void getVideosDtoWhenRestTemplateDisconnectsReturnEmptyOptionalTest() {
        List<Video> actual = client.getVideos(TEST_CHANNEL_ID);
        assertTrue(actual.isEmpty());
    }

    private Map<String, String> getUrlsParameters() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(YOUTUBE_API_KEY, TEST_API_KEY);
        parameters.put(CHANNEL_ID, TEST_CHANNEL_ID);
        return parameters;
    }

    private Map<String, String> getUrlsParameters(String pageToken) {
        Map<String, String> parameters = getUrlsParameters();
        parameters.put(PAGE_TOKEN, pageToken);
        return parameters;
    }
}
