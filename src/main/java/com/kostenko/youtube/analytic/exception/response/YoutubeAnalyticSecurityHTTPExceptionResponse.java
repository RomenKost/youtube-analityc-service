package com.kostenko.youtube.analytic.exception.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Builder
@ToString
@EqualsAndHashCode
public class YoutubeAnalyticSecurityHTTPExceptionResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("username")
    private String username;

    @JsonProperty("date")
    @EqualsAndHashCode.Exclude
    private Date date;

    @JsonProperty("exception")
    private String exceptionClassName;
}
