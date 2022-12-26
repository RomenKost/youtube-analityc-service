package com.kostenko.youtube.analytic.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthorizationExceptionResponse extends AbstractExceptionResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("reason")
    private String reason;

    @JsonProperty("username")
    private String username;
}
