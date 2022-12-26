package com.kostenko.youtube.analytic.service.youtube.api.dto.video;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class YoutubeV3VideoSnippetDto {
    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("publishedAt")
    private Date publishedAt;
}
