package com.kostenko.youtubeanalyticservice.mapper.dtotomodelmapper;

import com.kostenko.youtubeanalyticservice.dto.ChannelsDto;
import com.kostenko.youtubeanalyticservice.dto.VideosDto;
import com.kostenko.youtubeanalyticservice.model.Channel;
import com.kostenko.youtubeanalyticservice.model.Video;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",  injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(DtoToModelMapperDecorator.class)
public interface DtoToModelMapper {
    @Mapping(target = "id", expression = "java(channelsDto.getItems()[0].getId())")
    @Mapping(target = "title", expression = "java(channelsDto.getItems()[0].getSnippet().getTitle())")
    @Mapping(target = "description", expression = "java(channelsDto.getItems()[0].getSnippet().getDescription())")
    @Mapping(target = "country", expression = "java(channelsDto.getItems()[0].getSnippet().getCountry())")
    @Mapping(target = "publishedAt", expression = "java(channelsDto.getItems()[0].getSnippet().getPublishedAt())")
    Channel channelsDtoToChannel(ChannelsDto channelsDto);

    default List<Video> videosDtoToVideos(VideosDto videosDto) {
        if (videosDto == null || videosDto.getItems() == null) {
            return new ArrayList<>();
        }
        return Arrays.stream(videosDto.getItems())
                .map(this::videoItemToVideo)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Mapping(target = "videoId", expression ="java(item.getId().getVideoId())")
    @Mapping(target = "title", expression ="java(item.getSnippet().getTitle())")
    @Mapping(target = "description", expression ="java(item.getSnippet().getDescription())")
    @Mapping(target = "publishedAt", expression ="java(item.getSnippet().getPublishedAt())")
    Video videoItemToVideo(VideosDto.Item item);
}
