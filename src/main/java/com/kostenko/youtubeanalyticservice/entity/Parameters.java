package com.kostenko.youtubeanalyticservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Parameters {
    KEY("key", null),
    PART("part", "snippet"),
    ID_FILTER("id", null),
    CHANNEL_ID("channelId", null),
    CONTENT_TYPE("type", "video"),
    MAX_RESULTS_VIDEOS("maxResults", "50"),
    MAX_RESULTS_CHANNELS("maxResults", "1");

    private final String key;
    private final String value;
}
