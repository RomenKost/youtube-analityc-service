package com.kostenko.youtube.analytic.service.dto.youtube.analytic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
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
