package com.kostenko.youtube.analytic.service.mapper.youtube.analytic;

import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticChannelDto;
import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticChannelDto.YoutubeAnalyticChannelDtoBuilder;
import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.YoutubeV3ApiChannelsDto;
import com.kostenko.youtube.analytic.service.entity.ChannelEntity;
import com.kostenko.youtube.analytic.service.entity.ChannelEntity.ChannelEntityBuilder;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import java.util.Date;
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
public class YoutubeChannelMapperImpl_ implements YoutubeChannelMapper {

    @Override
    public Channel youtubeV3ApiChannelsDtoToChannel(YoutubeV3ApiChannelsDto channelsDto) {
        if ( channelsDto == null ) {
            return null;
        }

        String id = channelsDto.getItems()[0].getId();
        String title = channelsDto.getItems()[0].getSnippet().getTitle();
        String description = channelsDto.getItems()[0].getSnippet().getDescription();
        String country = channelsDto.getItems()[0].getSnippet().getCountry();
        Date publishedAt = channelsDto.getItems()[0].getSnippet().getPublishedAt();

        Channel channel = new Channel( id, title, description, country, publishedAt );

        return channel;
    }

    @Override
    public YoutubeAnalyticChannelDto channelToYoutubeAnalyticChannelDto(Channel channel) {
        if ( channel == null ) {
            return null;
        }

        YoutubeAnalyticChannelDtoBuilder youtubeAnalyticChannelDto = YoutubeAnalyticChannelDto.builder();

        youtubeAnalyticChannelDto.id( channel.getId() );
        youtubeAnalyticChannelDto.title( channel.getTitle() );
        youtubeAnalyticChannelDto.description( channel.getDescription() );
        youtubeAnalyticChannelDto.country( channel.getCountry() );
        youtubeAnalyticChannelDto.publishedAt( channel.getPublishedAt() );

        return youtubeAnalyticChannelDto.build();
    }

    @Override
    public ChannelEntity channelToChannelEntity(Channel channel) {
        if ( channel == null ) {
            return null;
        }

        ChannelEntityBuilder channelEntity = ChannelEntity.builder();

        channelEntity.id( channel.getId() );
        channelEntity.title( channel.getTitle() );
        channelEntity.description( channel.getDescription() );
        channelEntity.country( channel.getCountry() );
        channelEntity.publishedAt( channel.getPublishedAt() );

        return channelEntity.build();
    }
}
