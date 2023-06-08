package com.sarfaraz.opticart.security.commons.constants;

public class JwtConstants {

    public static final String JWT_PROPERTIES_PREFIX = "jwt";
    public static final String JWT_TOKEN_HEADER_PARAM = "X-Auth";
    public static final String JWT_REFRESH_TOKEN_HEADER_PARAM = "X-Auth-Refresh";
    public static final String SCOPES_KEY_NAME = "scopes";
    public static final String ROLE_ACCESS_TOKEN = "ROLE_ACCESS_TOKEN";
    public static final String ROLE_REFRESH_TOKEN = "ROLE_REFRESH_TOKEN";
    private JwtConstants() {
        throw new IllegalStateException("Constants class");
    }
}
