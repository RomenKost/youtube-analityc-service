package com.kostenko.youtube.analytic.exception.handler;

import com.kostenko.youtube.analytic.controller.YoutubeAnalyticRestController;
import com.kostenko.youtube.analytic.exception.YoutubeChannelNotFoundException;
import com.kostenko.youtube.analytic.exception.YoutubeVideosNotFoundException;
import com.kostenko.youtube.analytic.exception.response.YoutubeAnalyticHTTPExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@Slf4j
@ControllerAdvice(basePackageClasses = YoutubeAnalyticRestController.class)
public class YoutubeAnalyticExceptionHandler {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(YoutubeVideosNotFoundException.class)
    public YoutubeAnalyticHTTPExceptionResponse processYoutubeVideosNotFoundException(YoutubeVideosNotFoundException exception) {
        return YoutubeAnalyticHTTPExceptionResponse.builder()
                .channelId(exception.getId())
                .date(new Date())
                .message(exception.getMessage())
                .exceptionClassName(exception.getClass().getSimpleName())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(YoutubeChannelNotFoundException.class)
    public YoutubeAnalyticHTTPExceptionResponse processYoutubeChannelNotFoundException(YoutubeChannelNotFoundException exception) {
        return YoutubeAnalyticHTTPExceptionResponse.builder()
                .channelId(exception.getId())
                .date(new Date())
                .message(exception.getMessage())
                .exceptionClassName(exception.getClass().getSimpleName())
                .build();
    }
}
