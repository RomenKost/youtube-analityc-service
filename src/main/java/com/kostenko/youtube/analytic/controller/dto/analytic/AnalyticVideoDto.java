package com.kostenko.youtube.analytic.controller.dto.analytic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
public class AnalyticVideoDto {
    @JsonProperty("video_id")
    private String videoId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("published_at")
    private Date publishedAt;
}
