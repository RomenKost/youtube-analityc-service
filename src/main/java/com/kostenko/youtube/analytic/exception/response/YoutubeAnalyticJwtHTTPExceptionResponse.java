package com.kostenko.youtube.analytic.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;

@Builder
@Jacksonized
@EqualsAndHashCode
public class YoutubeAnalyticJwtHTTPExceptionResponse {
    @JsonProperty("message")
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("jwt")
    private String jwt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("expiration")
    private Date expiration;

    @JsonProperty("date")
    @EqualsAndHashCode.Exclude
    private Date date;

    @JsonProperty("exception")
    private String exceptionClassName;
}
