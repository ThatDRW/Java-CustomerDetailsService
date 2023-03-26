package com.thatdrw.customerdetailsservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

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
        http.authorizeHttpRequests((authz) -> authz
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


        // http        
        //     .headers().frameOptions().disable() // New Line: the h2 console runs on a "frame". By default, Spring Security prevents rendering within an iframe. This line disables its prevention.
        //     .and()
        //     .csrf().disable()
        //     .authorizeHttpRequests()  
        //     .requestMatchers(HttpMethod.GET, "/v3/**").permitAll() // New Line: allows us to access the v3 API documentation without the need to authenticate. ' ** '  instead of ' * ' because multiple path levels will follow /v3.
        //     .requestMatchers("/swagger-ui/**").permitAll() // New Line: allows us to access the swagger-ui API documentation UI without the need to authenticate. ' ** '  instead of ' * ' because multiple path levels will follow /swagger-ui.
        //     .requestMatchers(HttpMethod.GET, "/h2/**").permitAll() // New Line: allows us to access the h2 console without the need to authenticate. ' ** '  instead of ' * ' because multiple path levels will follow /h2.
        //     .requestMatchers(HttpMethod.POST, "/h2/**").permitAll() // New Line: allows us to access the h2 console without the need to authenticate. ' ** '  instead of ' * ' because multiple path levels will follow /h2.
        //     .requestMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH).permitAll()
        //     .anyRequest().authenticated()
        //     .and()
        //     .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
        //     .addFilter(authenticationFilter)
        //     .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
        //     .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
