package com.kostenko.youtube.analytic.service.service.database.manager.client;

import com.kostenko.youtube.analytic.service.entity.ChannelIdEntity;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;

import java.util.List;

public interface DatabaseClient {
    List<ChannelIdEntity> getChannelIds();

    void saveReport(Channel channel, List<Video> videos);
}
