package com.kostenko.youtubeanalyticservice.service;

import com.kostenko.youtubeanalyticservice.model.YoutubeChannelDto;
import com.kostenko.youtubeanalyticservice.model.YoutubeVideoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YoutubeService {
    private final YoutubeClient client;

    @Autowired
    public YoutubeService(YoutubeClient client) {
        this.client = client;
    }

    public YoutubeChannelDto getChannel(String id) {
        return client.getYoutubeChannelDto(id);
    }

    public List<YoutubeVideoDto> getVideos(String id) {
        return client.getYoutubeVideosDto(id);
    }
}
