package com.kostenko.youtube.analytic.service.exception.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@AllArgsConstructor
public class YoutubeAnalyticHTTPResponse {
    @JsonProperty("message")
    private String message;
}
