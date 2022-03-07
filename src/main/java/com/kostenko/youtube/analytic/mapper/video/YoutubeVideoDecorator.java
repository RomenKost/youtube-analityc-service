package com.kostenko.youtube.analytic.mapper.video;

import com.kostenko.youtube.analytic.dto.youtube.analytic.YoutubeAnalyticVideoDto;
import com.kostenko.youtube.analytic.dto.youtube.v3.api.YoutubeV3ApiVideosDto;
import com.kostenko.youtube.analytic.model.Video;
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
