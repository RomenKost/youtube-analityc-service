package com.kostenko.youtube.analytic.service.service.database.reader.client.impl;

import com.kostenko.youtube.analytic.service.entity.ChannelEntity;
import com.kostenko.youtube.analytic.service.entity.VideoEntity;
import com.kostenko.youtube.analytic.service.exception.YoutubeChannelNotFoundException;
import com.kostenko.youtube.analytic.service.exception.YoutubeVideosNotFoundException;
import com.kostenko.youtube.analytic.service.mapper.database.manager.ChannelIdMapper;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeChannelMapper;
import com.kostenko.youtube.analytic.service.mapper.youtube.analytic.YoutubeVideoMapper;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Channel;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;
import com.kostenko.youtube.analytic.service.repository.ChannelsInfoRepository;
import com.kostenko.youtube.analytic.service.repository.VideosInfoRepository;
import com.kostenko.youtube.analytic.service.service.database.reader.client.DatabaseReaderClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class SQLYoutubeDatabaseReaderClient implements DatabaseReaderClient {
    private final VideosInfoRepository videoRepository;
    private final ChannelsInfoRepository channelsInfoRepository;

    private final YoutubeChannelMapper channelMapper;
    private final YoutubeVideoMapper videoMapper;
    private final ChannelIdMapper channelIdMapper;

    @Override
    public Channel getChannel(String id) {
        log.info("Loading information about channel with id = " + id + " from database...");
        Optional<ChannelEntity> optionalChannelEntity = channelsInfoRepository.findById(id);

        if (optionalChannelEntity.isEmpty()) {
            YoutubeChannelNotFoundException exception = new YoutubeChannelNotFoundException(id);
            log.warn("Information about channel with id = " + id + " wasn't loaded from database.", exception);
            throw exception;
        }

        log.info("Information about channel with id = " + id + " was loaded from database.");
        return channelMapper.channelEntityToChannel(optionalChannelEntity.get());
    }

    @Override
    public List<Video> getVideos(String channelId) {
        log.info("Loading information about videos of channel with id = " + channelId + " from database...");
        ChannelEntity channelEntity = channelIdMapper.stringToChannelEntity(channelId);

        List<VideoEntity> videoEntities = new ArrayList<>();
        videoRepository.findAllByChannelId(channelEntity)
                .forEach(videoEntities::add);

        if (videoEntities.isEmpty()) {
            YoutubeVideosNotFoundException exception = new YoutubeVideosNotFoundException(channelId);
            log.warn("Information about videos of channel with id = " + channelId + " wasn't loaded from database.", exception);
            throw exception;
        }

        log.info("Information about videos of channel with id = " + channelId + " was loaded from database.");
        return videoMapper.videoEntitiesToVideos(videoEntities);
    }
}
