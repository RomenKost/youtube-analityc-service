package com.kostenko.youtube.analytic.service.channel.mapper.channel;

import com.kostenko.youtube.analytic.controller.dto.analytic.AnalyticChannelDto;
import com.kostenko.youtube.analytic.service.youtube.api.dto.channel.YoutubeV3ChannelDto;
import com.kostenko.youtube.analytic.model.youtube.Channel;
import com.kostenko.youtube.analytic.repository.youtube.entity.ChannelEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",  injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface YoutubeChannelMapper {
    List<Channel> youtubeV3ChannelsDtoToChannels(List<YoutubeV3ChannelDto> itemDtos);

    @Mapping(target = "title", source = "snippet.title")
    @Mapping(target = "description", source = "snippet.description")
    @Mapping(target = "country", source = "snippet.country")
    @Mapping(target = "publishedAt", source = "snippet.publishedAt")
    Channel youtubeV3ChannelDtoToChannel(YoutubeV3ChannelDto channelDto);

    Channel channelEntityToChannel(ChannelEntity channel);

    @Mapping(target = "videoEntities", ignore = true)
    @Mapping(target = "lastCheck", ignore = true)
    ChannelEntity channelToChannelEntity(Channel channel);

    AnalyticChannelDto channelToYoutubeAnalyticChannelDto(Channel channel);
}
