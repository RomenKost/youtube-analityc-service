package com.kostenko.youtube.analytic.service.controller;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.kostenko.youtube.analytic.service.exception.YoutubeServiceUnavailableException;
import com.kostenko.youtube.analytic.service.logger.LoggerChecker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertFalse;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class YoutubeAnalyticControllerAdviceTest {
    private final YoutubeAnalyticControllerAdvice controllerAdvice = new YoutubeAnalyticControllerAdvice();

    private ListAppender<ILoggingEvent> listAppender;

    @BeforeAll
    void initialize(){
        Logger logger = (Logger) LoggerFactory.getLogger(YoutubeAnalyticControllerAdvice.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @BeforeEach
    void clear() {
        listAppender.list.clear();
    }

    @Test
    void processYoutubeServiceUnavailableException() {
        YoutubeServiceUnavailableException serviceUnavailableException = new YoutubeServiceUnavailableException(
                "id", new RestClientException("Rest exception")
        );
        controllerAdvice.processYoutubeServiceUnavailableException(serviceUnavailableException);

        Iterator<ILoggingEvent> eventIterator = listAppender.list.iterator();
        LoggerChecker.checkErrorLog(
                eventIterator,
                "Request to getting information about channel with id=id wasn't processed.",
                serviceUnavailableException
        );
        assertFalse(eventIterator.hasNext());
    }
}
