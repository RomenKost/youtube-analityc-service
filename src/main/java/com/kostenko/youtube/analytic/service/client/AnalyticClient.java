package com.kostenko.youtube.analytic.service.client;

import com.kostenko.youtube.analytic.model.Channel;
import com.kostenko.youtube.analytic.model.Video;

import java.util.List;
import java.util.Optional;

public interface AnalyticClient {
    List<Video> getVideos(String id, String pageToken);

    Optional<Channel> getChannel(String id);
}
