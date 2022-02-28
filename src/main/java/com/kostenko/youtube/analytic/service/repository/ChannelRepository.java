package com.kostenko.youtube.analytic.service.repository;

import com.kostenko.youtube.analytic.service.entity.ChannelIdEntity;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface ChannelRepository extends Repository<ChannelIdEntity, String> {
    Iterable<ChannelIdEntity> findAll();
}
