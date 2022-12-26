package com.kostenko.youtube.analytic.exception.handler;

import com.kostenko.youtube.analytic.controller.YoutubeAnalyticController;
import com.kostenko.youtube.analytic.exception.mapper.ExceptionResponseMapper;
import com.kostenko.youtube.analytic.exception.response.AnalyticExceptionResponse;
import com.kostenko.youtube.analytic.exception.youtube.ChannelNotFoundException;
import com.kostenko.youtube.analytic.exception.youtube.VideosNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice(basePackageClasses = YoutubeAnalyticController.class)
public class YoutubeAnalyticExceptionHandler {
    private final ExceptionResponseMapper exceptionResponseMapper;

    @ResponseBody
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(VideosNotFoundException.class)
    public AnalyticExceptionResponse handleYoutubeVideosNotFoundException(VideosNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return exceptionResponseMapper.mapExceptionToExceptionResponse(exception);
    }

    @ResponseBody
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(ChannelNotFoundException.class)
    public AnalyticExceptionResponse handleYoutubeChannelNotFoundException(ChannelNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return exceptionResponseMapper.mapExceptionToExceptionResponse(exception);
    }
}
