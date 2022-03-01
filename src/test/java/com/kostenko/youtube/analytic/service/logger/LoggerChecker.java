package com.kostenko.youtube.analytic.service.logger;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxy;
import lombok.experimental.UtilityClass;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@UtilityClass
public class LoggerChecker {
    public void checkErrorLog(Iterator<ILoggingEvent> eventIterator, String expectedMessage, Throwable throwable) {
        assertTrue(eventIterator.hasNext());
        ILoggingEvent iLoggingEvent = eventIterator.next();

        assertEquals(Level.ERROR, iLoggingEvent.getLevel());
        assertEquals(expectedMessage, iLoggingEvent.getMessage());

        IThrowableProxy iThrowableProxy = iLoggingEvent.getThrowableProxy();
        assertTrue(iThrowableProxy instanceof ThrowableProxy);
        assertEquals(throwable, ((ThrowableProxy) iThrowableProxy).getThrowable());
    }
}
