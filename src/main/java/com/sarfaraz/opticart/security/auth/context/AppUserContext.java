package com.sarfaraz.opticart.security.auth.context;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
public class AppUserContext implements Serializable {
    private static final long serialVersionUID = 2405172041950251809L;
    private final String userId;
    private final List<GrantedAuthority> authorities;
}
