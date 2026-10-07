package com.aura.godeliver.security;

import com.aura.godeliver.exception.AccessTokenExpiredException;
import com.aura.godeliver.exception.InvalidAccessTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final JwtAuthenticationFailureHandler failureHandler;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authorizationHeader =
                request.getHeader("Authorization");

        // No Authorization header
        if (authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);

        try {

            String userId = jwtService.extractUserId(token);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userId,
                            null,
                            Collections.emptyList()
                    );

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException exception) {

            SecurityContextHolder.clearContext();

            failureHandler.handle(
                    response,
                    new AccessTokenExpiredException()
            );

        } catch (JwtException | IllegalArgumentException exception) {

            SecurityContextHolder.clearContext();

            failureHandler.handle(
                    response,
                    new InvalidAccessTokenException()
            );
        }
    }
}