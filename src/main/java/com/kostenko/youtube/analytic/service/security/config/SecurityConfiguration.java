package com.kostenko.youtube.analytic.service.security.config;

import com.kostenko.youtube.analytic.exception.handler.JwtFilterChainExceptionHandler;
import com.kostenko.youtube.analytic.service.jwt.filter.JwtTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import static com.kostenko.youtube.analytic.model.security.user.UserPermission.FIND_CHANNELS;
import static com.kostenko.youtube.analytic.model.security.user.UserPermission.FIND_VIDEOS;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JwtTokenVerifier youtubeAnalyticJwtTokenVerifier;
    private final JwtFilterChainExceptionHandler youtubeAnalyticFilterChainExceptionHandler;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(STATELESS)
                    .and()
                .addFilterBefore(youtubeAnalyticJwtTokenVerifier, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(youtubeAnalyticFilterChainExceptionHandler, LogoutFilter.class)
                .authorizeRequests()
                    .antMatchers(GET, "/actuator/health")
                        .permitAll()
                    .antMatchers(POST, "/youtube/analytic/v1/login")
                        .permitAll()
                    .antMatchers(GET, "/youtube/analytic/v1/channels/*")
                        .hasAuthority(FIND_CHANNELS.name())
                    .antMatchers(GET, "/youtube/analytic/v1/channels/*/videos")
                        .hasAuthority(FIND_VIDEOS.name())
                    .anyRequest()
                        .denyAll();
    }
}
