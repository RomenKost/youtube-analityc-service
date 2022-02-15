package com.kostenko.youtubeanalyticservice.model;

import com.kostenko.youtubeanalyticservice.model.YoutubeChannelDto;
import com.kostenko.youtubeanalyticservice.model.YoutubeVideoDto;
import lombok.experimental.UtilityClass;

import java.util.Date;
import java.util.List;

@UtilityClass
public class Entities {
    public List<YoutubeVideoDto> getVideos() {
        YoutubeVideoDto firstExpectedVideo = YoutubeVideoDto
                .builder()
                .videoId("any video id")
                .title("any title")
                .description("any description")
                .publishedAt(new Date(1_600_000_000_000L))
                .build();

        YoutubeVideoDto secondExpectedVideo = YoutubeVideoDto
                .builder()
                .videoId("another video id")
                .title("another title")
                .description("another description")
                .publishedAt(new Date(1_700_000_000_000L))
                .build();
        return List.of(firstExpectedVideo, secondExpectedVideo);
    }

    public YoutubeChannelDto getChannel() {
        return YoutubeChannelDto
                .builder()
                .title("any title")
                .publishedAt(new Date(1_600_000_000_000L))
                .description("any description")
                .country("any country")
                .build();
    }
}
