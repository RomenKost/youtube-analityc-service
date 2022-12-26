package com.kostenko.youtube.analytic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@EnableWebSecurity
@EnableTransactionManagement
@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class YoutubeAnalyticServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(YoutubeAnalyticServiceApplication.class, args);
    }
}
