package com.kostenko.youtube.analytic.service.service.database.manager.client.impl;

import com.kostenko.youtube.analytic.service.entity.ChannelEntity;
import com.kostenko.youtube.analytic.service.entity.ChannelIdEntity;
import com.kostenko.youtube.analytic.service.entity.VideoEntity;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeChannelMapper;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeVideoMapper;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;
import com.kostenko.youtube.analytic.service.repository.ChannelRepository;
import com.kostenko.youtube.analytic.service.repository.ChannelsInfoRepository;
import com.kostenko.youtube.analytic.service.service.database.manager.client.DatabaseClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SQLDatabaseClient implements DatabaseClient {
    private final ChannelRepository channelRepository;
    private final ChannelsInfoRepository channelsInfoRepository;

    private final YoutubeChannelMapper channelMapper;
    private final YoutubeVideoMapper videoMapper;

    public SQLDatabaseClient(ChannelRepository channelRepository,
                             ChannelsInfoRepository channelsInfoRepository,
                             YoutubeChannelMapper channelMapper,
                             YoutubeVideoMapper videoMapper) {
        this.channelRepository = channelRepository;
        this.channelsInfoRepository = channelsInfoRepository;
        this.channelMapper = channelMapper;
        this.videoMapper = videoMapper;
    }

    @Override
    public List<ChannelIdEntity> getChannelIds() {
        log.info("Loading channel ids from database...");
        List<ChannelIdEntity> entities = new ArrayList<>();
        channelRepository.findAll()
                .forEach(entities::add);
        log.info("Channel ids from database was loaded (count = " + entities.size() + ").");
        return entities;
    }

    @Override
    public void saveReport(Channel channel, List<Video> videos) {
        log.info("Saving report to the database (id = " + channel.getId() + ", videos count: " + videos.size() + ")...");
        ChannelEntity channelEntity = channelMapper.channelToChannelEntity(channel);
        List<VideoEntity> videoEntities = videoMapper.videosToVideoEntities(videos);

        videoEntities.forEach(videoEntity -> videoEntity.setChannelId(channelEntity));
        channelEntity.setVideoEntities(new HashSet<>(videoEntities));

        channelsInfoRepository.save(channelEntity);
        log.info("Report was saved to the database (id = " + channel.getId() + ", videos count: " + videos.size() + ").");
    }
}
