package com.kostenko.youtube.analytic.service.exception;

import lombok.Getter;

@Getter
public class YoutubeChannelNotFoundException extends RuntimeException {
    public YoutubeChannelNotFoundException(String id) {
        super("Channel with id = " + id + " wasn't found.");
        this.id = id;
    }

    private final String id;
}
