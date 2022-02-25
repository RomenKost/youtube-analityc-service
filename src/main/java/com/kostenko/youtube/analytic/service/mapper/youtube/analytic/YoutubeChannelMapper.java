package com.kostenko.youtube.analytic.service.mapper.youtube.analytic;

import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.YoutubeV3ApiChannelsDto;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticChannelDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",  injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(YoutubeChannelDecorator.class)
public interface YoutubeChannelMapper {
    @Mapping(target = "id", expression = "java(channelsDto.getItems()[0].getId())")
    @Mapping(target = "title", expression = "java(channelsDto.getItems()[0].getSnippet().getTitle())")
    @Mapping(target = "description", expression = "java(channelsDto.getItems()[0].getSnippet().getDescription())")
    @Mapping(target = "country", expression = "java(channelsDto.getItems()[0].getSnippet().getCountry())")
    @Mapping(target = "publishedAt", expression = "java(channelsDto.getItems()[0].getSnippet().getPublishedAt())")
    Channel youtubeV3ApiChannelsDtoToChannel(YoutubeV3ApiChannelsDto channelsDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "country", source = "country")
    @Mapping(target = "publishedAt", source = "publishedAt")
    YoutubeAnalyticChannelDto channelToYoutubeAnalyticChannelDto(Channel channel);
}

