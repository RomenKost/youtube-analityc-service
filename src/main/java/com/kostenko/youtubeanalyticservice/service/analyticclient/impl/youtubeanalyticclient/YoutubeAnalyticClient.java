package com.kostenko.youtubeanalyticservice.service.analyticclient.impl.youtubeanalyticclient;

import com.kostenko.youtubeanalyticservice.dto.ChannelsDto;
import com.kostenko.youtubeanalyticservice.dto.VideosDto;
import com.kostenko.youtubeanalyticservice.service.analyticclient.AnalyticClient;
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

    private final String apiKey;
    private final String apiChannelUrl;
    private final String apiVideosUrl;

    public YoutubeAnalyticClient(RestTemplate restTemplate,
                                 @Value("${youtube.v3.api.key}") String apiKey,
                                 @Value("${youtube.v3.api.urls.channel}") String apiChannelUrl,
                                 @Value("${youtube.v3.api.urls.videos}") String apiVideosUrl) {
        this.restTemplate = restTemplate;

        this.apiKey = apiKey;
        this.apiChannelUrl = apiChannelUrl;
        this.apiVideosUrl = apiVideosUrl;
    }

    @Override
    public VideosDto getVideosDto(String id) {
        Map<String, String> parameters = new HashMap<>();

        parameters.put(UrlParameters.KEY.getKey(), apiKey);
        parameters.put(UrlParameters.CHANNEL_ID.getKey(), id);
        parameters.put(UrlParameters.PART.getKey(), UrlParameters.PART.getValue());
        parameters.put(UrlParameters.CONTENT_TYPE.getKey(), UrlParameters.CONTENT_TYPE.getValue());
        parameters.put(UrlParameters.MAX_RESULTS_VIDEOS.getKey(), UrlParameters.MAX_RESULTS_VIDEOS.getValue());

        log.info("Loading videosDto for channel with id = " + id + "...");
        try {
            VideosDto videosDto = restTemplate.getForObject(apiVideosUrl, VideosDto.class, parameters);
            log.info("VideosDto for channel with id = " + id + " was loaded.");
            return videosDto;
        } catch (RestClientException restClientException) {
            log.error("Youtube api server is unavailable.", restClientException);
            return null;
        }
    }

    @Override
    public ChannelsDto getChannelsDto(String id) {
        Map<String, String> parameters = new HashMap<>();

        parameters.put(UrlParameters.KEY.getKey(), apiKey);
        parameters.put(UrlParameters.ID_FILTER.getKey(), id);
        parameters.put(UrlParameters.PART.getKey(), UrlParameters.PART.getValue());
        parameters.put(UrlParameters.MAX_RESULTS_CHANNELS.getKey(), UrlParameters.MAX_RESULTS_CHANNELS.getValue());

        log.info("Loading channelsDto for channel with id = " + id + "...");
        try {
            ChannelsDto channelsDto = restTemplate.getForObject(apiChannelUrl, ChannelsDto.class, parameters);
            log.info("ChannelsDto for channel with id = " + id + " was loaded.");
            return channelsDto;
        } catch (RestClientException restClientException) {
            log.error("Youtube api server is unavailable.", restClientException);
            return null;
        }
    }
}
