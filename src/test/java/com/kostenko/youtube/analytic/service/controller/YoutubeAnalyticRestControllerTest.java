package com.kostenko.youtube.analytic.service.controller;

import com.kostenko.youtube.analytic.service.YoutubeAnalyticServiceApplication;
import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticDto;
import com.kostenko.youtube.analytic.service.service.youtube.analytic.service.impl.YoutubeAnalyticService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = YoutubeAnalyticServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class YoutubeAnalyticRestControllerTest {
    @LocalServerPort
    private int port;
    @MockBean
    private YoutubeAnalyticService service;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void channelTest() {
        Mockito.when(service.getChannel("any_id"))
                .thenReturn(YoutubeAnalyticDto.getChannel());
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/youtube/analytic/v1/channels/any_id"),
                HttpMethod.GET, null, String.class
        );
        String expected = "{" +
                "\"id\":\"any id\"," +
                "\"title\":\"any title\"," +
                "\"description\":\"any description\"," +
                "\"country\":\"any country\"," +
                "\"publishedAt\":\"2020-09-13T12:26:40.000+00:00\"" +
                "}";
        String actual = response.getBody();

        assertEquals(expected, actual);
    }

    @Test
    void videosTest() {
        Mockito.when(service.getVideos("any_id"))
                .thenReturn(YoutubeAnalyticDto.getVideos());
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/youtube/analytic/v1/videos/any_id"),
                HttpMethod.GET, null, String.class
        );
        String expected = "[{" +
                "\"videoId\":\"any video id\"," +
                "\"title\":\"any title\"," +
                "\"description\":\"any description\"," +
                "\"publishedAt\":\"2020-09-13T12:26:40.000+00:00\"" +
                "},{" +
                "\"videoId\":\"another video id\"," +
                "\"title\":\"another title\"," +
                "\"description\":\"another description\"," +
                "\"publishedAt\":\"2023-11-14T22:13:20.000+00:00\"" +
                "}]";
        String actual = response.getBody();

        assertEquals(expected, actual);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
