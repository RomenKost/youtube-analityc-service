package com.kostenko.youtube.analytic.service.dto.youtube.v3.api;

import lombok.experimental.UtilityClass;

import java.util.Date;

@UtilityClass
public class V3ApiVideosDto {
    public YoutubeV3ApiVideosDto getVideos() {
        YoutubeV3ApiVideosDto.Item.Id secondVideoId = new YoutubeV3ApiVideosDto.Item.Id("another video id");
        YoutubeV3ApiVideosDto.Item.Snippet secondVideoSnippet = new YoutubeV3ApiVideosDto.Item.Snippet(
                "another title", "another description", new Date(1_700_000_000_000L)
        );
        YoutubeV3ApiVideosDto.Item firstVideo = getVideo();
        YoutubeV3ApiVideosDto.Item secondVideo = new YoutubeV3ApiVideosDto.Item(secondVideoId, secondVideoSnippet);
        return new YoutubeV3ApiVideosDto(new YoutubeV3ApiVideosDto.Item[]{firstVideo, secondVideo});
    }

    public YoutubeV3ApiChannelsDto getChannels() {
        YoutubeV3ApiChannelsDto.Item.Snippet snippet = new YoutubeV3ApiChannelsDto.Item.Snippet(
                "any title", "any description", "any country", new Date(1_600_000_000_000L)
        );
        YoutubeV3ApiChannelsDto.Item item = new YoutubeV3ApiChannelsDto.Item("any id", snippet);
        return new YoutubeV3ApiChannelsDto(new YoutubeV3ApiChannelsDto.Item[]{item});
    }

    public YoutubeV3ApiVideosDto.Item getVideo() {
        YoutubeV3ApiVideosDto.Item.Id videoId = new YoutubeV3ApiVideosDto.Item.Id("any video id");
        YoutubeV3ApiVideosDto.Item.Snippet videoSnippet = new YoutubeV3ApiVideosDto.Item.Snippet(
                "any title", "any description", new Date(1_600_000_000_000L)
        );
        return new YoutubeV3ApiVideosDto.Item(videoId, videoSnippet);
    }
}
