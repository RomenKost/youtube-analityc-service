package com.kostenko.youtube.analytic.service.mapper.youtube.analytic;

import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticDto;
import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticVideoDto;
import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.V3ApiVideosDto;
import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.YoutubeV3ApiVideosDto;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Models;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class YoutubeVideoMapperTest {
    @Autowired
    private YoutubeVideoMapper videoMapper;

    @Test
    void videoDTOsToVideosTest() {
        List<Video> expected = Models.getVideos();
        YoutubeV3ApiVideosDto videosDto = V3ApiVideosDto.getVideos();
        List<Video> actual = videoMapper.videoDTOsToVideos(videosDto);

        assertEquals(expected, actual);
    }

    @Test
    void nullVideoDTOsToVideosTest() {
        assertTrue(videoMapper.videoDTOsToVideos(null).isEmpty());
    }

    @Test
    void emptyVideoDTOsToVideosTest() {
        YoutubeV3ApiVideosDto videosDto = new YoutubeV3ApiVideosDto(new YoutubeV3ApiVideosDto.Item[0]);
        assertTrue(videoMapper.videoDTOsToVideos(videosDto).isEmpty());
    }

    @Test
    void videoDTOsWithoutItemsToVideosTest() {
        YoutubeV3ApiVideosDto videosDto = new YoutubeV3ApiVideosDto();
        assertTrue(videoMapper.videoDTOsToVideos(videosDto).isEmpty());
    }

    @Test
    void videoDTOsWithoutSnippetToVideosTest() {
        YoutubeV3ApiVideosDto.Item firstVideo = V3ApiVideosDto.getVideo();
        YoutubeV3ApiVideosDto.Item secondVideo = new YoutubeV3ApiVideosDto.Item(
                new YoutubeV3ApiVideosDto.Item.Id(), null
        );
        YoutubeV3ApiVideosDto videosDto = new YoutubeV3ApiVideosDto(
                new YoutubeV3ApiVideosDto.Item[]{firstVideo, secondVideo}
        );
        List<Video> expected = List.of(Models.getVideo());
        List<Video> actual = videoMapper.videoDTOsToVideos(videosDto);
        assertEquals(expected, actual);
    }

    @Test
    void videoDTOsWithoutIdToVideosTest() {
        YoutubeV3ApiVideosDto.Item firstVideo = V3ApiVideosDto.getVideo();
        YoutubeV3ApiVideosDto.Item secondVideo = new YoutubeV3ApiVideosDto.Item(
                null, new YoutubeV3ApiVideosDto.Item.Snippet()
        );
        YoutubeV3ApiVideosDto videosDto = new YoutubeV3ApiVideosDto(
                new YoutubeV3ApiVideosDto.Item[]{firstVideo, secondVideo}
        );
        List<Video> expected = List.of(Models.getVideo());
        List<Video> actual = videoMapper.videoDTOsToVideos(videosDto);
        assertEquals(expected, actual);
    }

    @Test
    void videoItemToVideoTest() {
        Video expected = Models.getVideo();
        YoutubeV3ApiVideosDto.Item item = V3ApiVideosDto.getVideo();
        Video actual = videoMapper.videoItemToVideo(item);

        assertEquals(expected, actual);
    }

    @Test
    void nullVideoItemToVideoTest() {
        assertNull(videoMapper.videoItemToVideo(null));
    }

    @Test
    void videoItemWithoutIdToVideoTest() {
        YoutubeV3ApiVideosDto.Item item = new YoutubeV3ApiVideosDto.Item(new YoutubeV3ApiVideosDto.Item.Id(), null);
        assertNull(videoMapper.videoItemToVideo(item));
    }

    @Test
    void videoItemWithoutSnippetToVideoTest() {
        YoutubeV3ApiVideosDto.Item item = new YoutubeV3ApiVideosDto.Item(null, new YoutubeV3ApiVideosDto.Item.Snippet());
        assertNull(videoMapper.videoItemToVideo(item));
    }

    @Test
    void videosToYoutubeAnalyticVideoDTOsTest() {
        List<YoutubeAnalyticVideoDto> expected = YoutubeAnalyticDto.getVideos();
        List<Video> videos = Models.getVideos();
        List<YoutubeAnalyticVideoDto> actual = videoMapper.videosToYoutubeAnalyticVideoDTOs(videos);

        assertEquals(expected, actual);
    }

    @Test
    void nullVideosToYoutubeAnalyticVideoDTOsTest() {
        assertNull(videoMapper.videosToYoutubeAnalyticVideoDTOs(null));
    }

    @Test
    void videosWithNullVideoToYoutubeAnalyticVideoDTOsTest() {
        List<YoutubeAnalyticVideoDto> expected = new ArrayList<>();
        expected.add(YoutubeAnalyticDto.getVideo());
        expected.add(null);

        List<Video> videos = new ArrayList<>();
        videos.add(Models.getVideo());
        videos.add(null);

        List<YoutubeAnalyticVideoDto> actual = videoMapper.videosToYoutubeAnalyticVideoDTOs(videos);

        assertEquals(expected, actual);
    }
}
