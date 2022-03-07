package com.kostenko.youtube.analytic.model;

import lombok.experimental.UtilityClass;

import java.util.Date;
import java.util.List;

@UtilityClass
public class Models {
    public List<Video> getVideos() {
        Video firstVideo = getVideo();
        Video secondVideo = new Video(
                "another id", "another title", "another description", new Date(1_700_000_000_000L)
        );
        return List.of(firstVideo, secondVideo);
    }

    public Channel getChannel() {
        return new Channel(
                "any id", "any title", "any description", "ac", new Date(1_600_000_000_000L)
        );
    }

    public Video getVideo() {
        return new Video(
                "any id", "any title", "any description", new Date(1_600_000_000_000L)
        );
    }
}
