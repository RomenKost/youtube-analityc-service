package com.kostenko.youtube.analytic.controller;

import com.kostenko.youtube.analytic.controller.dto.analytic.AnalyticChannelDto;
import com.kostenko.youtube.analytic.controller.dto.analytic.AnalyticVideoDto;
import com.kostenko.youtube.analytic.exception.response.AnalyticExceptionResponse;
import com.kostenko.youtube.analytic.exception.response.JwtExceptionResponse;
import com.kostenko.youtube.analytic.service.channel.mapper.channel.YoutubeChannelMapper;
import com.kostenko.youtube.analytic.service.channel.mapper.video.YoutubeVideoMapper;
import com.kostenko.youtube.analytic.model.youtube.Channel;
import com.kostenko.youtube.analytic.model.youtube.Video;
import com.kostenko.youtube.analytic.service.channel.YoutubeChannelsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/youtube/analytic/v1")
public class YoutubeAnalyticController {
    private final YoutubeChannelsService youtubeAnalyticDataService;

    private final YoutubeChannelMapper channelMapper;
    private final YoutubeVideoMapper videoMapper;

    @Operation(summary = "Get an youtube channel information by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Channel was loaded successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnalyticChannelDto.class))),
            @ApiResponse(responseCode = "401", description = "JWT is incorrect.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Channel with passed id wasn't found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnalyticExceptionResponse.class)))
    })
    @GetMapping("/channels/{channelId}")
    public AnalyticChannelDto getChannel(@Parameter(description = "Youtube channel id.") @PathVariable("channelId") String id) {
        Channel channel = youtubeAnalyticDataService.getChannel(id);
        return channelMapper.channelToYoutubeAnalyticChannelDto(channel);
    }

    @Operation(summary = "Get youtube videos information by channel id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Videos were loaded successfully.",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AnalyticVideoDto.class)))),
            @ApiResponse(responseCode = "401", description = "JWT is incorrect.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Videos of channel with passed id wasn't found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnalyticExceptionResponse.class)))
    })
    @GetMapping("channels/{channelId}/videos")
    public List<AnalyticVideoDto> getVideos(@Parameter(description = "Youtube channel id.") @PathVariable("channelId") String id) {
        List<Video> videos = youtubeAnalyticDataService.getVideos(id);
        return videoMapper.videosToYoutubeAnalyticVideoDtoList(videos);
    }
}
