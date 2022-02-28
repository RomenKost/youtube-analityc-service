package com.kostenko.youtube.analytic.service.service.youtube.analytic.service.impl;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.V3ApiVideosDto;
import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.YoutubeV3ApiVideosDto;
import com.kostenko.youtube.analytic.service.logger.LoggerChecker;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeChannelMapper;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeVideoMapper;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;
import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.YoutubeV3ApiChannelsDto;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Models;
import com.kostenko.youtube.analytic.service.service.youtube.analytic.client.AnalyticClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;
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

    private ListAppender<ILoggingEvent> listAppender;

    @BeforeAll
    void initialize(){
        Logger logger = (Logger) LoggerFactory.getLogger(YoutubeAnalyticService.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @BeforeEach
    void clear() {
        Mockito.reset(client, videoMapper, channelMapper);
        listAppender.list.clear();
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

        Iterator<ILoggingEvent> eventIterator = listAppender.list.iterator();
        LoggerChecker.checkLog(
                eventIterator, Level.INFO,
                "Processing request to getting information about channel with id=any id..."
        );
        LoggerChecker.checkLog(
                eventIterator, Level.INFO,
                "Request to getting information about channel with id=any id was processed."
        );
        assertFalse(eventIterator.hasNext());
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

        Iterator<ILoggingEvent> eventIterator = listAppender.list.iterator();
        LoggerChecker.checkLog(
                eventIterator, Level.INFO,
                "Processing request to getting information about videos of channel with id=any id..."
        );
        LoggerChecker.checkLog(
                eventIterator, Level.INFO,
                "Request to getting information about videos of channel with id=any id was processed."
        );
        assertFalse(eventIterator.hasNext());
    }
}
