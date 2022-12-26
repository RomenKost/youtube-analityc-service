package com.kostenko.youtube.analytic.service.youtube.api.dto.channel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
public class YoutubeV3ChannelsDto {
    @JsonProperty(value = "items")
    private List<YoutubeV3ChannelDto> channels;
}
