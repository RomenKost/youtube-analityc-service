package com.kostenko.youtubeanalyticservice.dto;

import lombok.experimental.UtilityClass;

import java.util.Date;

@UtilityClass
public class DTOs {
    public VideosDto getVideos() {
        VideosDto.Item.Id secondVideoId = new VideosDto.Item.Id("another video id");
        VideosDto.Item.Snippet secondVideoSnippet = new VideosDto.Item.Snippet(
                "another title", "another description", new Date(1_700_000_000_000L)
        );
        VideosDto.Item firstVideo = getVideo();
        VideosDto.Item secondVideo = new VideosDto.Item(secondVideoId, secondVideoSnippet);
        return new VideosDto(new VideosDto.Item[]{firstVideo, secondVideo});
    }

    public ChannelsDto getChannels() {
        ChannelsDto.Item.Snippet snippet = new ChannelsDto.Item.Snippet(
                "any title", "any description", "any country", new Date(1_600_000_000_000L)
        );
        ChannelsDto.Item item = new ChannelsDto.Item("any id", snippet);
        return new ChannelsDto(new ChannelsDto.Item[]{item});
    }

    public VideosDto.Item getVideo() {
        VideosDto.Item.Id videoId = new VideosDto.Item.Id("any video id");
        VideosDto.Item.Snippet videoSnippet = new VideosDto.Item.Snippet(
                "any title", "any description", new Date(1_600_000_000_000L)
        );
        return new VideosDto.Item(videoId, videoSnippet);
    }
}
