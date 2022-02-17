package com.kostenko.youtubeanalyticservice.service.analyticclient.impl.youtubeanalyticclient;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UrlParameters {
    KEY("key"),
    PART("part", "snippet"),
    ID_FILTER("id"),
    CHANNEL_ID("channelId"),
    CONTENT_TYPE("type", "video"),
    MAX_RESULTS_VIDEOS("maxResults", "50"),
    MAX_RESULTS_CHANNELS("maxResults", "1");

    UrlParameters(String key) {
        this.key = key;
    }

    private final String key;
    private String value;
}
