package com.kostenko.youtube.analytic.service.mapper.youtube.analytic;

import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticChannelDto;
import com.kostenko.youtube.analytic.service.dto.youtube.analytic.YoutubeAnalyticDto;
import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.V3ApiVideosDto;
import com.kostenko.youtube.analytic.service.dto.youtube.v3.api.YoutubeV3ApiChannelsDto;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Models;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class YoutubeChannelMapperTest {
    @Autowired
    private YoutubeChannelMapper channelMapper;

    @Test
    void youtubeV3ApiChannelsDtoDtoToChannelTest() {
        Channel expected = Models.getChannel();
        YoutubeV3ApiChannelsDto channelsDto = V3ApiVideosDto.getChannels();
        Channel actual = channelMapper.youtubeV3ApiChannelsDtoToChannel(channelsDto);

        assertEquals(expected, actual);
    }

    @Test
    void nullYoutubeV3ApiChannelsDtoToChannelTest() {
        assertNull(channelMapper.youtubeV3ApiChannelsDtoToChannel(null));
    }

    @Test
    void youtubeV3ApiChannelsDtoWithoutItemsToChannelTest() {
        assertNull(channelMapper.youtubeV3ApiChannelsDtoToChannel(new YoutubeV3ApiChannelsDto()));
    }

    @Test
    void emptyYoutubeV3ApiChannelsDtoToChannelTest() {
        YoutubeV3ApiChannelsDto channelsDto = new YoutubeV3ApiChannelsDto(new YoutubeV3ApiChannelsDto.Item[0]);
        assertNull(channelMapper.youtubeV3ApiChannelsDtoToChannel(channelsDto));
    }

    @Test
    void youtubeV3ApiChannelsDtoWithoutSnippetToChannelTest() {
        YoutubeV3ApiChannelsDto.Item item = new YoutubeV3ApiChannelsDto.Item("any id", null);
        YoutubeV3ApiChannelsDto channelsDto = new YoutubeV3ApiChannelsDto(new YoutubeV3ApiChannelsDto.Item[]{item});
        assertNull(channelMapper.youtubeV3ApiChannelsDtoToChannel(channelsDto));
    }

    @Test
    void channelToYoutubeAnalyticChannelDtoTest() {
        YoutubeAnalyticChannelDto expected = YoutubeAnalyticDto.getChannel();
        Channel channel = Models.getChannel();
        YoutubeAnalyticChannelDto actual = channelMapper.channelToYoutubeAnalyticChannelDto(channel);

        assertEquals(expected, actual);
    }

    @Test
    void nullChannelToYoutubeAnalyticChannelDtoTest() {
        assertNull(channelMapper.channelToYoutubeAnalyticChannelDto(null));
    }
}
