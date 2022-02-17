package com.kostenko.youtubeanalyticservice.service.analyticservice.impl;

import com.kostenko.youtubeanalyticservice.dto.ChannelsDto;
import com.kostenko.youtubeanalyticservice.dto.VideosDto;
import com.kostenko.youtubeanalyticservice.mapper.dtotomodelmapper.DtoToModelMapper;
import com.kostenko.youtubeanalyticservice.model.Channel;
import com.kostenko.youtubeanalyticservice.model.Video;
import com.kostenko.youtubeanalyticservice.service.analyticclient.AnalyticClient;
import com.kostenko.youtubeanalyticservice.service.analyticservice.AnalyticService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YoutubeAnalyticService implements AnalyticService {
    private final AnalyticClient client;
    private final DtoToModelMapper dtoToModelMapper;

    public YoutubeAnalyticService(AnalyticClient client, DtoToModelMapper dtoToModelMapper) {
        this.client = client;
        this.dtoToModelMapper = dtoToModelMapper;
    }

    @Override
    public Channel getChannel(String id) {
        ChannelsDto channelsDto = client.getChannelsDto(id);
        return dtoToModelMapper.channelsDtoToChannel(channelsDto);
    }

    @Override
    public List<Video> getVideos(String id) {
        VideosDto videosDto = client.getVideosDto(id);
        return dtoToModelMapper.videosDtoToVideos(videosDto);
    }
}
