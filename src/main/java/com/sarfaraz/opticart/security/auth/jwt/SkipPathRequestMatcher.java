package com.sarfaraz.opticart.security.auth.jwt;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SkipPathRequestMatcher implements RequestMatcher {
    private OrRequestMatcher matchers;

    public SkipPathRequestMatcher(String[] pathsToSkip) {
        Assert.noNullElements(pathsToSkip, "Skip list must one element to enter into app");
        List<RequestMatcher> m = Arrays.stream(pathsToSkip).map(AntPathRequestMatcher::new).collect(Collectors.toList());
        matchers = new OrRequestMatcher(m);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return !matchers.matches(request);
    }
}
