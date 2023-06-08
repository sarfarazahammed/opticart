package com.sarfaraz.opticart.security.auth.jwt;

import com.sarfaraz.opticart.security.auth.context.AppUserContext;
import com.sarfaraz.opticart.security.commons.JwtProperties;
import com.sarfaraz.opticart.security.commons.JwtUtil;
import com.sarfaraz.opticart.security.commons.model.JwtUser;
import com.sarfaraz.opticart.security.commons.token.AccessToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtProperties jwtProperties;
    private final JwtFacade jwtFacade;

    public JwtAuthenticationProvider(JwtProperties jwtProperties,
                                     JwtFacade jwtFacade) {
        this.jwtProperties = jwtProperties;
        this.jwtFacade = jwtFacade;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AccessToken accessToken = (AccessToken) authentication.getCredentials();
        if (!jwtFacade.isValidAccessToken(accessToken.getToken()))
            throw new InsufficientAuthenticationException("Token is invalid");
        //Validate and extract from access token
        JwtUser jwtUser = JwtUtil.validateAndExtractToken(accessToken.getToken(), jwtProperties);

        List<GrantedAuthority> authorities =
                jwtFacade.
                        getAuthorities(jwtUser.getUserId()).
                        stream().
                        map(SimpleGrantedAuthority::new).
                        collect(Collectors.toList());

        if (CollectionUtils.isEmpty(authorities)) {
            throw new InsufficientAuthenticationException("User doesn't have authorities to proceed");
        }

        AppUserContext context = new AppUserContext(jwtUser.getUserId(), authorities);

        return new JwtAuthenticationToken(context, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
