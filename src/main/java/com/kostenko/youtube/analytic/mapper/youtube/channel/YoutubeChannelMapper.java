package com.kostenko.youtube.analytic.mapper.youtube.channel;

import com.kostenko.youtube.analytic.dto.youtube.analytic.YoutubeAnalyticChannelDto;
import com.kostenko.youtube.analytic.model.youtube.Channel;
import com.kostenko.youtube.analytic.dto.youtube.v3.api.YoutubeV3ApiChannelsDto;
import com.kostenko.youtube.analytic.entity.youtube.ChannelEntity;
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

    YoutubeAnalyticChannelDto channelToYoutubeAnalyticChannelDto(Channel channel);

    @Mapping(target = "videoEntities", ignore = true)
    @Mapping(target = "lastCheck", ignore = true)
    ChannelEntity channelToChannelEntity(Channel channel);

    Channel channelEntityToChannel(ChannelEntity channel);
}
