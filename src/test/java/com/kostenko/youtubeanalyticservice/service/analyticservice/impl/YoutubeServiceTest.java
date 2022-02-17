package com.kostenko.youtubeanalyticservice.service.analyticservice.impl;

import com.kostenko.youtubeanalyticservice.dto.DTOs;
import com.kostenko.youtubeanalyticservice.dto.ChannelsDto;
import com.kostenko.youtubeanalyticservice.dto.VideosDto;
import com.kostenko.youtubeanalyticservice.mapper.dtotomodelmapper.DtoToModelMapper;
import com.kostenko.youtubeanalyticservice.model.Channel;
import com.kostenko.youtubeanalyticservice.model.Models;
import com.kostenko.youtubeanalyticservice.model.Video;
import com.kostenko.youtubeanalyticservice.service.analyticclient.AnalyticClient;
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
    private DtoToModelMapper dtoToModelMapper;
    @Autowired
    private YoutubeAnalyticService service;

    @Test
    void getChannelTest() {
        ChannelsDto channelsDto = DTOs.getChannels();
        Channel expected = Models.getChannel();

        Mockito.when(client.getChannelsDto("any id"))
                .thenReturn(channelsDto);
        Mockito.when(dtoToModelMapper.channelsDtoToChannel(channelsDto))
                .thenReturn(expected);

        Channel actual = service.getChannel("any id");
        assertEquals(expected, actual);
    }

    @Test
    void getVideosTest() {
        VideosDto videosDto = DTOs.getVideos();
        List<Video> expected = Models.getVideos();

        Mockito.when(client.getVideosDto("any id"))
                .thenReturn(videosDto);
        Mockito.when(dtoToModelMapper.videosDtoToVideos(videosDto))
                .thenReturn(expected);

        List<Video> actual = service.getVideos("any id");
        assertEquals(expected, actual);
    }
}
