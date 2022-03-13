package com.kostenko.youtube.analytic.mapper.youtube.channel.id;

import com.kostenko.youtube.analytic.entity.youtube.ChannelEntity;
import com.kostenko.youtube.analytic.entity.youtube.ChannelIdEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ChannelIdMapper {
    default List<String> channelIdEntitiesToStrings(List<ChannelIdEntity> channelIdEntities) {
        return channelIdEntities == null ? List.of()
                : channelIdEntities.stream()
                .map(ChannelIdEntity::getId)
                .collect(Collectors.toList());
    }

    default ChannelEntity stringToChannelEntity(String channelId) {
        ChannelEntity channelEntity = new ChannelEntity();
        channelEntity.setId(channelId);
        return channelEntity;
    }
}
