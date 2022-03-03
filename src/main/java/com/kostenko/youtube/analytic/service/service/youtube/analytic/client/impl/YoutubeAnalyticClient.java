package com.kostenko.youtube.analytic.service.service.youtube.analytic.client.impl;

import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.YoutubeV3ApiChannelsDto;
import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.YoutubeV3ApiVideosDto;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeChannelMapper;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeVideoMapper;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;
import com.kostenko.youtube.analytic.service.service.youtube.analytic.client.AnalyticClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
public class YoutubeAnalyticClient implements AnalyticClient {
    private final RestTemplate restTemplate;
    private final YoutubeChannelMapper channelMapper;
    private final YoutubeVideoMapper videoMapper;

    private final String apiKey;
    private final String apiChannelUrl;
    private final String apiVideosUrl;

    public YoutubeAnalyticClient(RestTemplate restTemplate,
                                 YoutubeChannelMapper channelMapper,
                                 YoutubeVideoMapper videoMapper,
                                 @Value("${youtube.v3.api.key}") String apiKey,
                                 @Value("${youtube.v3.api.urls.channel}") String apiChannelUrl,
                                 @Value("${youtube.v3.api.urls.videos}") String apiVideosUrl) {
        this.restTemplate = restTemplate;
        this.channelMapper = channelMapper;
        this.videoMapper = videoMapper;

        this.apiKey = apiKey;
        this.apiChannelUrl = apiChannelUrl;
        this.apiVideosUrl = apiVideosUrl;
    }

    @Override
    public List<Video> getVideos(String id, String pageToken) {
        Map<String, String> urlParameters = getUrlParameters(id);
        urlParameters.put(UrlParameters.PAGE_TOKEN.getKey(), pageToken);

        log.info("Loading videosDto for channel with id = " + id + ", page token = " + pageToken + "...");

        List<Video> videos = new ArrayList<>();
        try {
            YoutubeV3ApiVideosDto videosDto = restTemplate.getForObject(apiVideosUrl, YoutubeV3ApiVideosDto.class, urlParameters);
            videos.addAll(videoMapper.videoDTOsToVideos(videosDto));
            pageToken = videosDto == null ? null : videosDto.getNextPageToken();
        } catch (RestClientException restClientException) {
            log.error("Youtube analytic service is unavailable.", restClientException);
            return List.of();
        }

        if (videos.isEmpty()) {
            log.warn("Videos for channel with id = " + id + " wasn't found.");
        } else {
            log.info("VideosDto for channel with id = " + id + " was loaded.");
        }

        if (pageToken != null) {
            videos.addAll(getVideos(id, pageToken));
        }
        return videos;
    }

    @Override
    public Optional<Channel> getChannel(String id) {
        Map<String, String> urlParameters = getUrlParameters(id);
        log.info("Loading channelsDto for channel with id = " + id + "...");

        try {
            YoutubeV3ApiChannelsDto channelsDto = restTemplate.getForObject(apiChannelUrl, YoutubeV3ApiChannelsDto.class, urlParameters);
            Channel channel = channelMapper.youtubeV3ApiChannelsDtoToChannel(channelsDto);

            if (channel == null) {
                log.warn("Channel with id = " + id + " wasn't found.");
            } else {
                log.info("ChannelsDto for channel with id = " + id + " was loaded.");
            }

            return Optional.ofNullable(channel);
        } catch (RestClientException restClientException) {
            log.error("Youtube analytic service is unavailable.", restClientException);
            return Optional.empty();
        }
    }

    private Map<String, String> getUrlParameters(String id) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(UrlParameters.KEY.getKey(), apiKey);
        parameters.put(UrlParameters.CHANNEL_ID.getKey(), id);
        return parameters;
    }
}
