package com.kostenko.youtube.analytic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kostenko.youtube.analytic.dto.youtube.analytic.YoutubeAnalyticChannelDto;
import com.kostenko.youtube.analytic.dto.youtube.analytic.YoutubeAnalyticDTOs;
import com.kostenko.youtube.analytic.dto.youtube.analytic.YoutubeAnalyticVideoDto;
import com.kostenko.youtube.analytic.mapper.youtube.channel.YoutubeChannelMapper;
import com.kostenko.youtube.analytic.mapper.youtube.video.YoutubeVideoMapper;
import com.kostenko.youtube.analytic.model.Models;

import com.kostenko.youtube.analytic.model.youtube.Channel;
import com.kostenko.youtube.analytic.model.youtube.Video;
import com.kostenko.youtube.analytic.service.youtube.procesor.YoutubeAnalyticDataProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(value = YoutubeAnalyticRestController.class)
class YoutubeAnalyticRestControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private YoutubeAnalyticDataProcessor youtubeAnalyticDataProcessor;
    @MockBean
    private YoutubeChannelMapper channelMapper;
    @MockBean
    private YoutubeVideoMapper videoMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getChannelTest() throws Exception {
        Channel channel = Models.getChannel();
        YoutubeAnalyticChannelDto expected = YoutubeAnalyticDTOs.getChannelDto();
        when(youtubeAnalyticDataProcessor.getChannel("any_id"))
                .thenReturn(channel);
        when(channelMapper.channelToYoutubeAnalyticChannelDto(channel))
                .thenReturn(expected);

        String response = mockMvc.perform(
                        MockMvcRequestBuilders.get("/youtube/analytic/v1/channels/any_id")
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        YoutubeAnalyticChannelDto actual = objectMapper.readValue(response, YoutubeAnalyticChannelDto.class);

        assertEquals(expected, actual);
    }

    @Test
    void getVideosTest() throws Exception {
        List<YoutubeAnalyticVideoDto> expected = YoutubeAnalyticDTOs.getVideoDTOs();

        List<Video> videos = Models.getVideos();
        when(youtubeAnalyticDataProcessor.getVideos("any_id"))
                .thenReturn(videos);
        when(videoMapper.videosToYoutubeAnalyticVideoDTOs(videos))
                .thenReturn(expected);

        String response = mockMvc.perform(
                        MockMvcRequestBuilders.get("/youtube/analytic/v1/channels/any_id/videos")
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<YoutubeAnalyticVideoDto> actual =  Arrays.asList(objectMapper.readValue(response, YoutubeAnalyticVideoDto[].class));

        assertEquals(expected, actual);
    }
}
