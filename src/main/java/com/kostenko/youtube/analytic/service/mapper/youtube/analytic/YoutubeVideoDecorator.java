package com.kostenko.youtube.analytic.service.mapper.youtube.analytic;

import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.YoutubeV3ApiVideosDto;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;
import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticVideoDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class YoutubeVideoDecorator implements YoutubeVideoMapper {
    private YoutubeVideoMapper delegate;

    @Override
    public Video videoItemToVideo(YoutubeV3ApiVideosDto.Item item) {
        if (item == null || (item.getId() != null && item.getSnippet() != null)) {
            return delegate.videoItemToVideo(item);
        }
        return null;
    }

    @Override
    public List<YoutubeAnalyticVideoDto> videosToYoutubeAnalyticVideoDTOs(List<Video> videos) {
        return delegate.videosToYoutubeAnalyticVideoDTOs(videos);
    }

    @Autowired
    public void setDelegate(YoutubeVideoMapper delegate) {
        this.delegate = delegate;
    }
}
