package com.kostenko.youtube.analytic.repository;

import com.kostenko.youtube.analytic.entity.ChannelEntity;
import com.kostenko.youtube.analytic.entity.VideoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideosInfoRepository extends CrudRepository<VideoEntity, String> {
    Iterable<VideoEntity> findAllByChannelId(ChannelEntity channelId);
}
