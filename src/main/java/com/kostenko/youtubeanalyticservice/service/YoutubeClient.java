package com.kostenko.youtubeanalyticservice.service;

import com.kostenko.youtubeanalyticservice.entity.Parameters;
import com.kostenko.youtubeanalyticservice.entity.Urls;
import com.kostenko.youtubeanalyticservice.model.YoutubeChannelDto;
import com.kostenko.youtubeanalyticservice.model.YoutubeVideoDto;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import org.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class YoutubeClient {
    private final RestTemplate restTemplate;
    private final String apiKey;

    public YoutubeClient(@Autowired RestTemplate restTemplate,
                         @Value("${key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public YoutubeChannelDto getYoutubeChannelDto(String id) {
        Map<String, String> parameters = new HashMap<>();
        String url = Urls.CHANNELS.getUrl();

        parameters.put(Parameters.KEY.getKey(), apiKey);
        parameters.put(Parameters.ID_FILTER.getKey(), id);
        parameters.put(Parameters.PART.getKey(), Parameters.PART.getValue());
        parameters.put(Parameters.MAX_RESULTS_CHANNELS.getKey(), Parameters.MAX_RESULTS_CHANNELS.getValue());

        JSONArray array = load(url, parameters);
        if (array.isEmpty()) {
            return null;
        }
        JSONObject jsonObject = array.getJSONObject(0);
        return getYoutubeChannelDto(jsonObject);
    }

    public List<YoutubeVideoDto> getYoutubeVideosDto(String id) {
        Map<String, String> parameters = new HashMap<>();
        String url = Urls.VIDEOS.getUrl();

        parameters.put(Parameters.KEY.getKey(), apiKey);
        parameters.put(Parameters.CHANNEL_ID.getKey(), id);
        parameters.put(Parameters.PART.getKey(), Parameters.PART.getValue());
        parameters.put(Parameters.CONTENT_TYPE.getKey(), Parameters.CONTENT_TYPE.getValue());
        parameters.put(Parameters.MAX_RESULTS_VIDEOS.getKey(), Parameters.MAX_RESULTS_VIDEOS.getValue());

        JSONArray array = load(url, parameters);
        return array.isEmpty() ?
                new ArrayList<>() :
                IntStream.range(0, array.length())
                        .mapToObj(array::getJSONObject)
                        .map(this::getYoutubeVideoDto)
                        .collect(Collectors.toList());
    }

    private YoutubeVideoDto getYoutubeVideoDto(JSONObject jsonObject) {
        JSONObject snippet = JSONParser.parseSnippet(jsonObject);

        String title = JSONParser.parseTitle(snippet);
        String videoId = JSONParser.parseVideoId(jsonObject);
        String description = JSONParser.parseDescription(snippet);
        Date publishedAt = JSONParser.parsePublishedAt(snippet);

        return YoutubeVideoDto.builder()
                .title(title)
                .videoId(videoId)
                .description(description)
                .publishedAt(publishedAt)
                .build();
    }

    private YoutubeChannelDto getYoutubeChannelDto(JSONObject jsonObject) {
        JSONObject snippet = JSONParser.parseSnippet(jsonObject);

        String title = JSONParser.parseTitle(snippet);
        String country = JSONParser.parseCountry(snippet);
        String description = JSONParser.parseDescription(snippet);
        Date publishedAt = JSONParser.parsePublishedAt(snippet);

        return YoutubeChannelDto.builder()
                .title(title)
                .country(country)
                .description(description)
                .publishedAt(publishedAt)
                .build();
    }

    private JSONArray load(String url, Map<String, String> parameters) {
        String json = restTemplate.getForObject(url, String.class, parameters);
        return JSONParser.parseItems(json);
    }
}
