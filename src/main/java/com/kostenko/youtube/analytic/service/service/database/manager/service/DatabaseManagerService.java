package com.kostenko.youtube.analytic.service.service.database.manager.service;


import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;

import java.util.List;

public interface DatabaseManagerService {
    void processChannels();

    Channel getChannel(String id);

    List<Video> getVideos(String id);
}
