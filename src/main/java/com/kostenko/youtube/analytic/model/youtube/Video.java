package com.kostenko.youtube.analytic.model.youtube;

import lombok.Data;

import java.util.Date;

@Data
public class Video {
    private String videoId;
    private String title;
    private String description;
    private Date publishedAt;
}
