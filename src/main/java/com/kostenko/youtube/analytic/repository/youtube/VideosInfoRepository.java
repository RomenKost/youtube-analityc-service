package com.kostenko.youtube.analytic.repository.youtube;

import com.kostenko.youtube.analytic.entity.youtube.ChannelEntity;
import com.kostenko.youtube.analytic.entity.youtube.VideoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideosInfoRepository extends CrudRepository<VideoEntity, String> {
    Iterable<VideoEntity> findAllByChannelId(ChannelEntity channelId);
}
