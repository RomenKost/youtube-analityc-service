package com.kostenko.youtube.analytic.model.security.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static com.kostenko.youtube.analytic.model.security.user.UserPermission.*;

@Getter
@AllArgsConstructor
public enum UserRole {
    ROLE_CUSTOMER(List.of(FIND_VIDEOS, FIND_CHANNELS));

    private final List<UserPermission> permissions;
}
