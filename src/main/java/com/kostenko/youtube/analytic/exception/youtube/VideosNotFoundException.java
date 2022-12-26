package com.kostenko.youtube.analytic.exception.youtube;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class VideosNotFoundException extends RuntimeException {
    private final String id;

    public VideosNotFoundException(String id) {
        super("Videos of channel with id = " + id + " weren't found.");
        this.id = id;
    }
}
