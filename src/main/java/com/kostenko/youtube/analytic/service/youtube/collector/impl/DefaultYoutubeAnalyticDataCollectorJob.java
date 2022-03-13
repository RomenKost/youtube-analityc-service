package com.kostenko.youtube.analytic.service.youtube.collector.impl;

import com.kostenko.youtube.analytic.model.youtube.Channel;
import com.kostenko.youtube.analytic.model.youtube.Video;
import com.kostenko.youtube.analytic.service.youtube.client.AnalyticClient;
import com.kostenko.youtube.analytic.service.youtube.collector.YoutubeAnalyticDataCollectorJob;
import com.kostenko.youtube.analytic.service.youtube.procesor.YoutubeAnalyticDataProcessor;
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
@ConditionalOnProperty(
        value = "youtube.analytic.collector.enabled",
        matchIfMissing = true,
        havingValue = "true"
)
public class DefaultYoutubeAnalyticDataCollectorJob implements YoutubeAnalyticDataCollectorJob {
    private final YoutubeAnalyticDataProcessor dataProcessor;
    private final AnalyticClient analyticClient;

    @Override
    @Scheduled(fixedDelayString = "${youtube.analytic.collector.delay}")
    public void processChannels() {
        log.info("Processing channels...");
        List<String> channelIds = dataProcessor.getChannelIds();

        for (String channelId : channelIds) {
            Optional<Channel> optionalChannel = analyticClient.getChannel(channelId);
            List<Video> videos = analyticClient.getVideos(channelId, "");
            if (!videos.isEmpty() && optionalChannel.isPresent()) {
                dataProcessor.saveReport(optionalChannel.get(), videos);
            }
        }

        log.info("Channels was processed (count = " + channelIds.size() + ").");
    }
}
