package com.sarfaraz.opticart.security.config;

import com.sarfaraz.opticart.commons.helper.ExceptionHelper;
import com.sarfaraz.opticart.security.auth.jwt.JwtAuthenticationFilter;
import com.sarfaraz.opticart.security.auth.jwt.SkipPathRequestMatcher;
import com.sarfaraz.opticart.security.auth.jwt.extractor.TokenExtractor;
import com.sarfaraz.opticart.security.commons.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.sarfaraz.opticart.security.commons.constants.JwtAuthSkipUrls.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {


    private final TokenExtractor tokenExtractor;
    private final JwtProperties jwtProperties;
    private final AuthenticationProvider jwtAuthenticationProvider;
    private final ExceptionHelper exceptionHelper;

    private final String[] pathsToSkip = {SIGNUP, SIGNUP_VALIDATE, TOKENS, TOKENS_REFRESH, PASSWORD_RECOVERY,
            PASSWORD_RECOVERY_CODE_VALIDATE, PASSWORD_CHANGE, READY, HEALTHZ};

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(jwtAuthenticationProvider);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {


        http
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers(pathsToSkip).permitAll()
                .antMatchers(HttpMethod.GET, PRESCRIPTION_TYPE).permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(authManager), UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
        return http.build();
    }

    private JwtAuthenticationFilter buildJwtTokenAuthenticationProcessingFilter(AuthenticationManager authenticationManager) {
        Assert.noNullElements(pathsToSkip, "Skip list must one element to enter into app");
        List<RequestMatcher> m = Arrays.stream(pathsToSkip).map(AntPathRequestMatcher::new).collect(Collectors.toList());
        m.add(new AntPathRequestMatcher(PRESCRIPTION_TYPE, HttpMethod.GET.name()));
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(m);
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(tokenExtractor, jwtProperties, matcher, exceptionHelper);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        return jwtAuthenticationFilter;
    }

}
