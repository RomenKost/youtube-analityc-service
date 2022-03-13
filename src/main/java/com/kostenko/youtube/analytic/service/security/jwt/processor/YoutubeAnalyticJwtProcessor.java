package com.kostenko.youtube.analytic.service.security.jwt.processor;

import com.kostenko.youtube.analytic.model.security.YoutubeAnalyticJwt;

public interface YoutubeAnalyticJwtProcessor {
    YoutubeAnalyticJwt getJwt(String username, String password);
}
