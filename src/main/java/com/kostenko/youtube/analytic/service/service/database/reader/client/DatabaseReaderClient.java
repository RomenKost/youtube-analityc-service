package com.kostenko.youtube.analytic.service.service.database.reader.client;

import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;

import java.util.List;

public interface DatabaseReaderClient {
    Channel getChannel(String id);

    List<Video> getVideos(String channelId);
}
