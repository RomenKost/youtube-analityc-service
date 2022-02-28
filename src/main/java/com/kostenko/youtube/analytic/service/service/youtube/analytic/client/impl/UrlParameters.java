package com.kostenko.youtube.analytic.service.service.youtube.analytic.client.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UrlParameters {
    KEY("key"),
    CHANNEL_ID("channelId"),
    PAGE_TOKEN("pageToken");

    private final String key;
}
