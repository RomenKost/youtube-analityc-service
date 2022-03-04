package com.kostenko.youtube.analytic.exception.handler;

import com.kostenko.youtube.analytic.exception.YoutubeChannelNotFoundException;
import com.kostenko.youtube.analytic.exception.YoutubeVideosNotFoundException;
import com.kostenko.youtube.analytic.exception.response.YoutubeAnalyticHTTPExceptionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = YoutubeAnalyticExceptionHandler.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class YoutubeAnalyticExceptionHandlerTest {
    @Autowired
    private YoutubeAnalyticExceptionHandler exceptionHandler;

    @Test
    void processYoutubeVideosNotFoundExceptionTest() {
        YoutubeAnalyticHTTPExceptionResponse excepted = YoutubeAnalyticHTTPExceptionResponse
                .builder()
                .message("Videos weren't found.")
                .channelId("any id")
                .exceptionClassName(YoutubeVideosNotFoundException.class.getSimpleName())
                .build();

        YoutubeVideosNotFoundException youtubeVideosNotFoundException = new YoutubeVideosNotFoundException("any id");


        YoutubeAnalyticHTTPExceptionResponse actual = exceptionHandler.processYoutubeVideosNotFoundException(
                youtubeVideosNotFoundException
        );

        assertEquals(excepted, actual);
    }

    @Test
    void processYoutubeChannelNotFoundExceptionTest() {
        YoutubeAnalyticHTTPExceptionResponse excepted = YoutubeAnalyticHTTPExceptionResponse
                .builder()
                .message("Channel wasn't found.")
                .channelId("any id")
                .exceptionClassName(YoutubeChannelNotFoundException.class.getSimpleName())
                .build();

        YoutubeChannelNotFoundException channelNotFoundException = new YoutubeChannelNotFoundException("any id");

        YoutubeAnalyticHTTPExceptionResponse actual = exceptionHandler.processYoutubeChannelNotFoundException(
                channelNotFoundException
        );

        assertEquals(excepted, actual);
    }
}
