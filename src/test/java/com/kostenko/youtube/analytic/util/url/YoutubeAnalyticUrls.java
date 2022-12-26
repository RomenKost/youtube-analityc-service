package com.kostenko.youtube.analytic.util.url;

import lombok.experimental.UtilityClass;

@UtilityClass
public class YoutubeAnalyticUrls {
    public final String GET_CHANNEL_BY_ID = "/youtube/analytic/v1/channels/{channel_id}";
    public final String GET_VIDEOS_BY_CHANNEL_ID = "/youtube/analytic/v1/channels/{channel_id}/videos";

    public final String LOGIN = "/youtube/analytic/v1/login";
}
