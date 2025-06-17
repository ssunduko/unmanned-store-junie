package com.unmannedstore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class for Swagger/OpenAPI documentation.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configures the OpenAPI documentation for the application.
     *
     * @return The OpenAPI configuration
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Unmanned Store API")
                        .description("REST API for autonomous retail store operations including customer management, authentication, shopping, checkout, and robotic assistance")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Store Operations")
                                .email("api@unmannedstore.com")))
                .servers(List.of(
                        new Server().url("/").description("Default Server URL")
                ));
    }
}