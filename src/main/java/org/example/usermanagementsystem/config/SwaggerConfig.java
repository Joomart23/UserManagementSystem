package org.example.usermanagementsystem.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {
    private static final String API_KEY = "JWT";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .addServersItem(new Server().url("http://localhost:8087").description("Production Server"))
            .addServersItem(new Server().url("http://62.171.147.215:8087").description("Local Development Server"))
                .components(new Components()
                        .addSecuritySchemes(API_KEY, apiKeySecurityScheme()))
                .info(new Info().title("AistCargo Api").description("Api Documentation for AistCargo"))
                .security(Collections.singletonList(new SecurityRequirement().addList(API_KEY)));
    }

    public SecurityScheme apiKeySecurityScheme() {
        return new SecurityScheme()
                .name("Authorization")
                .description("Please put the token")
                .in(SecurityScheme.In.HEADER)
                .type(SecurityScheme.Type.HTTP)
                .scheme("Bearer")
                .bearerFormat("JWT");
    }
}
