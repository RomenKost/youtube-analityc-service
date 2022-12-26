package com.kostenko.youtube.analytic.service.jwt;

import com.kostenko.youtube.analytic.model.security.jwt.Jwt;

public interface JwtService {
    Jwt getJwt(String username, String password);
}
