package com.sarfaraz.opticart.security.commons.constants;

public class JwtAuthSkipUrls {

    public static final String HEALTHZ = "/healthz";
    public static final String READY = "/ready";
    public static final String SIGNUP = "/api/v1/auth/signup";
    public static final String SIGNUP_VALIDATE = "/api/v1/auth/signup/validate";
    public static final String TOKENS = "/api/v1/auth/tokens";
    public static final String TOKENS_REFRESH = "/api/v1/auth/tokens/refresh";
    public static final String PASSWORD_RECOVERY = "/api/v1/auth/password/recovery";
    public static final String PASSWORD_RECOVERY_CODE_VALIDATE = "/api/v1/auth/password/recovery/validate";
    public static final String PASSWORD_CHANGE = "/api/v1/auth/password/change";
    public static final String PRESCRIPTION_TYPE = "/api/v1/user/prescription/type/**";

    private JwtAuthSkipUrls() {
        throw new IllegalStateException("Constants class");
    }

}
