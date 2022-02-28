package com.kostenko.youtube.analytic.service.entity;

import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "channels_info")
public class ChannelEntity {
    @Id
    @Column(name = "channel_id")
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "country")
    private String country;

    @Column(name = "published_at")
    private Date publishedAt;

    @Setter
    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "channelId", fetch = FetchType.LAZY
    )
    private Set<VideoEntity> videoEntities;

    @UpdateTimestamp
    @Column(name = "last_check")
    private Date lastCheck;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelEntity that = (ChannelEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
