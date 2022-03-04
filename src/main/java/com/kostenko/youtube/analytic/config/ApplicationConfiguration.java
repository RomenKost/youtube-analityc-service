package com.kostenko.youtube.analytic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DatabaseManagerConfiguration.class, YoutubeAnalyticConfiguration.class})
public class ApplicationConfiguration {

}
