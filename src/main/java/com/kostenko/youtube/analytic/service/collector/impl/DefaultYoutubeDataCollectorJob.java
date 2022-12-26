package com.kostenko.youtube.analytic.service.collector.impl;

import com.kostenko.youtube.analytic.model.youtube.Channel;
import com.kostenko.youtube.analytic.model.youtube.Video;
import com.kostenko.youtube.analytic.service.youtube.api.YoutubeApiClient;
import com.kostenko.youtube.analytic.service.collector.YoutubeDataCollectorJob;
import com.kostenko.youtube.analytic.service.channel.YoutubeChannelsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty("youtube.analytic.collector.enabled")
public class DefaultYoutubeDataCollectorJob implements YoutubeDataCollectorJob {
    private final YoutubeChannelsService youtubeDataService;
    private final YoutubeApiClient analyticClient;

    @Override
    @Scheduled(cron = "${youtube.analytic.collector.cron}")
    public void collectYoutubeData() {
        log.info("Collecting data about channels.");
        List<String> channelIds = youtubeDataService.getChannelIds();
        for (String channelId : channelIds) {
            Optional<Channel> optionalChannel = analyticClient.getChannel(channelId);
            if (optionalChannel.isPresent()) {
                List<Video> videos = analyticClient.getVideos(channelId);
                youtubeDataService.saveChannel(optionalChannel.get(), videos);
            }
        }
        log.info("Data about {} channels was collected.",  channelIds.size());
    }
}
