package com.kostenko.youtubeanalyticservice.service.analyticservice;

import com.kostenko.youtubeanalyticservice.model.Channel;
import com.kostenko.youtubeanalyticservice.model.Video;

import java.util.List;

public interface AnalyticService {
    Channel getChannel(String id);

    List<Video> getVideos(String id);
}
