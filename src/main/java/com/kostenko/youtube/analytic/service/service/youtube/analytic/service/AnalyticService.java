package com.kostenko.youtube.analytic.service.service.youtube.analytic.service;

import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;

import java.util.List;

public interface AnalyticService {
    Channel getChannel(String id);

    List<Video> getVideos(String id);
}
