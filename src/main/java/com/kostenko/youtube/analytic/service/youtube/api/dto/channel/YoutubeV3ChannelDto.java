package com.kostenko.youtube.analytic.service.youtube.api.dto.channel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YoutubeV3ChannelDto {
    @JsonProperty("id")
    private String id;

    @JsonProperty(value = "snippet")
    private YoutubeV3ChannelSnippetDto snippet;
}
