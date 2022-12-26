package com.kostenko.youtube.analytic.model.security.user;

import lombok.Data;

@Data
public class User {
    private String username;
    private String password;
    private UserRole role;
    private UserStatus status;
}
