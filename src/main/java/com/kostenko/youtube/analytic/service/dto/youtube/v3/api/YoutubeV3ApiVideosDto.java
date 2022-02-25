package com.kostenko.youtube.analytic.service.dto.youtube.v3.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class YoutubeV3ApiVideosDto {
    @JsonProperty("items")
    private Item[] items;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        @JsonProperty("id")
        private Id id;

        @JsonProperty("snippet")
        private Snippet snippet;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Id {
            @JsonProperty("videoId")
            private String videoId;
        }

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Snippet {
            @JsonProperty("title")
            private String title;

            @JsonProperty("description")
            private String description;

            @JsonProperty("publishedAt")
            private Date publishedAt;
        }
    }
}
