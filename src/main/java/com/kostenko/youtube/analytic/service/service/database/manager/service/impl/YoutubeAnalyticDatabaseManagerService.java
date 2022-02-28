package com.kostenko.youtube.analytic.service.service.database.manager.service.impl;

import com.kostenko.youtube.analytic.service.entity.ChannelIdEntity;
import com.kostenko.youtube.analytic.service.exception.handler.DatabaseManagerExceptionHandler;
import com.kostenko.youtube.analytic.service.mapper.database.manager.ChannelIdMapper;
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

    private final ChannelIdMapper channelIdMapper;
    private final DatabaseManagerExceptionHandler exceptionHandler;

    private final boolean processChannelsJobEnabled;

    public YoutubeAnalyticDatabaseManagerService(DatabaseClient databaseClient,
                                                 AnalyticService analyticService,
                                                 ChannelIdMapper channelIdMapper,
                                                 DatabaseManagerExceptionHandler exceptionHandler,
                                                 @Value("${database.manager.enabled}") boolean processChannelsJobEnable) {
        this.databaseClient = databaseClient;
        this.analyticService = analyticService;

        this.channelIdMapper = channelIdMapper;
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

            List<ChannelIdEntity> channelIdEntities = databaseClient.getChannelIds();
            List<String> channelIds = channelIdMapper.channelIdEntitiesToStrings(channelIdEntities);

            for (String channelId : channelIds) {
                Channel channel = analyticService.getChannel(channelId);
                List<Video> videos = analyticService.getVideos(channelId);

                databaseClient.saveReport(channel, videos);
            }

            log.info("Channels was processed (count = " + channelIdEntities.size() + ").");
        } catch (Exception exception) {
            exceptionHandler.processThrowable(exception);
        }
    }
}
