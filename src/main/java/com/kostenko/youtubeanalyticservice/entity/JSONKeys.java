package com.kostenko.youtubeanalyticservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JSONKeys {
    TITLE("title"),
    DESCRIPTION("description"),
    COUNTRY("country"),
    PUBLISHED_AT("publishedAt"),
    ITEMS("items"),
    SNIPPET("snippet"),
    ID("id"),
    VIDEO_ID("videoId");

    private final String key;
}
