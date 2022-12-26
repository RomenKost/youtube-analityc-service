package com.kostenko.youtube.analytic.repository.youtube;

import com.kostenko.youtube.analytic.repository.youtube.entity.ChannelEntity;
import com.kostenko.youtube.analytic.repository.youtube.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideosInfoRepository extends JpaRepository<VideoEntity, String> {
    Iterable<VideoEntity> findAllByChannelId(ChannelEntity channelId);
}
