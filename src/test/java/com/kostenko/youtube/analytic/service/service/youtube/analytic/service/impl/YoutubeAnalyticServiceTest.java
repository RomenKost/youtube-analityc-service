package com.kostenko.youtube.analytic.service.service.youtube.analytic.service.impl;

import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.V3ApiVideosDto;
import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.YoutubeV3ApiVideosDto;
import com.kostenko.youtube.analytic.service.exception.YoutubeChannelNotFoundException;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeChannelMapper;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeVideoMapper;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;
import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.YoutubeV3ApiChannelsDto;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Models;
import com.kostenko.youtube.analytic.service.service.youtube.analytic.client.AnalyticClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = YoutubeAnalyticService.class)
class YoutubeAnalyticServiceTest {
    @MockBean
    private AnalyticClient client;
    @MockBean
    private YoutubeVideoMapper videoMapper;
    @MockBean
    private YoutubeChannelMapper channelMapper;

    @Autowired
    private YoutubeAnalyticService service;

    @BeforeEach
    void clear() {
        Mockito.reset(client, videoMapper, channelMapper);
    }

    @Test
    void getChannelTest() {
        YoutubeV3ApiChannelsDto youtubeV3ApiChannelsDto = V3ApiVideosDto.getChannelDto();
        Channel expected = Models.getChannel();

        Mockito.when(client.getChannelsDto("any id"))
                .thenReturn(youtubeV3ApiChannelsDto);
        Mockito.when(channelMapper.youtubeV3ApiChannelsDtoToChannel(youtubeV3ApiChannelsDto))
                .thenReturn(expected);

        Channel actual = service.getChannel("any id");
        assertEquals(expected, actual);
    }

    @Test
    void getChannelWhenChannelIsNullThrowYoutubeChannelNotFoundExceptionTest() {
        YoutubeChannelNotFoundException actual = assertThrows(
                YoutubeChannelNotFoundException.class,
                () -> service.getChannel("any id")
        );

        assertEquals("any id", actual.getId());
        assertEquals("Channel with id = any id wasn't found.", actual.getMessage());
    }

    @Test
    void getVideosTest() {
        YoutubeV3ApiVideosDto youtubeV3ApiVideosDto = V3ApiVideosDto.getVideoDTOs();
        List<Video> expected = Models.getVideos();

        Mockito.when(client.getVideosDto("any id", ""))
                .thenReturn(youtubeV3ApiVideosDto);
        Mockito.when(videoMapper.videoDTOsToVideos(youtubeV3ApiVideosDto))
                .thenReturn(expected);

        List<Video> actual = service.getVideos("any id");
        assertEquals(expected, actual);
    }

    @Test
    void getChannelWhenVideosListIsEmptyThrowYoutubeChannelNotFoundExceptionTest() {
        Mockito.when(client.getVideosDto("any id", ""))
                .thenReturn(new YoutubeV3ApiVideosDto());

        YoutubeChannelNotFoundException actual = assertThrows(
                YoutubeChannelNotFoundException.class,
                () -> service.getVideos("any id")
        );

        assertEquals("any id", actual.getId());
        assertEquals("Channel with id = any id wasn't found.", actual.getMessage());
    }
}
