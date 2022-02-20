package com.kostenko.youtube.analytic.service.service.youtube.analytic.service;

import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticChannelDto;
import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticVideoDto;
import java.util.List;

public interface AnalyticService {
    YoutubeAnalyticChannelDto getChannel(String id);

    List<YoutubeAnalyticVideoDto> getVideos(String id);
}
