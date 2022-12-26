package com.kostenko.youtube.analytic.exception.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
public abstract class AbstractExceptionResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("date")
    @EqualsAndHashCode.Exclude
    private Date date;

    @JsonProperty("exception")
    private String exceptionClassName;
}
