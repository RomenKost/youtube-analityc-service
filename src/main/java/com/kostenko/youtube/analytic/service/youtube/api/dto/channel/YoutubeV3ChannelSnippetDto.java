package com.kostenko.youtube.analytic.service.youtube.api.dto.channel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class YoutubeV3ChannelSnippetDto {
    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("country")
    private String country;

    @JsonProperty("publishedAt")
    private Date publishedAt;
}
