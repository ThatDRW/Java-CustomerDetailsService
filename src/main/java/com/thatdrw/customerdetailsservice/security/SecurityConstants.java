package com.thatdrw.customerdetailsservice.security;

import java.util.Arrays;
import java.util.List;

public class SecurityConstants {
    public static final String SECRET_KEY = "bQeThWmZq4t7w!z$C&F)J@NcRfUjXn2r5u8x/A?D*G-KaPdSgVkYp3s6v9y$B&E)"; //Your secret should always be strong (uppercase, lowercase, numbers, symbols) so that nobody can potentially decode the signature.
    public static final int TOKEN_EXPIRATION = 7200000; // 7200000 milliseconds = 7200 seconds = 2 hours.
    public static final String BEARER = "Bearer "; // Authorization : "Bearer " + Token 
    public static final String AUTHORIZATION = "Authorization"; // "Authorization" : Bearer Token
    
    public static final String REGISTER_PATH = "/user/register"; // Public path that clients can use to register.
    public static final String LOGIN_PATH = "/user/login";
    public static final String AUTH_PATH = "/authenticate";
    
    public static final String[] PERMIT_ALL_ROUTES = {"/h2/**", "/v3/**", "/swagger-ui/**", REGISTER_PATH, LOGIN_PATH};
    
    public static final List<String> CORS_ALLOWED_ORIGINS = Arrays.asList("http://localhost:8080", "http://localhost:4200");
    public static final List<String> CORS_ALLOWED_METHODS = Arrays.asList("GET","POST");
    public static final List<String> CORS_ALLOWED_HEADERS = Arrays.asList("*");
    public static final List<String> CORS_EXPOSED_HEADERS = Arrays.asList("*");
    public static final String CORS_CONFIG_SOURCE_URL = "/**";
}