package com.kostenko.youtube.analytic.service.service.youtube.analytic.service.impl;

import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.YoutubeV3ApiChannelsDto;
import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.YoutubeV3ApiVideosDto;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeVideoMapper;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;
import com.kostenko.youtube.analytic.service.service.youtube.analytic.client.AnalyticClient;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeChannelMapper;
import com.kostenko.youtube.analytic.service.service.youtube.analytic.service.AnalyticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class YoutubeAnalyticService implements AnalyticService {
    private final AnalyticClient client;

    private final YoutubeVideoMapper videoMapper;
    private final YoutubeChannelMapper channelMapper;

    public YoutubeAnalyticService(AnalyticClient client,
                                  YoutubeVideoMapper videoMapper,
                                  YoutubeChannelMapper channelMapper) {
        this.client = client;
        this.videoMapper = videoMapper;
        this.channelMapper = channelMapper;
    }

    @Override
    public Channel getChannel(String id) {
        log.info("Processing request to getting information about channel with id=" + id + "...");
        YoutubeV3ApiChannelsDto channelsDto = client.getChannelsDto(id);
        log.info("Request to getting information about channel with id=" + id + " was processed.");
        return channelMapper.youtubeV3ApiChannelsDtoToChannel(channelsDto);
    }

    @Override
    public List<Video> getVideos(String id) {
        log.info("Processing request to getting information about videos of channel with id=" + id + "...");

        String pageToken = "";
        List<Video> videos = new ArrayList<>();

        while (pageToken != null) {
            YoutubeV3ApiVideosDto videosDto = client.getVideosDto(id, pageToken);
            List<Video> videosInPage = videoMapper.videoDTOsToVideos(videosDto);
            videos.addAll(videosInPage);

            pageToken = videosDto.getNextPageToken();
        }

        log.info("Request to getting information about videos of channel with id=" + id + " was processed.");
        return videos;
    }
}
