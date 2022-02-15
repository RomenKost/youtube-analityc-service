package com.kostenko.youtubeanalyticservice.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
@EqualsAndHashCode
public class YoutubeVideoDto {
    private String videoId;
    private String title;
    private String description;
    private Date publishedAt;
}
