package com.kostenko.youtube.analytic.service.mapper.youtube.analytic;

import com.kostenko.youtube.analytic.service.entity.VideoEntity;
import com.kostenko.youtube.analytic.service.model.youtube.analytic.Video;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-28T00:34:21+0200",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.jar, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
@Primary
public class YoutubeVideoMapperImpl extends YoutubeVideoDecorator implements YoutubeVideoMapper {

    private final YoutubeVideoMapper delegate;

    @Autowired
    public YoutubeVideoMapperImpl(@Qualifier("delegate") YoutubeVideoMapper delegate) {

        this.delegate = delegate;
    }

    @Override
    public List<VideoEntity> videosToVideoEntities(List<Video> videos)  {
        return delegate.videosToVideoEntities( videos );
    }

    @Override
    public VideoEntity videoToVideoEntity(Video video)  {
        return delegate.videoToVideoEntity( video );
    }
}
