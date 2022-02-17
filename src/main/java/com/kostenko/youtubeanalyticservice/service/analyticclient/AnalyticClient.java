package com.kostenko.youtubeanalyticservice.service.analyticclient;

import com.kostenko.youtubeanalyticservice.dto.ChannelsDto;
import com.kostenko.youtubeanalyticservice.dto.VideosDto;

public interface AnalyticClient {
    VideosDto getVideosDto(String id);

    ChannelsDto getChannelsDto(String id);
}
