package com.kostenko.youtubeanalyticservice.model;

import lombok.experimental.UtilityClass;

import java.util.Date;
import java.util.List;

@UtilityClass
public class Models {
    public List<Video> getVideos() {
        Video firstVideo = getVideo();
        Video secondVideo = new Video(
                "another video id", "another title", "another description", new Date(1_700_000_000_000L)
        );
        return List.of(firstVideo, secondVideo);
    }

    public Channel getChannel() {
        return new Channel(
                "any id", "any title", "any description", "any country", new Date(1_600_000_000_000L)
        );
    }

    public Video getVideo() {
        return new Video(
                "any video id", "any title", "any description", new Date(1_600_000_000_000L)
        );
    }
}

