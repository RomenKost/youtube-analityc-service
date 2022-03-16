package com.kostenko.youtube.analytic.config;

import com.kostenko.youtube.analytic.exception.handler.YoutubeAnalyticFilterChainExceptionHandler;
import com.kostenko.youtube.analytic.security.filter.YoutubeAnalyticJwtTokenVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import static com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserPermission.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration
@EnableWebSecurity
public class YoutubeAnalyticSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final String secretKey;

    public YoutubeAnalyticSecurityConfiguration(@Value("${youtube.analytic.security.jwt.key}") String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(STATELESS)
                    .and()
                .addFilterBefore(jwtTokenVerifier(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(youtubeAnalyticFilterChainExceptionHandler(), LogoutFilter.class)
                .authorizeRequests()
                    .antMatchers(GET, "/actuator/health")
                        .permitAll()
                    .antMatchers(POST, "/youtube/analytic/v1/login")
                        .permitAll()
                    .antMatchers(GET, "/youtube/analytic/v1/channels/*")
                        .hasAuthority(FIND_CHANNELS.name())
                    .antMatchers(GET, "/youtube/analytic/v1/channels/*/videos")
                        .hasAuthority(FIND_VIDEOS.name())
                    .anyRequest().denyAll()
                    .and();
    }

    @Bean
    public YoutubeAnalyticJwtTokenVerifier jwtTokenVerifier() {
        return new YoutubeAnalyticJwtTokenVerifier(secretKey);
    }

    @Bean
    public YoutubeAnalyticFilterChainExceptionHandler youtubeAnalyticFilterChainExceptionHandler() {
        return new YoutubeAnalyticFilterChainExceptionHandler();
    }
}
