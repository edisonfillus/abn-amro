package com.abnamro.assessment.shared.security;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import static java.util.function.Predicate.not;

/**
 * Filter responsible to extract the user login token from the HTTP header Authorization
 * In case the login is valid, include the authentication on Spring Security Context
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Pattern BEARER_PATTERN = Pattern.compile("^Bearer (.+?)$");

    private final JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        extractTokenFromRequest(request)
            .map(jwtTokenService::decodeToken)
            .map(user -> JwtPreAuthenticationToken.builder()
                .principal(user)
                .details(new WebAuthenticationDetailsSource().buildDetails(request))
                .build()
            )
            .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));
        filterChain.doFilter(request, response);
    }

    private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
        return Optional
                    .ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                    .filter(not(String::isEmpty))
                    .map(BEARER_PATTERN::matcher)
                    .filter(Matcher::find)
                    .map(matcher -> matcher.group(1));
    }
}
