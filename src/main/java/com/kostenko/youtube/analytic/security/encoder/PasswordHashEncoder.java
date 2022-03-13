package com.kostenko.youtube.analytic.security.encoder;

public interface PasswordHashEncoder {
    String encode(String hash);
}
