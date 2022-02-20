package com.kostenko.youtube.analytic.service.service.youtube.analytic.service.impl;

import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.YoutubeV3ApiChannelsDto;
import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.YoutubeV3ApiVideosDto;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeVideoMapper;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;
import com.kostenko.youtube.analytic.service.service.youtube.analytic.client.AnalyticClient;
import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticChannelDto;
import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticVideoDto;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeChannelMapper;
import com.kostenko.youtube.analytic.service.service.youtube.analytic.service.AnalyticService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YoutubeAnalyticService implements AnalyticService {
    private final AnalyticClient client;

    private final YoutubeVideoMapper dtoToVideoMapper;
    private final YoutubeChannelMapper dtoToChannelMapper;

    public YoutubeAnalyticService(AnalyticClient client, YoutubeVideoMapper dtoToModelMapper, YoutubeChannelMapper dtoToChannelMapper) {
        this.client = client;
        this.dtoToVideoMapper = dtoToModelMapper;
        this.dtoToChannelMapper = dtoToChannelMapper;
    }

    @Override
    public YoutubeAnalyticChannelDto getChannel(String id) {
        YoutubeV3ApiChannelsDto channelsDto = client.getChannelsDto(id);
        Channel channel = dtoToChannelMapper.youtubeV3ApiChannelsDtoToChannel(channelsDto);
        return dtoToChannelMapper.channelToYoutubeAnalyticChannelDto(channel);
    }

    @Override
    public List<YoutubeAnalyticVideoDto> getVideos(String id) {
        YoutubeV3ApiVideosDto videosDto = client.getVideosDto(id);
        List<Video> videos = dtoToVideoMapper.videoDTOsToVideos(videosDto);
        return dtoToVideoMapper.videosToYoutubeAnalyticVideoDTOs(videos);
    }
}
