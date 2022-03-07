package com.kostenko.youtube.analytic.mapper.video;

import com.kostenko.youtube.analytic.dto.youtube.analytic.YoutubeAnalyticDto;
import com.kostenko.youtube.analytic.dto.youtube.analytic.YoutubeAnalyticVideoDto;
import com.kostenko.youtube.analytic.model.Models;
import com.kostenko.youtube.analytic.model.Video;
import com.kostenko.youtube.analytic.dto.youtube.v3.api.YoutubeV3ApiDTOs;
import com.kostenko.youtube.analytic.dto.youtube.v3.api.YoutubeV3ApiVideosDto;
import com.kostenko.youtube.analytic.entity.Entities;
import com.kostenko.youtube.analytic.entity.VideoEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = YoutubeVideoMapperTest.class)
@ComponentScan(basePackageClasses = YoutubeVideoMapper.class)
class YoutubeVideoMapperTest {
    @Autowired
    private YoutubeVideoMapper videoMapper;

    @Test
    void videoDTOsToVideosTest() {
        List<Video> expected = Models.getVideos();
        YoutubeV3ApiVideosDto videosDto = YoutubeV3ApiDTOs.getVideoDTOs();
        List<Video> actual = videoMapper.videoDTOsToVideos(videosDto);

        assertEquals(expected, actual);
    }

    @Test
    void videoDTOsToVideosWhenDtoIsNullReturnEmptyListTest() {
        assertTrue(videoMapper.videoDTOsToVideos(null).isEmpty());
    }

    @Test
    void videoDTOsToVideosWhenDtoHasEmptyItemsReturnEmptyListTest() {
        YoutubeV3ApiVideosDto videosDto = new YoutubeV3ApiVideosDto(new YoutubeV3ApiVideosDto.Item[0], null);
        assertTrue(videoMapper.videoDTOsToVideos(videosDto).isEmpty());
    }

    @Test
    void videoDTOsToVideosWhenDtoHasNotItemsReturnEmptyListTest() {
        YoutubeV3ApiVideosDto videosDto = new YoutubeV3ApiVideosDto();
        assertTrue(videoMapper.videoDTOsToVideos(videosDto).isEmpty());
    }

    @Test
    void videoDTOsToVideosWhenDtoItemHasNotSnippetReturnListWithoutThisMappedItemTest() {
        YoutubeV3ApiVideosDto.Item firstVideo = YoutubeV3ApiDTOs.getVideoDtoItem();
        YoutubeV3ApiVideosDto.Item secondVideo = new YoutubeV3ApiVideosDto.Item(
                new YoutubeV3ApiVideosDto.Item.Id(), null
        );
        YoutubeV3ApiVideosDto videosDto = new YoutubeV3ApiVideosDto(
                new YoutubeV3ApiVideosDto.Item[]{firstVideo, secondVideo}, null
        );
        List<Video> expected = List.of(Models.getVideo());
        List<Video> actual = videoMapper.videoDTOsToVideos(videosDto);
        assertEquals(expected, actual);
    }

    @Test
    void videoDTOsToVideosWhenDtoItemHasNotIdReturnListWithoutThisMappedDtoItemTest() {
        YoutubeV3ApiVideosDto.Item firstVideo = YoutubeV3ApiDTOs.getVideoDtoItem();
        YoutubeV3ApiVideosDto.Item secondVideo = new YoutubeV3ApiVideosDto.Item(
                null, new YoutubeV3ApiVideosDto.Item.Snippet()
        );
        YoutubeV3ApiVideosDto videosDto = new YoutubeV3ApiVideosDto(
                new YoutubeV3ApiVideosDto.Item[]{firstVideo, secondVideo}, null
        );
        List<Video> expected = List.of(Models.getVideo());
        List<Video> actual = videoMapper.videoDTOsToVideos(videosDto);
        assertEquals(expected, actual);
    }

    @Test
    void videoItemToVideoTest() {
        Video expected = Models.getVideo();
        YoutubeV3ApiVideosDto.Item item = YoutubeV3ApiDTOs.getVideoDtoItem();
        Video actual = videoMapper.videoItemToVideo(item);

        assertEquals(expected, actual);
    }

