package com.kostenko.youtube.analytic.exception.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Builder
@EqualsAndHashCode
public class YoutubeAnalyticHTTPExceptionResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("channel_id")
    private String channelId;

    @JsonProperty("date")
    @EqualsAndHashCode.Exclude
    private Date date;

    @JsonProperty("exception")
    private String exceptionClassName;
}
