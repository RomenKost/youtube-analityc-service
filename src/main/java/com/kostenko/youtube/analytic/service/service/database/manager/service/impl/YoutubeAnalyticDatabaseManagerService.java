package com.kostenko.youtube.analytic.service.service.database.manager.service.impl;

import com.kostenko.youtube.analytic.service.exception.YoutubeChannelNotFoundException;
import com.kostenko.youtube.analytic.service.exception.handler.DatabaseManagerExceptionHandler;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;
import com.kostenko.youtube.analytic.service.service.database.manager.client.DatabaseClient;
import com.kostenko.youtube.analytic.service.service.database.manager.service.DatabaseManagerService;
import com.kostenko.youtube.analytic.service.service.youtube.analytic.service.AnalyticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class YoutubeAnalyticDatabaseManagerService implements DatabaseManagerService {
    private final DatabaseClient databaseClient;
    private final AnalyticService analyticService;

    private final DatabaseManagerExceptionHandler exceptionHandler;

    private final boolean processChannelsJobEnabled;

    public YoutubeAnalyticDatabaseManagerService(DatabaseClient databaseClient,
                                                 AnalyticService analyticService,
                                                 DatabaseManagerExceptionHandler exceptionHandler,
                                                 @Value("${database.manager.enabled}") boolean processChannelsJobEnable) {
        this.databaseClient = databaseClient;
        this.analyticService = analyticService;

        this.exceptionHandler = exceptionHandler;

        this.processChannelsJobEnabled = processChannelsJobEnable;
    }

    @Override
    @Scheduled(fixedDelayString = "${database.manager.delay}")
    public void processChannels() {
        if (!processChannelsJobEnabled) {
            return;
        }
        try {
            log.info("Processing channels...");
            List<String> channelIds = databaseClient.getChannelIds();

            for (String channelId : channelIds) {
                Channel channel = analyticService.getChannel(channelId);
                List<Video> videos = analyticService.getVideos(channelId);

                databaseClient.saveReport(channel, videos);
            }

            log.info("Channels was processed (count = " + channelIds.size() + ").");
        } catch (Exception exception) {
            exceptionHandler.processThrowable(exception);
        }
    }

    @Override
    public Channel getChannel(String id) {
        log.info("Loading information about channel with id = " + id + " from database.");
        Channel channel = databaseClient.getChannel(id);
        if (channel == null) {
            throw new YoutubeChannelNotFoundException(id);
        }
        log.info("Information about channel with id = " + id + " was loaded from database.");
        return channel;
    }

    @Override
    public List<Video> getVideos(String id) {
        log.info("Loading information about videos of channel with id = " + id + " from database...");
        List<Video> videos = databaseClient.getVideos(id);
        if (videos.isEmpty()) {
            throw new YoutubeChannelNotFoundException(id);
        }
        log.info("Information about videos of channel with id = " + id + " was loaded from database...");
        return videos;
    }
}
