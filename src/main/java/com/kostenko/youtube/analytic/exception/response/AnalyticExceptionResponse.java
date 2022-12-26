package com.kostenko.youtube.analytic.exception.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AnalyticExceptionResponse extends AbstractExceptionResponse {
    @JsonProperty("channel_id")
    private String channelId;
}
