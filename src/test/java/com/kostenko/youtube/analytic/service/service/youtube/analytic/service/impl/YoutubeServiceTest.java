package com.kostenko.youtube.analytic.service.service.youtube.analytic.service.impl;

import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticChannelDto;
import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticDto;
import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticVideoDto;
import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.V3ApiVideosDto;
import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.YoutubeV3ApiVideosDto;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeChannelMapper;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeVideoMapper;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;
import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.YoutubeV3ApiChannelsDto;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Models;
import com.kostenko.youtube.analytic.service.service.youtube.analytic.client.AnalyticClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class YoutubeServiceTest {
    @MockBean
    private AnalyticClient client;
    @MockBean
    private YoutubeVideoMapper dtoToVideoMapper;
    @MockBean
    private YoutubeChannelMapper dtoToChannelMapper;

    @Autowired
    private YoutubeAnalyticService service;

    @Test
    void getChannelTest() {
        YoutubeV3ApiChannelsDto youtubeV3ApiChannelsDto = V3ApiVideosDto.getChannels();
        Channel channel = Models.getChannel();
        YoutubeAnalyticChannelDto expected = YoutubeAnalyticDto.getChannel();

        Mockito.when(client.getChannelsDto("any id"))
                .thenReturn(youtubeV3ApiChannelsDto);
        Mockito.when(dtoToChannelMapper.youtubeV3ApiChannelsDtoToChannel(youtubeV3ApiChannelsDto))
                .thenReturn(channel);
        Mockito.when(dtoToChannelMapper.channelToYoutubeAnalyticChannelDto(channel))
                .thenReturn(expected);

        YoutubeAnalyticChannelDto actual = service.getChannel("any id");
        assertEquals(expected, actual);
    }

    @Test
    void getVideosTest() {
        YoutubeV3ApiVideosDto youtubeV3ApiVideosDto = V3ApiVideosDto.getVideos();
        List<Video> videos = Models.getVideos();
        List<YoutubeAnalyticVideoDto> expected = YoutubeAnalyticDto.getVideos();

        Mockito.when(client.getVideosDto("any id"))
                .thenReturn(youtubeV3ApiVideosDto);
        Mockito.when(dtoToVideoMapper.videoDTOsToVideos(youtubeV3ApiVideosDto))
                .thenReturn(videos);
        Mockito.when(dtoToVideoMapper.videosToYoutubeAnalyticVideoDTOs(videos))
                .thenReturn(expected);

        List<YoutubeAnalyticVideoDto> actual = service.getVideos("any id");
        assertEquals(expected, actual);
    }
}
