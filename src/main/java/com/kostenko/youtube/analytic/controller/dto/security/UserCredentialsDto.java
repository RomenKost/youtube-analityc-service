package com.kostenko.youtube.analytic.controller.dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
public class UserCredentialsDto {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;
}
