package com.kostenko.youtube.analytic.service.service.youtube.analytic.client.impl;

import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.YoutubeV3ApiChannelsDto;
import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.YoutubeV3ApiVideosDto;
import com.kostenko.youtube.analytic.service.exception.YoutubeServiceUnavailableException;
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
    public YoutubeV3ApiVideosDto getVideosDto(String id, String pageToken) {
        Map<String, String> urlParameters = getUrlParameters(id);
        urlParameters.put(UrlParameters.PAGE_TOKEN.getKey(), pageToken);

        log.info("Loading videosDto for channel with id = " + id + ", page token = " + pageToken + "...");
        try {
            YoutubeV3ApiVideosDto videosDto = restTemplate.getForObject(apiVideosUrl, YoutubeV3ApiVideosDto.class, urlParameters);
            log.info("VideosDto for channel with id = " + id + " was loaded.");
            return videosDto;
        } catch (RestClientException restClientException) {
            throw new YoutubeServiceUnavailableException(id, restClientException);
        }
    }

    @Override
    public YoutubeV3ApiChannelsDto getChannelsDto(String id) {
        Map<String, String> urlParameters = getUrlParameters(id);
        log.info("Loading channelsDto for channel with id = " + id + "...");
        try {
            YoutubeV3ApiChannelsDto channelsDto = restTemplate.getForObject(apiChannelUrl, YoutubeV3ApiChannelsDto.class, urlParameters);
            log.info("ChannelsDto for channel with id = " + id + " was loaded.");
            return channelsDto;
        } catch (RestClientException restClientException) {
            throw new YoutubeServiceUnavailableException(id, restClientException);
        }
    }

    private Map<String, String> getUrlParameters(String id) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(UrlParameters.KEY.getKey(), apiKey);
        parameters.put(UrlParameters.CHANNEL_ID.getKey(), id);
        return parameters;
    }
}
