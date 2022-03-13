package com.kostenko.youtube.analytic.service.security.user.processor;

import com.kostenko.youtube.analytic.model.security.YoutubeAnalyticUser;

public interface YoutubeAnalyticUserProcessor {
    YoutubeAnalyticUser getUser(String username);
}
