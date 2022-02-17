package com.kostenko.youtubeanalyticservice.mapper.dtotomodelmapper;

import com.kostenko.youtubeanalyticservice.dto.ChannelsDto;
import com.kostenko.youtubeanalyticservice.dto.DTOs;
import com.kostenko.youtubeanalyticservice.dto.VideosDto;
import com.kostenko.youtubeanalyticservice.model.Channel;
import com.kostenko.youtubeanalyticservice.model.Models;
import com.kostenko.youtubeanalyticservice.model.Video;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DtoToModelMapperTest {
    @Autowired
    private DtoToModelMapper mapper;

    @Test
    void channelsDtoToChannelTest() {
        Channel expected = Models.getChannel();
        ChannelsDto channelsDto = DTOs.getChannels();
        Channel actual = mapper.channelsDtoToChannel(channelsDto);

        assertEquals(expected, actual);
    }

    @Test
    void nullChannelsDtoToChannelTest() {
        assertNull(mapper.channelsDtoToChannel(null));
    }

    @Test
    void channelsDtoWithoutItemsToChannelTest() {
        assertNull(mapper.channelsDtoToChannel(new ChannelsDto()));
    }

    @Test
    void emptyChannelsDtoToChannelTest() {
        ChannelsDto channelsDto = new ChannelsDto(new ChannelsDto.Item[0]);
        assertNull(mapper.channelsDtoToChannel(channelsDto));
    }

    @Test
    void channelsDtoWithoutSnippetToChannelTest() {
        ChannelsDto.Item item = new ChannelsDto.Item("any id", null);
        ChannelsDto channelsDto = new ChannelsDto(new ChannelsDto.Item[]{item});
        assertNull(mapper.channelsDtoToChannel(channelsDto));
    }

    @Test
    void videosDtoToVideosTest() {
        List<Video> expected = Models.getVideos();
        VideosDto videosDto = DTOs.getVideos();
        List<Video> actual = mapper.videosDtoToVideos(videosDto);

        assertEquals(expected, actual);
    }

    @Test
    void nullVideosDtoToVideosTest() {
        assertTrue(mapper.videosDtoToVideos(null).isEmpty());
    }

    @Test
    void emptyVideosDtoToVideosTest() {
        VideosDto videosDto = new VideosDto(new VideosDto.Item[0]);
        assertTrue(mapper.videosDtoToVideos(videosDto).isEmpty());
    }

    @Test
    void videosDtoWithoutItemsToVideosTest() {
        VideosDto videosDto = new VideosDto();
        assertTrue(mapper.videosDtoToVideos(videosDto).isEmpty());
    }

    @Test
    void videosDtoWithoutSnippetToVideosTest() {
        VideosDto.Item firstVideo = DTOs.getVideo();
        VideosDto.Item secondVideo = new VideosDto.Item(
                new VideosDto.Item.Id(), null
        );
        VideosDto videosDto = new VideosDto(
                new VideosDto.Item[]{firstVideo, secondVideo}
        );
        List<Video> expected = List.of(Models.getVideo());
        List<Video> actual = mapper.videosDtoToVideos(videosDto);
        assertEquals(expected, actual);
    }

    @Test
    void videosDtoWithoutIdToVideosTest() {
        VideosDto.Item firstVideo = DTOs.getVideo();
        VideosDto.Item secondVideo = new VideosDto.Item(
                null, new VideosDto.Item.Snippet()
        );
        VideosDto videosDto = new VideosDto(
                new VideosDto.Item[]{firstVideo, secondVideo}
        );
        List<Video> expected = List.of(Models.getVideo());
        List<Video> actual = mapper.videosDtoToVideos(videosDto);
        assertEquals(expected, actual);
    }

    @Test
    void videoItemToVideoTest() {
        Video expected = Models.getVideo();
        VideosDto.Item item = DTOs.getVideo();
        Video actual = mapper.videoItemToVideo(item);

        assertEquals(expected, actual);
    }

    @Test
    void nullVideoItemToVideoTest() {
        assertNull(mapper.videoItemToVideo(null));
    }

    @Test
    void videoItemWithoutIdToVideoTest() {
        VideosDto.Item item = new VideosDto.Item(new VideosDto.Item.Id(), null);
        assertNull(mapper.videoItemToVideo(item));
    }

    @Test
    void videoItemWithoutSnippetToVideoTest() {
        VideosDto.Item item = new VideosDto.Item(null, new VideosDto.Item.Snippet());
        assertNull(mapper.videoItemToVideo(item));
    }
}
