package com.kostenko.youtube.analytic.service.service.youtube.analytic.client;

import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.YoutubeV3ApiChannelsDto;
import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.YoutubeV3ApiVideosDto;

public interface AnalyticClient {
    YoutubeV3ApiVideosDto getVideosDto(String id);

    YoutubeV3ApiChannelsDto getChannelsDto(String id);
}
