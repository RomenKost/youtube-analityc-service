package com.kostenko.youtube.analytic.controller.dto.analytic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
public class AnalyticChannelDto {
    @JsonProperty("channel_id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("country")
    private String country;

    @JsonProperty("published_at")
    private Date publishedAt;
}
