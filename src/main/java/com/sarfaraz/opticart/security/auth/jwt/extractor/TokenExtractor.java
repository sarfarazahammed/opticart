package com.sarfaraz.opticart.security.auth.jwt.extractor;

public interface TokenExtractor {
    String extract(String payload);
}
