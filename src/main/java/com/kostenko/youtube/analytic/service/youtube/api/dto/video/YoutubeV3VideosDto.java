package com.kostenko.youtube.analytic.service.youtube.api.dto.video;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
public class YoutubeV3VideosDto {
    @JsonProperty("items")
    private List<YoutubeV3VideoDto> videoDtos;

    @JsonProperty("nextPageToken")
    private String nextPageToken;
}
