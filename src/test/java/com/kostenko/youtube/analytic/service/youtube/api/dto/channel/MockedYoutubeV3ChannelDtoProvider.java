package com.kostenko.youtube.analytic.service.youtube.api.dto.channel;

import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;

import static com.kostenko.youtube.analytic.util.TestConstants.*;

@UtilityClass
public class MockedYoutubeV3ChannelDtoProvider {
    public YoutubeV3ChannelSnippetDto getMockedYoutubeV3ChannelSnippetDto() {
        YoutubeV3ChannelSnippetDto snippet = new YoutubeV3ChannelSnippetDto();
        snippet.setCountry(TEST_COUNTRY);
        snippet.setDescription(TEST_TEXT_FIELD);
        snippet.setTitle(TEST_TEXT_FIELD);
        snippet.setPublishedAt(TEST_DATE);
        return snippet;
    }

    public List<YoutubeV3ChannelDto> getMockedYoutubeV3ChannelDtoList() {
        return List.of(getMockedYoutubeV3ChannelDto());
    }

    public YoutubeV3ChannelDto getMockedYoutubeV3ChannelDto() {
        YoutubeV3ChannelDto channel = new YoutubeV3ChannelDto();
        channel.setId(TEST_CHANNEL_ID);
        channel.setSnippet(getMockedYoutubeV3ChannelSnippetDto());
        return channel;
    }

    public YoutubeV3ChannelsDto getMockedYoutubeV3ChannelsDto() {
        YoutubeV3ChannelsDto channelsDto = new YoutubeV3ChannelsDto();
        channelsDto.setChannels(Collections.singletonList(getMockedYoutubeV3ChannelDto()));
        return channelsDto;
    }
}
