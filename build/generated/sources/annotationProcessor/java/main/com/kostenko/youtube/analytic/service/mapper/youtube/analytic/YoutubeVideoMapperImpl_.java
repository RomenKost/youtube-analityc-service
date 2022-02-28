package com.kostenko.youtube.analytic.service.mapper.youtube.analytic;

import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticVideoDto;
import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticVideoDto.YoutubeAnalyticVideoDtoBuilder;
import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.YoutubeV3ApiVideosDto.Item;
import com.kostenko.youtube.analytic.service.entity.VideoEntity;
import com.kostenko.youtube.analytic.service.entity.VideoEntity.VideoEntityBuilder;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-28T00:34:21+0200",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.jar, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
@Qualifier("delegate")
public class YoutubeVideoMapperImpl_ implements YoutubeVideoMapper {

    @Override
    public Video videoItemToVideo(Item item) {
        if ( item == null ) {
            return null;
        }

        String videoId = item.getId().getVideoId();
        String title = item.getSnippet().getTitle();
        String description = item.getSnippet().getDescription();
        Date publishedAt = item.getSnippet().getPublishedAt();

        Video video = new Video( videoId, title, description, publishedAt );

        return video;
    }

    @Override
    public List<YoutubeAnalyticVideoDto> videosToYoutubeAnalyticVideoDTOs(List<Video> videos) {
        if ( videos == null ) {
            return null;
        }

        List<YoutubeAnalyticVideoDto> list = new ArrayList<YoutubeAnalyticVideoDto>( videos.size() );
        for ( Video video : videos ) {
            list.add( videoToYoutubeAnalyticVideoDto( video ) );
        }

        return list;
    }

    @Override
    public List<VideoEntity> videosToVideoEntities(List<Video> videos) {
        if ( videos == null ) {
            return null;
        }

        List<VideoEntity> list = new ArrayList<VideoEntity>( videos.size() );
        for ( Video video : videos ) {
            list.add( videoToVideoEntity( video ) );
        }

        return list;
    }

    @Override
    public VideoEntity videoToVideoEntity(Video video) {
        if ( video == null ) {
            return null;
        }

        VideoEntityBuilder videoEntity = VideoEntity.builder();

        videoEntity.videoId( video.getVideoId() );
        videoEntity.title( video.getTitle() );
        videoEntity.description( video.getDescription() );
        videoEntity.publishedAt( video.getPublishedAt() );

        return videoEntity.build();
    }

    protected YoutubeAnalyticVideoDto videoToYoutubeAnalyticVideoDto(Video video) {
        if ( video == null ) {
            return null;
        }

        YoutubeAnalyticVideoDtoBuilder youtubeAnalyticVideoDto = YoutubeAnalyticVideoDto.builder();

        youtubeAnalyticVideoDto.videoId( video.getVideoId() );
        youtubeAnalyticVideoDto.title( video.getTitle() );
        youtubeAnalyticVideoDto.description( video.getDescription() );
        youtubeAnalyticVideoDto.publishedAt( video.getPublishedAt() );

        return youtubeAnalyticVideoDto.build();
    }
}
