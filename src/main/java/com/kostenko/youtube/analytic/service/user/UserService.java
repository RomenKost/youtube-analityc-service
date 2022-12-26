package com.kostenko.youtube.analytic.service.user;

import com.kostenko.youtube.analytic.model.security.user.User;

public interface UserService {
    User getUser(String username);
}
