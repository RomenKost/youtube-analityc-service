package com.kostenko.youtube.analytic.security.encoder.impl;

import com.kostenko.youtube.analytic.security.encoder.PasswordHashEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class Base64PasswordHashEncoder implements PasswordHashEncoder {
    @Override
    public String encode(String hash) {
        return new String(Base64.getDecoder().decode(hash.getBytes(StandardCharsets.UTF_8)));
    }
}
