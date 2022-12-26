package com.kostenko.youtube.analytic.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kostenko.youtube.analytic.config.MockedDataSourceConfiguration;
import com.kostenko.youtube.analytic.controller.dto.analytic.AnalyticChannelDto;
import com.kostenko.youtube.analytic.controller.dto.analytic.AnalyticVideoDto;
import com.kostenko.youtube.analytic.exception.response.AnalyticExceptionResponse;
import com.kostenko.youtube.analytic.exception.youtube.ChannelNotFoundException;
import com.kostenko.youtube.analytic.exception.youtube.VideosNotFoundException;
import com.kostenko.youtube.analytic.service.channel.mapper.channel.YoutubeChannelMapper;
import com.kostenko.youtube.analytic.service.channel.mapper.video.YoutubeVideoMapper;
import com.kostenko.youtube.analytic.service.channel.YoutubeChannelsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.kostenko.youtube.analytic.exception.response.MockedExceptionResponseProvider.*;
import static com.kostenko.youtube.analytic.model.youtube.MockedYoutubeModelsProvider.*;
import static com.kostenko.youtube.analytic.controller.dto.analytic.MockedAnalyticDtoProvider.*;
import static com.kostenko.youtube.analytic.util.TestConstants.*;
import static com.kostenko.youtube.analytic.util.url.YoutubeAnalyticUrls.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = MockedDataSourceConfiguration.class)
class YoutubeAnalyticControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private YoutubeChannelsService youtubeAnalyticDataProcessor;
    @MockBean
    private YoutubeChannelMapper channelMapper;
    @MockBean
    private YoutubeVideoMapper videoMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getChannelTest() throws Exception {
        AnalyticChannelDto expected = getMockedAnalyticChannelDto();

        when(youtubeAnalyticDataProcessor.getChannel(TEST_CHANNEL_ID))
                .thenReturn(getMockedChannel());
        when(channelMapper.channelToYoutubeAnalyticChannelDto(getMockedChannel()))
                .thenReturn(expected);

        String response = mockMvc.perform(get(GET_CHANNEL_BY_ID, TEST_CHANNEL_ID))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AnalyticChannelDto actual = objectMapper.readValue(response, AnalyticChannelDto.class);

        assertEquals(expected, actual);
    }

    @Test
    void getVideosTest() throws Exception {
        List<AnalyticVideoDto> expected = getMockedAnalyticVideoDtoList();

        when(youtubeAnalyticDataProcessor.getVideos(TEST_CHANNEL_ID))
                .thenReturn(getMockedVideoList());
        when(videoMapper.videosToYoutubeAnalyticVideoDtoList(getMockedVideoList()))
                .thenReturn(expected);

        String response = mockMvc.perform(get(GET_VIDEOS_BY_CHANNEL_ID, TEST_CHANNEL_ID))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<AnalyticVideoDto> actual =  objectMapper.readValue(response, new TypeReference<>() {});

        assertEquals(expected, actual);
    }

    @Test
    void handleYoutubeVideosNotFoundExceptionTest() throws Exception {
        AnalyticExceptionResponse expected = getMockedVideosNotFoundExceptionResponse();

        when(youtubeAnalyticDataProcessor.getVideos(TEST_CHANNEL_ID))
                .thenThrow(new VideosNotFoundException(TEST_CHANNEL_ID));

        String response = mockMvc.perform(get(GET_VIDEOS_BY_CHANNEL_ID, TEST_CHANNEL_ID))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AnalyticExceptionResponse actual =  objectMapper.readValue(response, new TypeReference<>() {});

        assertEquals(expected, actual);
    }

    @Test
    void handleYoutubeChannelNotFoundExceptionTest() throws Exception {
        AnalyticExceptionResponse expected = getMockedChannelNotFoundExceptionResponse();

        when(youtubeAnalyticDataProcessor.getVideos(TEST_CHANNEL_ID))
                .thenThrow(new ChannelNotFoundException(TEST_CHANNEL_ID));

        String response = mockMvc.perform(get(GET_VIDEOS_BY_CHANNEL_ID, TEST_CHANNEL_ID))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AnalyticExceptionResponse actual =  objectMapper.readValue(response, new TypeReference<>() {});

        assertEquals(expected, actual);
    }
}
