package com.kostenko.youtube.analytic.exception.youtube;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ChannelNotFoundException extends RuntimeException {
    private final String id;

    public ChannelNotFoundException(String id) {
        super("Channel with id = " + id + " wasn't found.");
        this.id = id;
    }
}
