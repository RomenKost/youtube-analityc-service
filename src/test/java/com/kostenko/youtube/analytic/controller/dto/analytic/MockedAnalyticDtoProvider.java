package com.kostenko.youtube.analytic.controller.dto.analytic;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.kostenko.youtube.analytic.util.TestConstants.*;

@UtilityClass
public class MockedAnalyticDtoProvider {
    public AnalyticChannelDto getMockedAnalyticChannelDto() {
        AnalyticChannelDto channelDto = new AnalyticChannelDto();
        channelDto.setId(TEST_CHANNEL_ID);
        channelDto.setCountry(TEST_COUNTRY);
        channelDto.setDescription(TEST_TEXT_FIELD);
        channelDto.setTitle(TEST_TEXT_FIELD);
        channelDto.setPublishedAt(TEST_DATE);
        return channelDto;
    }

    public AnalyticVideoDto getMockedAnalyticVideoDto() {
        AnalyticVideoDto youtubeAnalyticVideoDto = new AnalyticVideoDto();
        youtubeAnalyticVideoDto.setVideoId(TEST_VIDEO_ID);
        youtubeAnalyticVideoDto.setDescription(TEST_TEXT_FIELD);
        youtubeAnalyticVideoDto.setPublishedAt(TEST_DATE);
        youtubeAnalyticVideoDto.setTitle(TEST_TEXT_FIELD);
        return youtubeAnalyticVideoDto;
    }

    public List<AnalyticVideoDto> getMockedAnalyticVideoDtoList() {
        return getMockedAnalyticVideoDtoList(3);
    }

    public List<AnalyticVideoDto> getMockedAnalyticVideoDtoList(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> getMockedAnalyticVideoDto())
                .collect(Collectors.toList());
    }
}
