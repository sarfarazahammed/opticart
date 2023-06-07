package com.sarfaraz.opticart.security.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarfaraz.opticart.commons.helper.ExceptionHelper;
import com.sarfaraz.opticart.security.auth.jwt.extractor.TokenExtractor;
import com.sarfaraz.opticart.security.commons.JwtProperties;
import com.sarfaraz.opticart.security.commons.JwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.sarfaraz.opticart.security.commons.constants.JwtConstants.JWT_TOKEN_HEADER_PARAM;

@Slf4j
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final TokenExtractor tokenExtractor;
    private final ExceptionHelper exceptionHelper;
    private final JwtProperties jwtProperties;


    public JwtAuthenticationFilter(TokenExtractor tokenExtractor,
                                   JwtProperties jwtProperties,
                                   RequestMatcher matcher, ExceptionHelper exceptionHelper) {
        super(matcher);
        this.tokenExtractor = tokenExtractor;
        this.jwtProperties = jwtProperties;
        this.exceptionHelper = exceptionHelper;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {

        try {
            String bearerToken = tokenExtractor.extract(request.getHeader(JWT_TOKEN_HEADER_PARAM));
            return getAuthenticationManager().authenticate(new JwtAuthenticationToken(JwtUtil.validateToken(bearerToken, jwtProperties)));
        } catch (JwtException ex) {
            log.error("Exception in JWT: {}", ex.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType("application/json");
            ObjectMapper objectMapper = new ObjectMapper();
            String errorResponse = objectMapper.writeValueAsString(exceptionHelper.getErrorResponse(request, ex, HttpStatus.BAD_REQUEST));
            response.getWriter().write(errorResponse);
        } catch (Exception ex) {
            log.error("Unhandled exception: {}", ex.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json");
            ObjectMapper objectMapper = new ObjectMapper();
            String errorResponse = objectMapper.writeValueAsString(exceptionHelper.getErrorResponse(request, ex, HttpStatus.INTERNAL_SERVER_ERROR));
            response.getWriter().write(errorResponse);
        }
        return null;

    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        //create the context and set the auth resultes (user context)
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
