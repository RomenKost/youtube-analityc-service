package com.kostenko.youtube.analytic.service.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.AbstractMatcherFilter;
import ch.qos.logback.core.spi.FilterReply;
import lombok.Setter;

@Setter
public class RestTemplateFilter extends AbstractMatcherFilter<ILoggingEvent> {
    private static final String LOGGER_NAME = "org.springframework.web.client.RestTemplate";

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (!isStarted()) {
            return FilterReply.NEUTRAL;
        }
        if (event.getLoggerName().equals(LOGGER_NAME) && event.getLevel().levelInt > Level.DEBUG.levelInt) {
            return onMatch;
        }
        return onMismatch;
    }
}
