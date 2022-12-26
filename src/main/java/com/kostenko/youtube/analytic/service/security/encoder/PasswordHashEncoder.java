package com.kostenko.youtube.analytic.service.security.encoder;

public interface PasswordHashEncoder {
    String encode(String hash);
}
