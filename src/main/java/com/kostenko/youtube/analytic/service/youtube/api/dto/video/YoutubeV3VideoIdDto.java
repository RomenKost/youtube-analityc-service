package com.kostenko.youtube.analytic.service.youtube.api.dto.video;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YoutubeV3VideoIdDto {
    @JsonProperty("videoId")
    private String videoId;
}
