package com.kostenko.youtube.analytic.service.youtube.procesor;

import com.kostenko.youtube.analytic.model.youtube.Channel;
import com.kostenko.youtube.analytic.model.youtube.Video;

import java.util.List;

public interface YoutubeAnalyticDataProcessor {
    Channel getChannel(String id);

    List<Video> getVideos(String channelId);

    List<String> getChannelIds();

    void saveReport(Channel channel, List<Video> videos);
}
