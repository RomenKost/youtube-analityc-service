package com.kostenko.youtube.analytic.service.controller;

import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticChannelDto;
import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticVideoDto;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeChannelMapper;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeVideoMapper;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;
import com.kostenko.youtube.analytic.service.service.youtube.analytic.service.AnalyticService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/youtube/analytic/v1")
public class YoutubeAnalyticRestController {
    private final AnalyticService youtubeService;

    private final YoutubeChannelMapper channelMapper;
    private final YoutubeVideoMapper videoMapper;

    public YoutubeAnalyticRestController(AnalyticService youtubeService,
                                         YoutubeChannelMapper channelMapper,
                                         YoutubeVideoMapper videoMapper) {
        this.youtubeService = youtubeService;
        this.channelMapper = channelMapper;
        this.videoMapper = videoMapper;
    }

    @GetMapping("/channels/{channelId}")
    public YoutubeAnalyticChannelDto getChannels(@PathVariable("channelId") String id) {
        Channel channel = youtubeService.getChannel(id);
        return channelMapper.channelToYoutubeAnalyticChannelDto(channel);
    }

    @GetMapping("channels/{channelId}/videos")
    public List<YoutubeAnalyticVideoDto> getVideos(@PathVariable("channelId") String id) {
        List<Video> videos = youtubeService.getVideos(id);
        return videoMapper.videosToYoutubeAnalyticVideoDTOs(videos);
    }
}
