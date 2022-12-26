package com.kostenko.youtube.analytic.model.youtube;

import lombok.Data;

import java.util.Date;

@Data
public class Channel {
    private String id;
    private String title;
    private String description;
    private String country;
    private Date publishedAt;
}
