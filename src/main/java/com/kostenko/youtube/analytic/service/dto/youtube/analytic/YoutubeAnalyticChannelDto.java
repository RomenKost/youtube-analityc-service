package com.kostenko.youtube.analytic.service.dto.youtube.analytic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
@EqualsAndHashCode
public class YoutubeAnalyticChannelDto {
    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("country")
    private String country;

    @JsonProperty("publishedAt")
    private Date publishedAt;
}
