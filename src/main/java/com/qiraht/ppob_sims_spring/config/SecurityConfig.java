package com.qiraht.ppob_sims_spring.config;

import com.qiraht.ppob_sims_spring.exception.handler.UnAuthenticationHandler;
import com.qiraht.ppob_sims_spring.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    private final UnAuthenticationHandler unAuthenticationHandler;
    private final JwTAuthFilter jwtAuthFilter;

    public SecurityConfig(CustomUserDetailsService userDetailsService, UnAuthenticationHandler unAuthenticationHandler, JwTAuthFilter jwtAuthFilter) {
        this.userDetailsService = userDetailsService;
        this.unAuthenticationHandler = unAuthenticationHandler;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .userDetailsService(userDetailsService).authorizeHttpRequests(auth ->
                        auth.requestMatchers(
                                        "/v3/api-docs/**",
                                        "/swagger-ui.html",
                                        "/swagger-ui/**"
                                ).permitAll()
                                .requestMatchers(HttpMethod.GET, "/banner").permitAll()
                                .requestMatchers(HttpMethod.POST, "/login", "/registration").permitAll()
                                .anyRequest().authenticated())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(unAuthenticationHandler))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
