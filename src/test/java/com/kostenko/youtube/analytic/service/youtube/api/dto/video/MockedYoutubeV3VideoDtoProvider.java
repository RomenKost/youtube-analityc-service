package com.kostenko.youtube.analytic.service.youtube.api.dto.video;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.kostenko.youtube.analytic.util.TestConstants.*;

@UtilityClass
public class MockedYoutubeV3VideoDtoProvider {
    public YoutubeV3VideoIdDto getMockedYoutubeV3VideoIdDto() {
        YoutubeV3VideoIdDto id = new YoutubeV3VideoIdDto();
        id.setVideoId(TEST_VIDEO_ID);
        return id;
    }

    public YoutubeV3VideoSnippetDto getMockedYoutubeV3VideoSnippetDto() {
        YoutubeV3VideoSnippetDto snippet = new YoutubeV3VideoSnippetDto();
        snippet.setDescription(TEST_TEXT_FIELD);
        snippet.setTitle(TEST_TEXT_FIELD);
        snippet.setPublishedAt(TEST_DATE);
        return snippet;
    }

    public YoutubeV3VideoDto getMockedYoutubeV3VideoDto() {
        YoutubeV3VideoDto video = new YoutubeV3VideoDto();
        video.setSnippet(getMockedYoutubeV3VideoSnippetDto());
        video.setId(getMockedYoutubeV3VideoIdDto());
        return video;
    }

    public List<YoutubeV3VideoDto> getMockedYoutubeV3VideoDtoList(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> getMockedYoutubeV3VideoDto())
                .collect(Collectors.toList());
    }

    public List<YoutubeV3VideoDto> getMockedYoutubeV3VideoDtoList() {
        return getMockedYoutubeV3VideoDtoList(3);
    }

    public YoutubeV3VideosDto getMockedYoutubeV3VideosDto(String nextPageToken) {
        YoutubeV3VideosDto videosDto = new YoutubeV3VideosDto();
        videosDto.setVideoDtos(getMockedYoutubeV3VideoDtoList());
        videosDto.setNextPageToken(nextPageToken);
        return videosDto;
    }
}
