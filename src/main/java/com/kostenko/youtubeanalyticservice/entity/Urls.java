package com.kostenko.youtubeanalyticservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Urls {
    VIDEOS("https://www.googleapis.com/youtube/v3/search?key={key}&channelId={channelId}&part={part}&type={type}&maxResults={maxResults}"),
    CHANNELS("https://www.googleapis.com/youtube/v3/channels?key={key}&id={id}&part={part}&maxResults={maxResults}");

    private final String url;
}
