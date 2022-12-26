package com.kostenko.youtube.analytic.service.youtube.api;

import com.kostenko.youtube.analytic.model.youtube.Channel;
import com.kostenko.youtube.analytic.model.youtube.Video;

import java.util.List;
import java.util.Optional;

public interface YoutubeApiClient {
    List<Video> getVideos(String id);

    Optional<Channel> getChannel(String id);
}
