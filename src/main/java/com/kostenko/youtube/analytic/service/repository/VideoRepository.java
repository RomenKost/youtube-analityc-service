package com.kostenko.youtube.analytic.service.repository;

import com.kostenko.youtube.analytic.service.entity.ChannelEntity;
import com.kostenko.youtube.analytic.service.entity.VideoEntity;
import org.springframework.data.repository.Repository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface VideoRepository extends Repository<VideoEntity, String> {
    List<VideoEntity> findAllByChannelId(ChannelEntity channelId);
}
