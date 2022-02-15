package com.kostenko.youtubeanalyticservice.service;

import com.kostenko.youtubeanalyticservice.model.Entities;
import com.kostenko.youtubeanalyticservice.entity.JSONKeys;
import com.kostenko.youtubeanalyticservice.entity.Parameters;
import com.kostenko.youtubeanalyticservice.entity.Urls;
import com.kostenko.youtubeanalyticservice.model.YoutubeChannelDto;
import com.kostenko.youtubeanalyticservice.model.YoutubeVideoDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class YoutubeClientTest {
    private RestTemplate restTemplate;
    private YoutubeClient client;

    @BeforeAll
    public void initialize() {
        restTemplate = mock(RestTemplate.class);
        client = new YoutubeClient(restTemplate, "api_key");
    }

    @Test
    void getYoutubeChannelDtoTest() {
        String restTemplateResponse = String.format(
                "{'%s': [{'%s': {'%s': 'any title', '%s': 'any country', '%s': 'any description', '%s': '2020-09-13T12:26:40Z'}}]}",
                JSONKeys.ITEMS.getKey(),
                JSONKeys.SNIPPET.getKey(),
                JSONKeys.TITLE.getKey(),
                JSONKeys.COUNTRY.getKey(),
                JSONKeys.DESCRIPTION.getKey(),
                JSONKeys.PUBLISHED_AT.getKey()
        );
        prepareMockForTestingGetYoutubeChannelDtoTest(restTemplateResponse);

        YoutubeChannelDto expected = Entities.getChannel();
        YoutubeChannelDto actual = client.getYoutubeChannelDto("channel_id");
        assertEquals(expected, actual);
    }

    @Test
    void getYoutubeVideoDtoTest() {
        String restTemplateResponse = String.format(
                "{'%s': [{'%2$s': {'%3$s': 'any video id'}," +
                        "'%4$s': {'%5$s': 'any title', '%6$s': 'any description', '%7$s': '2020-09-13T12:26:40Z'}}," +
                        "{'%2$s': {'%3$s': 'another video id'}," +
                        "'%4$s': {'%5$s': 'another title', '%6$s': 'another description', '%7$s': '2023-11-14T22:13:20Z'}}]}",
                JSONKeys.ITEMS.getKey(),
                JSONKeys.ID.getKey(),
                JSONKeys.VIDEO_ID.getKey(),
                JSONKeys.SNIPPET.getKey(),
                JSONKeys.TITLE.getKey(),
                JSONKeys.DESCRIPTION.getKey(),
                JSONKeys.PUBLISHED_AT.getKey()
        );
        prepareMockForTestingGetYoutubeVideoDtoTest(restTemplateResponse);

        List<YoutubeVideoDto> expected = Entities.getVideos();
        List<YoutubeVideoDto> actual = client.getYoutubeVideosDto("channel_id");

        assertEquals(expected, actual);
    }

    @Test
    void getEmptyYoutubeVideosDto() {
        String restTemplateResponse = "{}";
        prepareMockForTestingGetYoutubeVideoDtoTest(restTemplateResponse);

        List<YoutubeVideoDto> actual = client.getYoutubeVideosDto("channel_id");
        assertTrue(actual.isEmpty());
    }

    @Test
    void getEmptyYoutubeChannelDtoTest() {
        String restResponse = "{}";
        prepareMockForTestingGetYoutubeChannelDtoTest(restResponse);

        YoutubeChannelDto actual = client.getYoutubeChannelDto("channel_id");
        assertNull(actual);
    }

    private void prepareMockForTestingGetYoutubeChannelDtoTest(String restTemplateResponse) {
        Map<String, String> parameters = new HashMap<>();

        parameters.put(Parameters.KEY.getKey(), "api_key");
        parameters.put(Parameters.PART.getKey(), Parameters.PART.getValue());
        parameters.put(Parameters.MAX_RESULTS_CHANNELS.getKey(), Parameters.MAX_RESULTS_CHANNELS.getValue());
        parameters.put(Parameters.ID_FILTER.getKey(), "channel_id");

        Mockito.when(restTemplate.getForObject(Urls.CHANNELS.getUrl(), String.class, parameters))
                .thenReturn(restTemplateResponse);
    }

    private void prepareMockForTestingGetYoutubeVideoDtoTest(String restTemplateResponse) {
        Map<String, String> parameters = new HashMap<>();

        parameters.put(Parameters.KEY.getKey(), "api_key");
        parameters.put(Parameters.PART.getKey(), Parameters.PART.getValue());
        parameters.put(Parameters.MAX_RESULTS_VIDEOS.getKey(), Parameters.MAX_RESULTS_VIDEOS.getValue());
        parameters.put(Parameters.CHANNEL_ID.getKey(), "channel_id");
        parameters.put(Parameters.CONTENT_TYPE.getKey(), Parameters.CONTENT_TYPE.getValue());

        Mockito.when(restTemplate.getForObject(Urls.VIDEOS.getUrl(), String.class, parameters))
                .thenReturn(restTemplateResponse);
    }
}