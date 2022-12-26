package com.kostenko.youtube.analytic.controller;

import com.kostenko.youtube.analytic.controller.dto.security.JwtDto;
import com.kostenko.youtube.analytic.controller.dto.security.UserCredentialsDto;
import com.kostenko.youtube.analytic.exception.response.AuthorizationExceptionResponse;
import com.kostenko.youtube.analytic.service.jwt.mapper.JwtMapper;
import com.kostenko.youtube.analytic.model.security.jwt.Jwt;
import com.kostenko.youtube.analytic.service.jwt.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/youtube/analytic/v1")
public class SecurityController {
    private final JwtMapper jwtMapper;
    private final JwtService jwtProcessor;

    @Operation(summary = "Get an jwt.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Jwt was loaded successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtDto.class))),
            @ApiResponse(responseCode = "401", description = "Credentials is incorrect.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorizationExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "User account is unavailable.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorizationExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "User wasn't found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorizationExceptionResponse.class)))
    })
    @PostMapping("/login")
    public JwtDto getJwt(@RequestBody UserCredentialsDto credentialsDto) {
        Jwt jwtModel = jwtProcessor.getJwt(credentialsDto.getUsername(), credentialsDto.getPassword());
        return jwtMapper.jwtToJwtDto(jwtModel);
    }
}
