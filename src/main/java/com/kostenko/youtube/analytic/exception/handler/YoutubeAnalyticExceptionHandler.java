package com.kostenko.youtube.analytic.exception.handler;

import com.kostenko.youtube.analytic.controller.YoutubeAnalyticRestController;
import com.kostenko.youtube.analytic.exception.youtube.YoutubeChannelNotFoundException;
import com.kostenko.youtube.analytic.exception.youtube.YoutubeVideosNotFoundException;
import com.kostenko.youtube.analytic.exception.response.YoutubeAnalyticHTTPExceptionResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice(basePackageClasses = YoutubeAnalyticRestController.class)
public class YoutubeAnalyticExceptionHandler {
    @ResponseBody
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(YoutubeVideosNotFoundException.class)
    public YoutubeAnalyticHTTPExceptionResponse handleYoutubeVideosNotFoundException(YoutubeVideosNotFoundException exception) {
        return YoutubeAnalyticHTTPExceptionResponse.builder()
                .channelId(exception.getId())
                .date(new Date())
                .message(exception.getMessage())
                .exceptionClassName(exception.getClass().getSimpleName())
                .build();
    }

    @ResponseBody
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(YoutubeChannelNotFoundException.class)
    public YoutubeAnalyticHTTPExceptionResponse handleYoutubeChannelNotFoundException(YoutubeChannelNotFoundException exception) {
        return YoutubeAnalyticHTTPExceptionResponse.builder()
                .channelId(exception.getId())
                .date(new Date())
                .message(exception.getMessage())
                .exceptionClassName(exception.getClass().getSimpleName())
                .build();
    }
}
