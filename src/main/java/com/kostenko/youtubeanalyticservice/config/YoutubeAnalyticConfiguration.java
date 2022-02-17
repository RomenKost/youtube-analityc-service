package com.kostenko.youtubeanalyticservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource("classpath:.env")
public class YoutubeAnalyticConfiguration {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
