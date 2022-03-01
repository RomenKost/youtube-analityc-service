package com.kostenko.youtube.analytic.service.exception.handler;

import com.kostenko.youtube.analytic.service.exception.YoutubeChannelNotFoundException;
import com.kostenko.youtube.analytic.service.exception.YoutubeServiceUnavailableException;
import com.kostenko.youtube.analytic.service.exception.response.YoutubeAnalyticHTTPExceptionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClientException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = YoutubeAnalyticExceptionHandler.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class YoutubeAnalyticExceptionHandlerTest {
    @Autowired
    private YoutubeAnalyticExceptionHandler exceptionHandler;

    @Test
    void processYoutubeServiceUnavailableExceptionTest() {
        YoutubeAnalyticHTTPExceptionResponse excepted = new YoutubeAnalyticHTTPExceptionResponse(
                "Youtube service is unavailable.", "any id"
        );
        YoutubeServiceUnavailableException serviceUnavailableException = new YoutubeServiceUnavailableException(
                "any id", new RestClientException("Rest exception")
        );


        YoutubeAnalyticHTTPExceptionResponse actual = exceptionHandler.processYoutubeServiceUnavailableException(
                serviceUnavailableException
        );

        assertEquals(excepted, actual);
    }

    @Test
    void processYoutubeChannelNotFoundExceptionTest() {
        YoutubeAnalyticHTTPExceptionResponse excepted = new YoutubeAnalyticHTTPExceptionResponse(
                "Youtube channel wasn't found.", "any id"
        );
        YoutubeChannelNotFoundException channelNotFoundException = new YoutubeChannelNotFoundException("any id");


        YoutubeAnalyticHTTPExceptionResponse actual = exceptionHandler.processYoutubeChannelNotFoundException(
                channelNotFoundException
        );

        assertEquals(excepted, actual);
    }
}
