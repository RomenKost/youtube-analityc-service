package com.kostenko.youtubeanalyticservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChannelsDto {
    @JsonProperty("items")
    private Item[] items;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        @JsonProperty("id")
        private String id;

        @JsonProperty("snippet")
        private Snippet snippet;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Snippet {
            @JsonProperty("title")
            private String title;

            @JsonProperty("description")
            private String description;

            @JsonProperty("country")
            private String country;

            @JsonProperty("publishedAt")
            private Date publishedAt;
        }
    }
}
