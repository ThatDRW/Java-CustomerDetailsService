package com.thatdrw.customerdetailsservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import java.util.Arrays;

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
        authenticationFilter.setFilterProcessesUrl("/authenticate");

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests((authz) -> authz
                                .requestMatchers(antMatcher("/h2/**")).permitAll()
                                .requestMatchers(antMatcher("/v3/**")).permitAll() // New Line: allows us to access the v3 API documentation without the need to authenticate. ' ** '  instead of ' * ' because multiple path levels will follow /v3.
                                .requestMatchers(antMatcher("/swagger-ui/**")).permitAll() // New Line: allows us to access the swagger-ui API documentation UI without the need to authenticate. ' ** '  instead of ' * ' because multiple path levels will follow /swagger-ui.
                                .requestMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH).permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                
        http.headers(headers -> headers.frameOptions().disable());
        http.csrf().disable();

        return http.build();
    }

    CorsConfigurationSource corsConfigurationSource() {
        final var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("*"));

        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
