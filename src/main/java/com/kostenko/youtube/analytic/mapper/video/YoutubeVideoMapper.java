package com.kostenko.youtube.analytic.mapper.video;

import com.kostenko.youtube.analytic.entity.VideoEntity;
import com.kostenko.youtube.analytic.model.Video;
import com.kostenko.youtube.analytic.dto.youtube.analytic.YoutubeAnalyticVideoDto;
import com.kostenko.youtube.analytic.dto.youtube.v3.api.YoutubeV3ApiVideosDto;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(YoutubeVideoDecorator.class)
public interface YoutubeVideoMapper {
    default List<Video> videoDTOsToVideos(YoutubeV3ApiVideosDto videosDto) {
        if (videosDto == null || videosDto.getItems() == null) {
            return new ArrayList<>();
        }
        return Arrays.stream(videosDto.getItems())
                .map(this::videoItemToVideo)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    List<YoutubeAnalyticVideoDto> videosToYoutubeAnalyticVideoDTOs(List<Video> videos);

    List<VideoEntity> videosToVideoEntities(List<Video> videos);

    List<Video> videoEntitiesToVideos(List<VideoEntity> videos);

    @Mapping(target = "videoId", expression = "java(item.getId().getVideoId())")
    @Mapping(target = "title", expression = "java(item.getSnippet().getTitle())")
    @Mapping(target = "description", expression = "java(item.getSnippet().getDescription())")
    @Mapping(target = "publishedAt", expression = "java(item.getSnippet().getPublishedAt())")
    Video videoItemToVideo(YoutubeV3ApiVideosDto.Item item);

    @Mapping(target = "channelId", ignore = true)
    @Mapping(target = "lastCheck", ignore = true)
    VideoEntity videoToVideoEntity(Video video);

    Video videoEntityToVideo(VideoEntity video);
}
