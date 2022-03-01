package com.kostenko.youtube.analytic.service.dto.youtube.analytic;

import lombok.experimental.UtilityClass;

import java.util.Date;
import java.util.List;

@UtilityClass
public class YoutubeAnalyticDto {
    public List<YoutubeAnalyticVideoDto> getVideoDTOs() {
        YoutubeAnalyticVideoDto firstVideo = getVideoDto();
        YoutubeAnalyticVideoDto secondVideo = YoutubeAnalyticVideoDto
                .builder()
                .videoId("another id")
                .title("another title")
                .description("another description")
                .publishedAt(new Date(1_700_000_000_000L))
                .build();
        return List.of(firstVideo, secondVideo);
    }

    public YoutubeAnalyticChannelDto getChannelDto() {
        return YoutubeAnalyticChannelDto
                .builder()
                .id("any id")
                .title("any title")
                .description("any description")
                .country("ac")
                .publishedAt(new Date(1_600_000_000_000L))
                .build();
    }

    public YoutubeAnalyticVideoDto getVideoDto() {
        return YoutubeAnalyticVideoDto
                .builder()
                .videoId("any id")
                .title("any title")
                .description("any description")
                .publishedAt(new Date(1_600_000_000_000L))
                .build();
    }
}
