package com.kostenko.youtube.analytic.service.controller;

import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticChannelDto;
import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticVideoDto;
import com.kostenko.youtube.analytic.service.exception.response.YoutubeAnalyticHTTPExceptionResponse;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeChannelMapper;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeVideoMapper;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;
import com.kostenko.youtube.analytic.service.service.database.manager.service.DatabaseManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/youtube/analytic/v1")
public class YoutubeAnalyticRestController {
    private final DatabaseManagerService databaseManagerService;

    private final YoutubeChannelMapper channelMapper;
    private final YoutubeVideoMapper videoMapper;

    public YoutubeAnalyticRestController(DatabaseManagerService databaseManagerService,
                                         YoutubeChannelMapper channelMapper,
                                         YoutubeVideoMapper videoMapper) {
        this.databaseManagerService = databaseManagerService;
        this.channelMapper = channelMapper;
        this.videoMapper = videoMapper;
    }


    @Operation(summary = "Get an youtube channel information by id.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Channel was loaded successfully.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = YoutubeAnalyticChannelDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Channel with passed id wasn't found.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = YoutubeAnalyticHTTPExceptionResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Youtube analytic service is unavailable.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = YoutubeAnalyticHTTPExceptionResponse.class)
                    )
            )
    })
    @GetMapping("/channels/{channelId}")
    public YoutubeAnalyticChannelDto getChannels(
            @Parameter(description = "Youtube channel id.")
            @PathVariable("channelId") String id
    ) {
        Channel channel = databaseManagerService.getChannel(id);
        return channelMapper.channelToYoutubeAnalyticChannelDto(channel);
    }


    @Operation(summary = "Get youtube videos information by channel id.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Videos were loaded successfully.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = YoutubeAnalyticVideoDto.class)
                                    )
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Channel with passed id wasn't found.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = YoutubeAnalyticHTTPExceptionResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Youtube analytic service is unavailable.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = YoutubeAnalyticHTTPExceptionResponse.class)
                    )
            )
    })
    @GetMapping("channels/{channelId}/videos")
    public List<YoutubeAnalyticVideoDto> getVideos(
            @Parameter(description = "Youtube channel id.")
            @PathVariable("channelId") String id
    ) {
        List<Video> videos = databaseManagerService.getVideos(id);
        return videoMapper.videosToYoutubeAnalyticVideoDTOs(videos);
    }
}
