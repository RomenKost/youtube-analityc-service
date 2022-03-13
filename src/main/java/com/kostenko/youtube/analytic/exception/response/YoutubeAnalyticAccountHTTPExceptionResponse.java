package com.kostenko.youtube.analytic.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Builder
@EqualsAndHashCode
public class YoutubeAnalyticAccountHTTPExceptionResponse {
    @JsonProperty("message")
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("jwt")
    private String reason;

    @JsonProperty("username")
    private String username;

    @JsonProperty("date")
    @EqualsAndHashCode.Exclude
    private Date date;

    @JsonProperty("exception")
    private String exceptionClassName;
}
