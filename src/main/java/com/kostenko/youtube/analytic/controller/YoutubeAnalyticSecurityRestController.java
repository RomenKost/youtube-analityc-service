package com.kostenko.youtube.analytic.controller;

import com.kostenko.youtube.analytic.dto.security.YoutubeAnalyticJwtDto;
import com.kostenko.youtube.analytic.dto.security.UserCredentialsDto;
import com.kostenko.youtube.analytic.exception.response.YoutubeAnalyticAccountHTTPExceptionResponse;
import com.kostenko.youtube.analytic.exception.response.YoutubeAnalyticSecurityHTTPExceptionResponse;
import com.kostenko.youtube.analytic.mapper.security.token.JwtMapper;
import com.kostenko.youtube.analytic.model.security.YoutubeAnalyticJwt;
import com.kostenko.youtube.analytic.service.security.jwt.processor.YoutubeAnalyticJwtProcessor;
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
public class YoutubeAnalyticSecurityRestController {
    private final JwtMapper jwtMapper;
    private final YoutubeAnalyticJwtProcessor jwtProcessor;

    @Operation(summary = "Get an jwt.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Jwt was loaded successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = YoutubeAnalyticJwtDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Credentials is incorrect.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = YoutubeAnalyticSecurityHTTPExceptionResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User account is unavailable.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = YoutubeAnalyticAccountHTTPExceptionResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User wasn't found.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = YoutubeAnalyticSecurityHTTPExceptionResponse.class)
                    )
            )
    })
    @PostMapping("/login")
    public YoutubeAnalyticJwtDto getJwt(@RequestBody UserCredentialsDto credentialsDto) {
        YoutubeAnalyticJwt jwtModel = jwtProcessor.getJwt(credentialsDto.getUsername(), credentialsDto.getPassword());
        return jwtMapper.jwtToJwtDto(jwtModel);
    }
}
