package com.kostenko.youtube.analytic.service.exception;

import lombok.Getter;

@Getter
public class YoutubeVideosNotFoundException extends RuntimeException {
    private final String id;

    public YoutubeVideosNotFoundException(String id) {
        super("Videos of channel with id = " + id + " weren't found.");
        this.id = id;
    }
}
