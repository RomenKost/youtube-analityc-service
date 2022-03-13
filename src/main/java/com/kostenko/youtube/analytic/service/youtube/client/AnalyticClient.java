package com.kostenko.youtube.analytic.service.youtube.client;

import com.kostenko.youtube.analytic.model.youtube.Channel;
import com.kostenko.youtube.analytic.model.youtube.Video;

import java.util.List;
import java.util.Optional;

public interface AnalyticClient {
    List<Video> getVideos(String id, String pageToken);

    Optional<Channel> getChannel(String id);
}
