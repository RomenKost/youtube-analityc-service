package com.kostenko.youtube.analytic.service.channel.mapper;

import com.kostenko.youtube.analytic.config.MockedDataSourceConfiguration;
import com.kostenko.youtube.analytic.model.youtube.Channel;
import com.kostenko.youtube.analytic.repository.youtube.entity.ChannelEntity;
import com.kostenko.youtube.analytic.service.channel.mapper.channel.YoutubeChannelMapper;
import com.kostenko.youtube.analytic.service.youtube.api.dto.channel.YoutubeV3ChannelDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.kostenko.youtube.analytic.model.youtube.MockedYoutubeModelsProvider.*;
import static com.kostenko.youtube.analytic.repository.youtube.entity.MockedYoutubeEntityProvider.*;
import static com.kostenko.youtube.analytic.service.youtube.api.dto.channel.MockedYoutubeV3ChannelDtoProvider.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = MockedDataSourceConfiguration.class)
class YoutubeChannelMapperTest {
    @Autowired
    private YoutubeChannelMapper channelMapper;

    @Test
    void youtubeV3ChannelDtoToChannel() {
        YoutubeV3ChannelDto channelDto = getMockedYoutubeV3ChannelDto();

        Channel expected = getMockedChannel();
        Channel actual = channelMapper.youtubeV3ChannelDtoToChannel(channelDto);

        assertEquals(expected, actual);
    }

    @Test
    void channelToChannelEntity() {
        Channel channel = getMockedChannel();

        ChannelEntity expected = getMockedChannelEntity();
        expected.setVideoEntities(null);
        expected.setLastCheck(null);

        ChannelEntity actual = channelMapper.channelToChannelEntity(channel);

        assertEquals(expected, actual);
    }
}
