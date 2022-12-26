package com.kostenko.youtube.analytic.service.channel.impl;

import com.kostenko.youtube.analytic.exception.youtube.ChannelNotFoundException;
import com.kostenko.youtube.analytic.exception.youtube.VideosNotFoundException;
import com.kostenko.youtube.analytic.repository.youtube.entity.ChannelEntity;
import com.kostenko.youtube.analytic.repository.youtube.entity.ChannelIdEntity;
import com.kostenko.youtube.analytic.repository.youtube.entity.VideoEntity;
import com.kostenko.youtube.analytic.service.channel.mapper.channel.YoutubeChannelMapper;
import com.kostenko.youtube.analytic.service.channel.mapper.video.YoutubeVideoMapper;
import com.kostenko.youtube.analytic.model.youtube.Channel;
import com.kostenko.youtube.analytic.model.youtube.Video;
import com.kostenko.youtube.analytic.repository.youtube.ChannelRepository;
import com.kostenko.youtube.analytic.repository.youtube.ChannelsInfoRepository;
import com.kostenko.youtube.analytic.repository.youtube.VideosInfoRepository;
import com.kostenko.youtube.analytic.service.channel.YoutubeChannelsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.kostenko.youtube.analytic.exception.message.ErrorMessages.*;

@Slf4j
@Service
@AllArgsConstructor
public class DefaultYoutubeChannelService implements YoutubeChannelsService {
    private final VideosInfoRepository videosInfoRepository;
    private final ChannelsInfoRepository channelsInfoRepository;
    private final ChannelRepository channelRepository;

    private final YoutubeChannelMapper channelMapper;
    private final YoutubeVideoMapper videoMapper;

    @Override
    public Channel getChannel(String id) {
        log.info("Loading information about channel with id = {} from database.", id);
        Optional<ChannelEntity> optionalChannelEntity = channelsInfoRepository.findById(id);

        if (optionalChannelEntity.isEmpty()) {
            ChannelNotFoundException exception = new ChannelNotFoundException(id);
            log.error(CHANNEL_NOT_FOUND, exception);
            throw exception;
        }

        log.info("Information about channel with id = {} was loaded from database.", id);
        return channelMapper.channelEntityToChannel(optionalChannelEntity.get());
    }

    @Override
    public List<Video> getVideos(String channelId) {
        log.info("Loading information about videos of channel with id = {} from database.", channelId);
        ChannelEntity channelEntity = new ChannelEntity();
        channelEntity.setId(channelId);

        List<VideoEntity> videoEntities = new ArrayList<>();
        videosInfoRepository.findAllByChannelId(channelEntity)
                .forEach(videoEntities::add);

        if (videoEntities.isEmpty()) {
            VideosNotFoundException exception = new VideosNotFoundException(channelId);
            log.error(VIDEOS_NOT_FOUND, exception);
            throw exception;
        }

        log.info("Information about videos of channel with id = {} was loaded from database.", channelId);
        return videoMapper.videoEntitiesToVideos(videoEntities);
    }

    @Override
    public List<String> getChannelIds() {
        log.info("Loading channel ids from database...");
        List<String> channelIds = channelRepository.findAll()
                .stream()
                .map(ChannelIdEntity::getId)
                .collect(Collectors.toList());
        log.info("Channel ids from database was loaded (count = {}).", channelIds.size());
        return channelIds;
    }

    @Override
    @Transactional
    public void saveChannel(Channel channel, List<Video> videos) {
        log.info("Saving report to the database (id = {}, videos count: {}).", channel.getId(), videos.size());
        ChannelEntity channelEntity = channelMapper.channelToChannelEntity(channel);
        List<VideoEntity> videoEntities = videoMapper.videosToVideoEntities(videos);

        videoEntities.forEach(videoEntity -> videoEntity.setChannelId(channelEntity));
        channelEntity.setVideoEntities(new HashSet<>(videoEntities));

        channelsInfoRepository.save(channelEntity);
        log.info("Report was saved to the database (id = " + channel.getId() + ", videos count: " + videos.size() + ").");
    }
}
