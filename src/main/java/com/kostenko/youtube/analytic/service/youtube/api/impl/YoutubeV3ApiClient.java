package com.kostenko.youtube.analytic.service.youtube.api.impl;

import com.kostenko.youtube.analytic.service.youtube.api.dto.channel.YoutubeV3ChannelsDto;
import com.kostenko.youtube.analytic.service.youtube.api.dto.video.YoutubeV3VideoDto;
import com.kostenko.youtube.analytic.service.youtube.api.dto.video.YoutubeV3VideosDto;
import com.kostenko.youtube.analytic.service.channel.mapper.channel.YoutubeChannelMapper;
import com.kostenko.youtube.analytic.service.channel.mapper.video.YoutubeVideoMapper;
import com.kostenko.youtube.analytic.model.youtube.Channel;
import com.kostenko.youtube.analytic.model.youtube.Video;
import com.kostenko.youtube.analytic.service.youtube.api.YoutubeApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.kostenko.youtube.analytic.exception.message.ErrorMessages.*;
import static com.kostenko.youtube.analytic.util.UrlParameters.*;

@Slf4j
@Service
public class YoutubeV3ApiClient implements YoutubeApiClient {
    private static final String INITIAL_PAGE_TOKEN = "";

    private final RestTemplate restTemplate;
    private final YoutubeChannelMapper channelMapper;
    private final YoutubeVideoMapper videoMapper;

    private final String apiKey;
    private final String apiChannelUrl;
    private final String apiVideosUrl;

    public YoutubeV3ApiClient(RestTemplate restTemplate,
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
    public List<Video> getVideos(String id) {
        int page = 0;
        String pageToken = INITIAL_PAGE_TOKEN;
        List<Video> videos = new ArrayList<>();
        do {
            log.info("Loading videosDto for channel with id = {} for page {}.", id, page);
            try {
                YoutubeV3VideosDto videosDto = restTemplate.getForObject(apiVideosUrl, YoutubeV3VideosDto.class, getUrlParameters(id, pageToken));
                if(videosDto == null) {
                    break;
                }
                List<YoutubeV3VideoDto> v3VideoDtos = videosDto.getVideoDtos();

                if (v3VideoDtos == null || v3VideoDtos.isEmpty()) {
                    log.warn("Videos for channel with id = {} for page {} wasn't found.", id, page++);
                } else {
                    videos.addAll(videoMapper.youtubeV3VideoDtosToVideos(v3VideoDtos));
                    log.info("VideosDto for channel with id = {} for page {} was loaded.", id, page++);
                }

                pageToken = videosDto.getNextPageToken();
            } catch (RestClientException restClientException) {
                log.error(ANALYTIC_SERVICE_UNAVAILABLE, restClientException);
                return videos;
            }
        } while (pageToken != null);
        return videos;
    }

    @Override
    public Optional<Channel> getChannel(String id) {
        log.info("Loading channelsDto for channel with id = {}", id);

        try {
            YoutubeV3ChannelsDto channelsDto = restTemplate.getForObject(apiChannelUrl, YoutubeV3ChannelsDto.class, getUrlParameters(id));
            List<Channel> channels = channelsDto == null
                    ? new ArrayList<>()
                    : channelMapper.youtubeV3ChannelsDtoToChannels(channelsDto.getChannels());

            if (channels.isEmpty()) {
                log.warn("Channel with id = {} wasn't found.", id);
            } else {
                log.info("ChannelsDto for channel with id = {} was loaded.", id);
            }

            return channels.stream().findFirst();
        } catch (RestClientException restClientException) {
            log.error(ANALYTIC_SERVICE_UNAVAILABLE, restClientException);
            return Optional.empty();
        }
    }

    private Map<String, String> getUrlParameters(String id) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(YOUTUBE_API_KEY, apiKey);
        parameters.put(CHANNEL_ID, id);
        return parameters;
    }

    private Map<String, String> getUrlParameters(String id, String pageToken) {
        Map<String, String> parameters = getUrlParameters(id);
        parameters.put(PAGE_TOKEN, pageToken);
        return parameters;
    }
}
