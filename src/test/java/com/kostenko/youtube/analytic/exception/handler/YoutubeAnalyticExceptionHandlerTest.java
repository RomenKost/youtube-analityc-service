package com.kostenko.youtube.analytic.exception.handler;

import com.kostenko.youtube.analytic.exception.youtube.YoutubeChannelNotFoundException;
import com.kostenko.youtube.analytic.exception.youtube.YoutubeVideosNotFoundException;
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
                .message("Videos of channel with id = any id weren't found.")
                .channelId("any id")
                .exceptionClassName(YoutubeVideosNotFoundException.class.getSimpleName())
                .build();

        YoutubeVideosNotFoundException youtubeVideosNotFoundException = new YoutubeVideosNotFoundException("any id");


        YoutubeAnalyticHTTPExceptionResponse actual = exceptionHandler.handleYoutubeVideosNotFoundException(
                youtubeVideosNotFoundException
        );

        assertEquals(excepted, actual);
    }

    @Test
    void processYoutubeChannelNotFoundExceptionTest() {
        YoutubeAnalyticHTTPExceptionResponse excepted = YoutubeAnalyticHTTPExceptionResponse
                .builder()
                .message("Channel with id = any id wasn't found.")
                .channelId("any id")
                .exceptionClassName(YoutubeChannelNotFoundException.class.getSimpleName())
                .build();

        YoutubeChannelNotFoundException channelNotFoundException = new YoutubeChannelNotFoundException("any id");

        YoutubeAnalyticHTTPExceptionResponse actual = exceptionHandler.handleYoutubeChannelNotFoundException(
                channelNotFoundException
        );

        assertEquals(excepted, actual);
    }
}
