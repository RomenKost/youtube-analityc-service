package com.kostenko.youtube.analytic.model;

import com.kostenko.youtube.analytic.model.security.YoutubeAnalyticJwt;
import com.kostenko.youtube.analytic.model.security.YoutubeAnalyticUser;
import com.kostenko.youtube.analytic.model.youtube.Channel;
import com.kostenko.youtube.analytic.model.youtube.Video;
import com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserStatus;
import com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserRole;
import lombok.experimental.UtilityClass;

import java.util.Date;
import java.util.List;

@UtilityClass
public class Models {
    public List<Video> getVideos() {
        Video firstVideo = getVideo();
        Video secondVideo = new Video(
                "another id",
                "another title",
                "another description",
                new Date(1_700_000_000_000L)
        );
        return List.of(firstVideo, secondVideo);
    }

    public Channel getChannel() {
        return new Channel(
                "any id",
                "any title",
                "any description",
                "ac",
                new Date(1_600_000_000_000L)
        );
    }

    public Video getVideo() {
        return new Video(
                "any id",
                "any title",
                "any description",
                new Date(1_600_000_000_000L)
        );
    }

    public YoutubeAnalyticUser getUser(YoutubeAnalyticUserRole userRole, YoutubeAnalyticUserStatus userStatus) {
        return new YoutubeAnalyticUser(
                "any username",
                "any password",
                userRole,
                userStatus
        );
    }

    public YoutubeAnalyticJwt getJwt(YoutubeAnalyticUserRole userRole) {
        return new YoutubeAnalyticJwt(
                "any token",
                "any username",
                12345,
                userRole
        );
    }
}
