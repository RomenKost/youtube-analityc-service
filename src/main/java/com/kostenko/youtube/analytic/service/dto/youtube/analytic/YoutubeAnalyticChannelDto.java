package com.kostenko.youtube.analytic.service.dto.youtube.analytic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Builder
@EqualsAndHashCode
public class YoutubeAnalyticChannelDto {
    @NotBlank
    @Size(min = 24, max = 24)
    @JsonProperty("channel_id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @Size(min = 2, max = 2)
    @JsonProperty("country")
    private String country;

    @JsonProperty("published_at")
    private Date publishedAt;
}
