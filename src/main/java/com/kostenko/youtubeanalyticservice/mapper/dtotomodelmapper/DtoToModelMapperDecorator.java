package com.kostenko.youtubeanalyticservice.mapper.dtotomodelmapper;

import com.kostenko.youtubeanalyticservice.dto.ChannelsDto;
import com.kostenko.youtubeanalyticservice.dto.VideosDto;
import com.kostenko.youtubeanalyticservice.model.Channel;
import com.kostenko.youtubeanalyticservice.model.Video;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DtoToModelMapperDecorator implements DtoToModelMapper {
    private DtoToModelMapper delegate;

    @Override
    public Channel channelsDtoToChannel(ChannelsDto channelsDto) {
        if (channelsDto == null || (channelsDto.getItems() != null
                && channelsDto.getItems().length != 0
                && channelsDto.getItems()[0].getSnippet() != null)) {
            return delegate.channelsDtoToChannel(channelsDto);
        }
        return null;
    }

    @Override
    public Video videoItemToVideo(VideosDto.Item item) {
        if (item == null || (item.getId() != null && item.getSnippet() != null)) {
            return delegate.videoItemToVideo(item);
        }
        return null;
    }

    @Autowired
    public void setDtoToModelMapper(DtoToModelMapper delegate) {
        this.delegate = delegate;
    }
}
