package com.kostenko.youtube.analytic.entity.security;

import com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserStatus;
import com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private YoutubeAnalyticUserRole role;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private YoutubeAnalyticUserStatus status;

    @UpdateTimestamp
    @Column(name = "updated")
    private Date updated;

    @CreationTimestamp
    @Column(name = "created")
    private Date created;
}
