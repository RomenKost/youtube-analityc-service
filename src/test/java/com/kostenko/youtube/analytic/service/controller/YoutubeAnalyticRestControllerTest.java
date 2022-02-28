package com.kostenko.youtube.analytic.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticDto;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeChannelMapper;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeVideoMapper;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Models;
import com.kostenko.youtube.analytic.service.service.youtube.analytic.service.AnalyticService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(YoutubeAnalyticRestController.class)
class YoutubeAnalyticRestControllerTest {
    @Value("${server.port}")
    private int port;
    @MockBean
    private AnalyticService service;
    @MockBean
    private YoutubeChannelMapper channelMapper;
    @MockBean
    private YoutubeVideoMapper videoMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getChannelTest() throws Exception {
        Mockito.when(service.getChannel("any_id"))
                .thenReturn(Models.getChannel());
        Mockito.when(channelMapper.channelToYoutubeAnalyticChannelDto(Models.getChannel()))
                .thenReturn(YoutubeAnalyticDto.getChannelDto());

        String expected = new ObjectMapper().writeValueAsString(Models.getChannel())
                .replace("1600000000000", "\"2020-09-13T12:26:40.000+00:00\"");

        String actual = mockMvc.perform(
                        MockMvcRequestBuilders.get("/youtube/analytic/v1/channels/any_id")
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(expected, actual);
    }

    @Test
    void getVideosTest() throws Exception {
        Mockito.when(service.getVideos("any_id"))
                .thenReturn(Models.getVideos());
        Mockito.when(videoMapper.videosToYoutubeAnalyticVideoDTOs(Models.getVideos()))
                .thenReturn(YoutubeAnalyticDto.getVideoDTOs());
        String expected = new ObjectMapper().writeValueAsString(Models.getVideos())
                .replace("1600000000000", "\"2020-09-13T12:26:40.000+00:00\"")
                .replace("1700000000000", "\"2023-11-14T22:13:20.000+00:00\"");

        String actual = mockMvc.perform(
                        MockMvcRequestBuilders.get("/youtube/analytic/v1/channels/any_id/videos")
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(expected, actual);
    }
}
