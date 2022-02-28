package com.kostenko.youtube.analytic.service.exception.handler;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.kostenko.youtube.analytic.service.exception.YoutubeChannelNotFoundException;
import com.kostenko.youtube.analytic.service.exception.YoutubeServiceUnavailableException;
import com.kostenko.youtube.analytic.service.exception.response.YoutubeAnalyticHTTPExceptionResponse;
import com.kostenko.youtube.analytic.service.logger.LoggerChecker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClientException;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = YoutubeAnalyticExceptionHandler.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class YoutubeAnalyticExceptionHandlerTest {
    @Autowired
    private YoutubeAnalyticExceptionHandler exceptionHandler;

    private ListAppender<ILoggingEvent> listAppender;

    @BeforeAll
    void initialize(){
        Logger logger = (Logger) LoggerFactory.getLogger(YoutubeAnalyticExceptionHandler.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @BeforeEach
    void clear() {
        listAppender.list.clear();
    }

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

        Iterator<ILoggingEvent> eventIterator = listAppender.list.iterator();
        LoggerChecker.checkErrorLog(
                eventIterator,
                "Request to getting information about channel with id=any id wasn't processed.",
                serviceUnavailableException
        );
        assertFalse(eventIterator.hasNext());
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

        Iterator<ILoggingEvent> eventIterator = listAppender.list.iterator();
        LoggerChecker.checkErrorLog(
                eventIterator,
                "Request to getting information about channel with id=any id wasn't processed.",
                channelNotFoundException
        );
        assertFalse(eventIterator.hasNext());
    }
}
