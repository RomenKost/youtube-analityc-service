package com.kostenko.youtube.analytic.service.service.database.updater.client;

import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;

import java.util.List;

public interface DatabaseUpdaterClient {
    void saveReport(Channel channel, List<Video> videos);

    List<String> getChannelIds();
}
