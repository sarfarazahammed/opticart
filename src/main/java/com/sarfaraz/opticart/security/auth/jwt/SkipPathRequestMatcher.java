package com.sarfaraz.opticart.security.auth.jwt;

import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SkipPathRequestMatcher implements RequestMatcher {
    private final OrRequestMatcher matchers;

    public SkipPathRequestMatcher(List<RequestMatcher> matchersToSkip) {
        matchers = new OrRequestMatcher(matchersToSkip);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return !matchers.matches(request);
    }
}