    @Test
    void videoItemToVideoWhenVideoIsNullReturnNullTest() {
        assertNull(videoMapper.videoItemToVideo(null));
    }

    @Test
    void videoItemToVideoWhenItemIsWithoutSnippedReturnNullTest() {
        YoutubeV3ApiVideosDto.Item item = new YoutubeV3ApiVideosDto.Item(new YoutubeV3ApiVideosDto.Item.Id(), null);
        assertNull(videoMapper.videoItemToVideo(item));
    }

    @Test
    void videoItemToVideoWhenItemIsWithoutIdReturnNullTest() {
        YoutubeV3ApiVideosDto.Item item = new YoutubeV3ApiVideosDto.Item(null, new YoutubeV3ApiVideosDto.Item.Snippet());
        assertNull(videoMapper.videoItemToVideo(item));
    }

    @Test
    void videosToYoutubeAnalyticVideoDTOsTest() {
        List<YoutubeAnalyticVideoDto> expected = YoutubeAnalyticDto.getVideoDTOs();
        List<Video> videos = Models.getVideos();
        List<YoutubeAnalyticVideoDto> actual = videoMapper.videosToYoutubeAnalyticVideoDTOs(videos);

        assertEquals(expected, actual);
    }

    @Test
    void videosToYoutubeAnalyticVideoDTOsWhenVideosIsNullReturnNullTest() {
        assertNull(videoMapper.videosToYoutubeAnalyticVideoDTOs(null));
    }

    @Test
    void videosToYoutubeAnalyticVideoDTOsWhenOneVideoIsNullReturnListWithoutThisMappedVideoTest() {
        List<YoutubeAnalyticVideoDto> expected = new ArrayList<>();
        expected.add(YoutubeAnalyticDto.getVideoDto());
        expected.add(null);

        List<Video> videos = new ArrayList<>();
        videos.add(Models.getVideo());
        videos.add(null);

        List<YoutubeAnalyticVideoDto> actual = videoMapper.videosToYoutubeAnalyticVideoDTOs(videos);

        assertEquals(expected, actual);
    }

    @Test
    void videoToVideoEntityTest() {
        VideoEntity expected = Entities.getVideoEntity();
        Video video = Models.getVideo();

        VideoEntity actual = videoMapper.videoToVideoEntity(video);

        assertEquals(expected, actual);
    }

    @Test
    void videoToVideoEntityWhenVideoIsNullReturnNullTest() {
        assertNull(videoMapper.videoToVideoEntity(null));
    }

    @Test
    void videosToVideoEntitiesTest() {
        List<VideoEntity> expected = Entities.getVideoEntities();
        List<Video> videos = Models.getVideos();

        List<VideoEntity> actual = videoMapper.videosToVideoEntities(videos);

        assertEquals(expected, actual);
    }

    @Test
    void videosToVideoEntitiesWhenVideosIsNullReturnNullTest() {
        assertNull(videoMapper.videosToVideoEntities(null));
    }

    @Test
    void videoEntitiesToVideosTest() {
        List<Video> expected = Models.getVideos();
        List<VideoEntity> videoEntities = Entities.getVideoEntities();

        List<Video> actual = videoMapper.videoEntitiesToVideos(videoEntities);

        assertEquals(expected, actual);
    }

    @Test
    void videoEntitiesToVideosWhenVideosIsEmptyReturnNullTest() {
        assertNull(videoMapper.videoEntitiesToVideos(null));
    }

    @Test
    void videoEntityToVideoTest() {
        Video expected = Models.getVideo();
        VideoEntity videoEntity = Entities.getVideoEntity();

        Video actual = videoMapper.videoEntityToVideo(videoEntity);

        assertEquals(expected, actual);
    }

    @Test
    void videoEntityToVideoWhenEntityIsNullReturnNullTest() {
        assertNull(videoMapper.videoEntityToVideo(null));
    }
}
