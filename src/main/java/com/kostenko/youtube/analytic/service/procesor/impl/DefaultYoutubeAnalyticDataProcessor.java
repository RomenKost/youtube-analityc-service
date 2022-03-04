package com.kostenko.youtube.analytic.service.procesor.impl;

import com.kostenko.youtube.analytic.entity.ChannelEntity;
import com.kostenko.youtube.analytic.entity.ChannelIdEntity;
import com.kostenko.youtube.analytic.entity.VideoEntity;
import com.kostenko.youtube.analytic.exception.YoutubeChannelNotFoundException;
import com.kostenko.youtube.analytic.exception.YoutubeVideosNotFoundException;
import com.kostenko.youtube.analytic.mapper.channel.id.ChannelIdMapper;
import com.kostenko.youtube.analytic.mapper.channel.YoutubeChannelMapper;
import com.kostenko.youtube.analytic.mapper.video.YoutubeVideoMapper;
import com.kostenko.youtube.analytic.model.Channel;
import com.kostenko.youtube.analytic.model.Video;
import com.kostenko.youtube.analytic.repository.ChannelRepository;
import com.kostenko.youtube.analytic.service.procesor.YoutubeAnalyticDataProcessor;
import com.kostenko.youtube.analytic.repository.ChannelsInfoRepository;
import com.kostenko.youtube.analytic.repository.VideosInfoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.kostenko.youtube.analytic.exception.message.ErrorMessages.*;

@Slf4j
@Service
@AllArgsConstructor
public class DefaultYoutubeAnalyticDataProcessor implements YoutubeAnalyticDataProcessor {
    private final VideosInfoRepository videosInfoRepository;
    private final ChannelsInfoRepository channelsInfoRepository;
    private final ChannelRepository channelRepository;

    private final YoutubeChannelMapper channelMapper;
    private final YoutubeVideoMapper videoMapper;
    private final ChannelIdMapper channelIdMapper;

    @Override
    public Channel getChannel(String id) {
        log.info("Loading information about channel with id = " + id + " from database...");
        Optional<ChannelEntity> optionalChannelEntity = channelsInfoRepository.findById(id);

        if (optionalChannelEntity.isEmpty()) {
            YoutubeChannelNotFoundException exception = new YoutubeChannelNotFoundException(id);
            log.error(CHANNEL_NOT_FOUND, exception);
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
        videosInfoRepository.findAllByChannelId(channelEntity)
                .forEach(videoEntities::add);

        if (videoEntities.isEmpty()) {
            YoutubeVideosNotFoundException exception = new YoutubeVideosNotFoundException(channelId);
            log.error(VIDEOS_NOT_FOUND, exception);
            throw exception;
        }

        log.info("Information about videos of channel with id = " + channelId + " was loaded from database.");
        return videoMapper.videoEntitiesToVideos(videoEntities);
    }

    @Override
    public List<String> getChannelIds() {
        log.info("Loading channel ids from database...");
        List<ChannelIdEntity> entities = new ArrayList<>();
        channelRepository.findAll()
                .forEach(entities::add);
        log.info("Channel ids from database was loaded (count = " + entities.size() + ").");
        return channelIdMapper.channelIdEntitiesToStrings(entities);
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
