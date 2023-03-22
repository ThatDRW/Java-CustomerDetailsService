package com.thatdrw.customerdetailsservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI openApi() {
        return new OpenAPI()
            .info(new Info()
            .title("CustomerDetailsService API")
            .description("An API that can manage Customer Details.")
            .version("v0.0.1-SNAPSHOT"));
    }
}