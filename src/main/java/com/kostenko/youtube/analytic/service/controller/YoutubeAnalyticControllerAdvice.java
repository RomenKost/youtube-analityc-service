package com.kostenko.youtube.analytic.service.controller;

import com.kostenko.youtube.analytic.service.exception.YoutubeServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class YoutubeAnalyticControllerAdvice {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(YoutubeServiceUnavailableException.class)
    public void processYoutubeServiceUnavailableException(YoutubeServiceUnavailableException exception) {
        log.error("Request to getting information about channel with id=" + exception.getId() + " wasn't processed.", exception);
    }
}
