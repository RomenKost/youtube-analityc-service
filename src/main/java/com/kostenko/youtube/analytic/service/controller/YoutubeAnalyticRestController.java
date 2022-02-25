package com.kostenko.youtube.analytic.service.controller;

import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticChannelDto;
import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticVideoDto;
import com.kostenko.youtube.analytic.service.service.youtube.analytic.service.AnalyticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/youtube/analytic/v1")
public class YoutubeAnalyticRestController {
    private final AnalyticService youtubeService;

    @Autowired
    public YoutubeAnalyticRestController(AnalyticService youtubeService) {
        this.youtubeService = youtubeService;
    }

    @GetMapping("/channels/{channelId}")
    public YoutubeAnalyticChannelDto getChannels(@PathVariable("channelId") String id) {
        log.info("Processing request to getting information about channel with id=" + id + "...");
        YoutubeAnalyticChannelDto youtubeAnalyticChannelDto = youtubeService.getChannel(id);
        log.info("Request to getting information about channel with id=" + id + " was processed.");
        return youtubeAnalyticChannelDto;
    }

    @GetMapping("/videos/{channelId}")
    public List<YoutubeAnalyticVideoDto> getVideos(@PathVariable("channelId") String id) {
        log.info("Processing request to getting information about videos of channel with id=" + id + "...");
        List<YoutubeAnalyticVideoDto> youtubeAnalyticVideoDtos = youtubeService.getVideos(id);
        log.info("Request to getting information about videos of channel with id=" + id + " was processed.");
        return youtubeAnalyticVideoDtos;
    }
}
