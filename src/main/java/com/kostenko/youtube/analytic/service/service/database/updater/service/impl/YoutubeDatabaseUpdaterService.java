package com.kostenko.youtube.analytic.service.service.database.updater.service.impl;

import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;
import com.kostenko.youtube.analytic.service.service.database.updater.client.DatabaseUpdaterClient;
import com.kostenko.youtube.analytic.service.service.database.updater.service.DatabaseUpdaterService;
import com.kostenko.youtube.analytic.service.service.youtube.analytic.client.AnalyticClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
@ConditionalOnProperty(value = "database.manager.enabled", matchIfMissing = true, havingValue = "true")
public class YoutubeDatabaseUpdaterService implements DatabaseUpdaterService {
    private final DatabaseUpdaterClient databaseUpdaterClient;
    private final AnalyticClient analyticClient;

    @Override
    @Scheduled(fixedDelayString = "${database.manager.delay}")
    public void processChannels() {
        log.info("Processing channels...");
        List<String> channelIds = databaseUpdaterClient.getChannelIds();

        for (String channelId : channelIds) {
            Optional<Channel> optionalChannel = analyticClient.getChannel(channelId);
            List<Video> videos = analyticClient.getVideos(channelId, "");
            if (!videos.isEmpty() && optionalChannel.isPresent()) {
                databaseUpdaterClient.saveReport(optionalChannel.get(), videos);
            }
        }

        log.info("Channels was processed (count = " + channelIds.size() + ").");
    }
}
