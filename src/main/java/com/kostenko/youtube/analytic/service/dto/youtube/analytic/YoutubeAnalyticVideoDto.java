package com.kostenko.youtube.analytic.service.dto.youtube.analytic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class YoutubeAnalyticVideoDto {
    @NotBlank
    @Size(min = 11, max = 11)
    @JsonProperty("video_id")
    private String videoId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("published_at")
    private Date publishedAt;
}
