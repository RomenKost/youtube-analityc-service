package com.kostenko.youtube.analytic.service.exception.handler;

import com.kostenko.youtube.analytic.service.exception.YoutubeChannelNotFoundException;
import com.kostenko.youtube.analytic.service.exception.YoutubeServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DatabaseManagerExceptionHandler {
    public void processThrowable(Throwable throwable) {
        if (throwable instanceof YoutubeServiceUnavailableException) {
            processYoutubeServiceUnavailableException((YoutubeServiceUnavailableException) throwable);
        } else if (throwable instanceof YoutubeChannelNotFoundException) {
            processYoutubeChannelNotFoundException((YoutubeChannelNotFoundException) throwable);
        } else {
            processUnknownException(throwable);
        }
    }

    private void processYoutubeServiceUnavailableException(YoutubeServiceUnavailableException exception) {
        log.error(
                "Request to getting information about channel with id=" + exception.getId() + " wasn't processed. " +
                        "Youtube service is unavailable.", exception
        );
    }

    private void processYoutubeChannelNotFoundException(YoutubeChannelNotFoundException exception) {
        log.error(
                "Request to getting information about channel with id=" + exception.getId() + " wasn't processed. " +
                        "Channel with such id wasn't found.", exception
        );
    }

    private void processUnknownException(Throwable throwable) {
        log.error("Unknown exception was thrown.", throwable);
    }
}
