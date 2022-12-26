package com.kostenko.youtube.analytic.service.youtube.api.dto.video;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class YoutubeV3VideoDto {
    @JsonProperty("id")
    private YoutubeV3VideoIdDto id;

    @JsonProperty("snippet")
    private YoutubeV3VideoSnippetDto snippet;
}
