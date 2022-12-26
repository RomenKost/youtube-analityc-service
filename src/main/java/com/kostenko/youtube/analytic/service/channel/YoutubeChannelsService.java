package com.kostenko.youtube.analytic.service.channel;

import com.kostenko.youtube.analytic.model.youtube.Channel;
import com.kostenko.youtube.analytic.model.youtube.Video;

import java.util.List;

public interface YoutubeChannelsService {
    Channel getChannel(String id);

    List<Video> getVideos(String channelId);

    List<String> getChannelIds();

    void saveChannel(Channel channel, List<Video> videos);
}
