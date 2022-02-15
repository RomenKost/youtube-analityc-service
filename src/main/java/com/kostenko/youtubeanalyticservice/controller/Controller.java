package com.kostenko.youtubeanalyticservice.controller;

import com.kostenko.youtubeanalyticservice.model.YoutubeChannelDto;
import com.kostenko.youtubeanalyticservice.model.YoutubeVideoDto;
import com.kostenko.youtubeanalyticservice.service.YoutubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PropertySource("classpath:settings.properties")
public class Controller {
    private final YoutubeService youtubeService;

    @Autowired
    public Controller(YoutubeService youtubeService) {
        this.youtubeService = youtubeService;
    }

    @GetMapping("/statistic")
    public YoutubeChannelDto statistic(@RequestParam String id) {
        return youtubeService.getChannel(id);
    }

    @GetMapping("/videos")
    public List<YoutubeVideoDto> videos(@RequestParam String id) {
        return youtubeService.getVideos(id);
    }
}
