package com.sarfaraz.opticart.security.auth.jwt;

import com.sarfaraz.opticart.security.auth.context.AppUserContext;
import com.sarfaraz.opticart.security.commons.token.AccessToken;
import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


@EqualsAndHashCode(callSuper = false)
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 2877954820905567501L;

    private AccessToken rawAccessToken;
    private AppUserContext appUserContext;

    public JwtAuthenticationToken(AccessToken unsafeToken) {
        super(null);
        this.rawAccessToken = unsafeToken;
        this.setAuthenticated(false);
    }

    public JwtAuthenticationToken(AppUserContext appUserContext, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.eraseCredentials();
        this.appUserContext = appUserContext;
        super.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return rawAccessToken;
    }

    @Override
    public Object getPrincipal() {
        return this.appUserContext;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.rawAccessToken = null;
    }
}
