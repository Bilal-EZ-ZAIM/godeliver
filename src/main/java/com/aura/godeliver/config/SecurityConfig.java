package com.aura.godeliver.config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aura.godeliver.security.JwtAuthenticationFilter;
import com.aura.godeliver.security.JwtAuthenticationEntryPoint;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

        @Bean
        public SecurityFilterChain securityFilterChain(
                        HttpSecurity http) throws Exception {

                http
                                .csrf(csrf -> csrf.disable())

                                .sessionManagement(session -> session.sessionCreationPolicy(
                                                SessionCreationPolicy.STATELESS))

                                .authorizeHttpRequests(auth -> auth

                                                // Public endpoints
                                                .requestMatchers(
                                                                "/api/v1/auth/**",
                                                                "/swagger-ui/**",
                                                                "/v3/api-docs/**")
                                                .permitAll()

                                                // Everything else requires authentication
                                                .anyRequest()
                                                .authenticated())

                                // Authentication errors → 401
                                .exceptionHandling(exception -> exception.authenticationEntryPoint(
                                                jwtAuthenticationEntryPoint))

                                .addFilterBefore(
                                                jwtAuthenticationFilter,
                                                UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        
}