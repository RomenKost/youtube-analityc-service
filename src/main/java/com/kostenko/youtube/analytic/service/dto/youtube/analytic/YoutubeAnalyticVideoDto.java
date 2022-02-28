package com.kostenko.youtube.analytic.service.dto.youtube.analytic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
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
