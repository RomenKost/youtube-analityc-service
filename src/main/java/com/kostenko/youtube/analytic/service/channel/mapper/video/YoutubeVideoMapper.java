package com.kostenko.youtube.analytic.service.channel.mapper.video;

import com.kostenko.youtube.analytic.repository.youtube.entity.VideoEntity;
import com.kostenko.youtube.analytic.model.youtube.Video;
import com.kostenko.youtube.analytic.controller.dto.analytic.AnalyticVideoDto;
import com.kostenko.youtube.analytic.service.youtube.api.dto.video.YoutubeV3VideoDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface YoutubeVideoMapper {
    List<AnalyticVideoDto> videosToYoutubeAnalyticVideoDtoList(List<Video> videos);

    List<VideoEntity> videosToVideoEntities(List<Video> videos);

    List<Video> videoEntitiesToVideos(List<VideoEntity> videos);

    List<Video> youtubeV3VideoDtosToVideos(List<YoutubeV3VideoDto> videoDtos);

    @Mapping(target = "videoId", source = "id.videoId")
    @Mapping(target = "title", source = "snippet.title")
    @Mapping(target = "description", source = "snippet.description")
    @Mapping(target = "publishedAt", source = "snippet.publishedAt")
    Video youtubeV3VideoDtoToVideo(YoutubeV3VideoDto videoDto);

    @Mapping(target = "channelId", ignore = true)
    @Mapping(target = "lastCheck", ignore = true)
    VideoEntity videoToVideoEntity(Video video);

    Video videoEntityToVideo(VideoEntity video);
}
