package com.kostenko.youtube.analytic.service.dto.youtube.analytic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
@EqualsAndHashCode
public class YoutubeAnalyticVideoDto {
    @JsonProperty("videoId")
    private String videoId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("publishedAt")
    private Date publishedAt;
}
