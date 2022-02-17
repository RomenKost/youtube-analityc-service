package com.kostenko.youtubeanalyticservice.controller;

import com.kostenko.youtubeanalyticservice.model.Channel;
import com.kostenko.youtubeanalyticservice.model.Video;
import com.kostenko.youtubeanalyticservice.service.analyticservice.AnalyticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/youtube/analytic/v1")
public class YoutubeAnalyticController {
    private final AnalyticService youtubeService;

    @Autowired
    public YoutubeAnalyticController(AnalyticService youtubeService) {
        this.youtubeService = youtubeService;
    }

    @GetMapping("/channel/{channelId}")
    public Channel channel(@PathVariable("channelId") String id) {
        log.info("Processing request to getting information about channel with id=" + id + "...");
        Channel channel = youtubeService.getChannel(id);
        log.info("Request to getting information about channel with id=" + id + " was processed.");
        return channel;
    }

    @GetMapping("/videos/{channelId}")
    public List<Video> videos(@PathVariable("channelId") String id) {
        log.info("Processing request to getting information about videos of channel with id=" + id + "...");
        List<Video> videos = youtubeService.getVideos(id);
        log.info("Request to getting information about videos of channel with id=" + id + " was processed.");
        return videos;
    }
}
