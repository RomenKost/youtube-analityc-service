package com.kostenko.youtube.analytic.service.repository;

import com.kostenko.youtube.analytic.service.entity.ChannelEntity;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Repository
public interface ChannelsInfoRepository extends Repository<ChannelEntity, String> {
    @Transactional
    void save(ChannelEntity channelEntity);

    ChannelEntity findById(String id);
}
