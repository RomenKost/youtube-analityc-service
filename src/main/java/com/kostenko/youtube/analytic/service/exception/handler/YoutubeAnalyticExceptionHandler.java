package com.kostenko.youtube.analytic.service.exception.handler;

import com.kostenko.youtube.analytic.service.controller.YoutubeAnalyticRestController;
import com.kostenko.youtube.analytic.service.exception.YoutubeChannelNotFoundException;
import com.kostenko.youtube.analytic.service.exception.YoutubeServiceUnavailableException;
import com.kostenko.youtube.analytic.service.exception.response.YoutubeAnalyticHTTPExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice(basePackageClasses = YoutubeAnalyticRestController.class)
public class YoutubeAnalyticExceptionHandler {
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(YoutubeServiceUnavailableException.class)
    public YoutubeAnalyticHTTPExceptionResponse processYoutubeServiceUnavailableException(YoutubeServiceUnavailableException exception) {
        log.error("Request to getting information about channel with id=" + exception.getId() + " wasn't processed.", exception);
        return new YoutubeAnalyticHTTPExceptionResponse("Youtube service is unavailable.", exception.getId());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(YoutubeChannelNotFoundException.class)
    public YoutubeAnalyticHTTPExceptionResponse processYoutubeChannelNotFoundException(YoutubeChannelNotFoundException exception) {
        log.error("Request to getting information about channel with id=" + exception.getId() + " wasn't processed.", exception);
        return new YoutubeAnalyticHTTPExceptionResponse("Youtube channel wasn't found.", exception.getId());
    }
}
