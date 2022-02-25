package com.kostenko.youtube.analytic.service.model.youtube.analytic;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Video {
    private String videoId;
    private String title;
    private String description;
    private Date publishedAt;
}
