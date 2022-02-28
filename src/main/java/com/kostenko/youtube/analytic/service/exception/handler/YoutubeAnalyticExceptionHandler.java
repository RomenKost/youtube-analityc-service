package com.kostenko.youtube.analytic.service.exception.handler;

import com.kostenko.youtube.analytic.service.exception.YoutubeServiceUnavailableException;
import com.kostenko.youtube.analytic.service.exception.response.YoutubeAnalyticHTTPResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@Slf4j
@ControllerAdvice
public class YoutubeAnalyticExceptionHandler {
    @ExceptionHandler(YoutubeServiceUnavailableException.class)
    public ResponseEntity<YoutubeAnalyticHTTPResponse> processYoutubeServiceUnavailableException(YoutubeServiceUnavailableException exception) {
        log.error("Request to getting information about channel with id=" + exception.getId() + " wasn't processed.", exception);
        YoutubeAnalyticHTTPResponse response = new YoutubeAnalyticHTTPResponse("Youtube service is unavailable.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
