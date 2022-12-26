package com.kostenko.youtube.analytic.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class JwtExceptionResponse extends AbstractExceptionResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("jwt")
    private String jwt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("expiration")
    private Date expiration;
}
