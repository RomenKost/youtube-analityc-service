package com.kostenko.youtubeanalyticservice.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
@EqualsAndHashCode
public class YoutubeChannelDto {
    private String title;
    private String description;
    private String country;
    private Date publishedAt;
}
