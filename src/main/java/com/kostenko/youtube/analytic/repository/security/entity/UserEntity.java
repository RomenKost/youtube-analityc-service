package com.kostenko.youtube.analytic.repository.security.entity;

import com.kostenko.youtube.analytic.model.security.user.UserStatus;
import com.kostenko.youtube.analytic.model.security.user.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

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
    private UserRole role;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @UpdateTimestamp
    @Column(name = "updated")
    private Date updated;

    @CreationTimestamp
    @Column(name = "created")
    private Date created;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return username.equals(that.username) && password.equals(that.password) && role == that.role && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, role, status);
    }
}
