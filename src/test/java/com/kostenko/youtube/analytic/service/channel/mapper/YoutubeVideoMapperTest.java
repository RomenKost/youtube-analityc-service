package com.kostenko.youtube.analytic.service.channel.mapper;

import com.kostenko.youtube.analytic.config.MockedDataSourceConfiguration;
import com.kostenko.youtube.analytic.model.youtube.Video;
import com.kostenko.youtube.analytic.repository.youtube.entity.VideoEntity;
import com.kostenko.youtube.analytic.service.channel.mapper.video.YoutubeVideoMapper;
import com.kostenko.youtube.analytic.service.youtube.api.dto.video.YoutubeV3VideoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.kostenko.youtube.analytic.model.youtube.MockedYoutubeModelsProvider.*;
import static com.kostenko.youtube.analytic.repository.youtube.entity.MockedYoutubeEntityProvider.*;
import static com.kostenko.youtube.analytic.service.youtube.api.dto.video.MockedYoutubeV3VideoDtoProvider.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MockedDataSourceConfiguration.class)
class YoutubeVideoMapperTest {
    @Autowired
    private YoutubeVideoMapper videoMapper;

    @Test
    void youtubeV3VideoDtoToVideo() {
        YoutubeV3VideoDto videoDto = getMockedYoutubeV3VideoDto();

        Video expected = getMockedVideo();
        Video actual = videoMapper.youtubeV3VideoDtoToVideo(videoDto);

        assertEquals(expected, actual);
    }

    @Test
    void videoToVideoEntity() {
        Video video = getMockedVideo();

        VideoEntity expected = getMockedVideoEntity();
        expected.setChannelId(null);
        expected.setLastCheck(null);

        VideoEntity actual = videoMapper.videoToVideoEntity(video);

        assertEquals(expected, actual);
    }
}
