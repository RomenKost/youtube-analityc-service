package com.kostenko.youtube.analytic.dto.youtube.analytic;

import com.kostenko.youtube.analytic.dto.security.UserCredentialsDto;
import com.kostenko.youtube.analytic.dto.security.YoutubeAnalyticJwtDto;
import com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserRole;
import lombok.experimental.UtilityClass;

import java.util.Date;
import java.util.List;

import static com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserRole.*;

@UtilityClass
public class YoutubeAnalyticDTOs {
    public List<YoutubeAnalyticVideoDto> getVideoDTOs() {
        YoutubeAnalyticVideoDto firstVideo = getVideoDto();
        YoutubeAnalyticVideoDto secondVideo = YoutubeAnalyticVideoDto
                .builder()
                .videoId("another id")
                .title("another title")
                .description("another description")
                .publishedAt(new Date(1_700_000_000_000L))
                .build();
        return List.of(firstVideo, secondVideo);
    }

    public YoutubeAnalyticChannelDto getChannelDto() {
        return YoutubeAnalyticChannelDto
                .builder()
                .id("any id")
                .title("any title")
                .description("any description")
                .country("ac")
                .publishedAt(new Date(1_600_000_000_000L))
                .build();
    }

    public YoutubeAnalyticVideoDto getVideoDto() {
        return YoutubeAnalyticVideoDto
                .builder()
                .videoId("any id")
                .title("any title")
                .description("any description")
                .publishedAt(new Date(1_600_000_000_000L))
                .build();
    }

    public YoutubeAnalyticJwtDto getJwtDto(YoutubeAnalyticUserRole userRole) {
        return YoutubeAnalyticJwtDto
                .builder()
                .username("any username")
                .token("any token")
                .expiration(12345)
                .role(userRole)
                .build();
    }

    public UserCredentialsDto getCredentialsDto() {
        return new UserCredentialsDto("any username", "any password");
    }
}
