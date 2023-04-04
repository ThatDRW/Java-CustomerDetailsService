package com.thatdrw.customerdetailsservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.thatdrw.customerdetailsservice.security.filter.AuthenticationFilter;
import com.thatdrw.customerdetailsservice.security.filter.ExceptionHandlerFilter;
import com.thatdrw.customerdetailsservice.security.filter.JWTAuthorizationFilter;
import com.thatdrw.customerdetailsservice.security.manager.CustomAuthenticationManager;

import lombok.AllArgsConstructor;

import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    
    private final CustomAuthenticationManager customAuthenticationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager);
        authenticationFilter.setFilterProcessesUrl(SecurityConstants.AUTH_PATH);

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.authorizeHttpRequests((authz) -> authz
            .requestMatchers(SecurityConstants.PERMIT_ALL_ROUTES).permitAll()
            .anyRequest().authenticated()
        );
        
        http.addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
            .addFilter(authenticationFilter)
            .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
            .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                
        http.headers(headers -> headers.frameOptions().disable());

        http.csrf().disable();

        return http.build();
    }

    CorsConfigurationSource corsConfigurationSource() {
        final var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(SecurityConstants.CORS_ALLOWED_ORIGINS);
        configuration.setAllowedMethods(SecurityConstants.CORS_ALLOWED_METHODS);
        configuration.setAllowedHeaders(SecurityConstants.CORS_ALLOWED_HEADERS);
        configuration.setExposedHeaders(SecurityConstants.CORS_EXPOSED_HEADERS);

        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(SecurityConstants.CORS_CONFIG_SOURCE_URL, configuration);

        return source;
    }
}
