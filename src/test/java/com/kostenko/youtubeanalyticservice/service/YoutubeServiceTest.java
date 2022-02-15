package com.kostenko.youtubeanalyticservice.service;

import com.kostenko.youtubeanalyticservice.model.Entities;
import com.kostenko.youtubeanalyticservice.model.YoutubeChannelDto;
import com.kostenko.youtubeanalyticservice.model.YoutubeVideoDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class YoutubeServiceTest {
    private YoutubeClient client;
    private YoutubeService service;

    @BeforeAll
    public void initialize() {
        client = mock(YoutubeClient.class);
        service = new YoutubeService(client);
    }

    @Test
    void getChannelTest() {
        YoutubeChannelDto expected = Entities.getChannel();

        Mockito.when(client.getYoutubeChannelDto("any id"))
                .thenReturn(expected);

        YoutubeChannelDto actual = service.getChannel("any id");
        assertEquals(expected, actual);
    }

    @Test
    void getVideosTest() {
        List<YoutubeVideoDto> expected = Entities.getVideos();

        Mockito.when(client.getYoutubeVideosDto("any id"))
                .thenReturn(expected);

        List<YoutubeVideoDto> actual = service.getVideos("any id");
        assertEquals(expected, actual);
    }
}
