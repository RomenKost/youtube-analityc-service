package com.kostenko.youtube.analytic.service.service.youtube.analytic.client;

import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;

import java.util.List;
import java.util.Optional;

public interface AnalyticClient {
    List<Video> getVideos(String id, String pageToken);

    Optional<Channel> getChannel(String id);
}
