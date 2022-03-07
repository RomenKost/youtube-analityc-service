package com.kostenko.youtube.analytic.mapper.channel.id;

import com.kostenko.youtube.analytic.entity.ChannelEntity;
import com.kostenko.youtube.analytic.entity.ChannelIdEntity;
import com.kostenko.youtube.analytic.entity.Entities;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ChannelIdMapperTest.class)
@ComponentScan(basePackageClasses = ChannelIdMapper.class)
class ChannelIdMapperTest {
    @Autowired
    private ChannelIdMapper channelIdMapper;

    @Test
    void channelIdEntitiesToStringsTest() {
        List<String> expected = List.of("any id", "another id");
        List<ChannelIdEntity> channelIdEntities = Entities.getChannelIdEntities();

        List<String> actual = channelIdMapper.channelIdEntitiesToStrings(channelIdEntities);

        assertEquals(expected, actual);
    }

    @Test
    void channelIdEntitiesToStringsWhenListIsNullReturnEmptyListTest() {
        assertTrue(channelIdMapper.channelIdEntitiesToStrings(null).isEmpty());
    }

    @Test
    void stringToChannelEntity() {
        ChannelEntity expected = new ChannelEntity();
        expected.setId("any id");

        ChannelEntity actual = channelIdMapper.stringToChannelEntity("any id");

        assertEquals(expected, actual);
    }
}
