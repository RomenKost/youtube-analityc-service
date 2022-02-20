package com.kostenko.youtube.analytic.service.exception;

import lombok.Getter;
import org.springframework.web.client.RestClientException;

@Getter
public class YoutubeServiceUnavailableException extends RuntimeException {
    public YoutubeServiceUnavailableException(String id, RestClientException e) {
        super("Youtube api server is unavailable.", e);
        this.id = id;
    }

    private final String id;
}
