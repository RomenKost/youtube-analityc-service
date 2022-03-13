package com.kostenko.youtube.analytic.dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
public class UserCredentialsDto {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;
}
