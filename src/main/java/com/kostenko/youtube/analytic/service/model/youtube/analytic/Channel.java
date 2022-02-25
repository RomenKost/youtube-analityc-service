package com.kostenko.youtube.analytic.service.model.youtube.analytic;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Channel {
    private String id;
    private String title;
    private String description;
    private String country;
    private Date publishedAt;
}
