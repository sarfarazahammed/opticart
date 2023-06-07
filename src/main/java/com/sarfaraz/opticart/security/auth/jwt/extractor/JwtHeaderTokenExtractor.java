package com.sarfaraz.opticart.security.auth.jwt.extractor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.common.annotations.VisibleForTesting;
import com.sarfaraz.opticart.security.commons.JwtProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class JwtHeaderTokenExtractor implements TokenExtractor {

    private final JwtProperties jwtProperties;

    public JwtHeaderTokenExtractor(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public String extract(String header) {
        if (StringUtils.isBlank(header)) {
            throw new AuthenticationServiceException("Authorization header cannot be blank!");
        }

        if (header.length() < jwtProperties.getActiveTokenType().getPrefix().length()) {
            throw new AuthenticationServiceException("Invalid authorization header size.");
        }

        if (isMultipleTokenInHeader(header)) {
            return getOpticartJwtTokenFromHeader(header);
        } else {
            return header.substring(jwtProperties.getActiveTokenType().getPrefix().length());
        }
    }

    private String getOpticartJwtTokenFromHeader(String multipleBearerHeader) {
        String result = "";

        boolean found = false;

        String[] arr = multipleBearerHeader.split(",");

        for (int i = 0; i < arr.length && !found; i++) {
            String tmpStr = arr[i].trim().substring(jwtProperties.getActiveTokenType().getPrefix().length());
            if (isOpticartJwtToken(tmpStr)) {
                found = true;
                result = tmpStr;
            }
        }

        if (found) {
            return result;
        } else {
            throw new AuthenticationServiceException("Opticart token not found in " + multipleBearerHeader);
        }

    }

    @VisibleForTesting
    private boolean isMultipleTokenInHeader(String header) {
        return header.contains(",");
    }

    /*
     * see APP-10228
     */
    @VisibleForTesting
    private boolean isOpticartJwtToken(String base64DecodedJWTToken) {

        try {

            String s = base64DecodedJWTToken.split("[.]")[1];
            String jsonStrOfClaimsPart = new String(Base64.getDecoder().decode(s));
            ObjectMapper mapper = new JsonMapper();
            JsonNode json = mapper.readTree(jsonStrOfClaimsPart);
            return json.get("iss").asText().contains(jwtProperties.getTokenIssuer());

        } catch (Exception e) {
            throw new AuthenticationServiceException(e.getMessage());
        }

    }
}
