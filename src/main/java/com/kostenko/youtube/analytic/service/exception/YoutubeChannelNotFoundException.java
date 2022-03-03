package com.kostenko.youtube.analytic.service.exception;

import lombok.Getter;

@Getter
public class YoutubeChannelNotFoundException extends RuntimeException {
    private final String id;

    public YoutubeChannelNotFoundException(String id) {
        super("Channel with id = " + id + " wasn't found.");
        this.id = id;
    }
}
