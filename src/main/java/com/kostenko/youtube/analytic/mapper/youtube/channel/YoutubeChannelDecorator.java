package com.kostenko.youtube.analytic.mapper.youtube.channel;

import com.kostenko.youtube.analytic.dto.youtube.analytic.YoutubeAnalyticChannelDto;
import com.kostenko.youtube.analytic.model.youtube.Channel;
import com.kostenko.youtube.analytic.dto.youtube.v3.api.YoutubeV3ApiChannelsDto;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class YoutubeChannelDecorator implements YoutubeChannelMapper {
    private YoutubeChannelMapper delegate;

    @Override
    public Channel youtubeV3ApiChannelsDtoToChannel(YoutubeV3ApiChannelsDto channelsDto) {
        if (channelsDto == null || (channelsDto.getItems() != null
                && channelsDto.getItems().length != 0
                && channelsDto.getItems()[0].getSnippet() != null)) {
            return delegate.youtubeV3ApiChannelsDtoToChannel(channelsDto);
        }
        return null;
    }

    @Override
    public YoutubeAnalyticChannelDto channelToYoutubeAnalyticChannelDto(Channel channel) {
        return delegate.channelToYoutubeAnalyticChannelDto(channel);
    }

    @Autowired
    public void setDelegate(YoutubeChannelMapper delegate) {
        this.delegate = delegate;
    }
}
