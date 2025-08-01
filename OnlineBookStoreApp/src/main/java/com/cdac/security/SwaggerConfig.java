package com.cdac.security;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)

@OpenAPIDefinition(info = @Info(title = "Online Book Store", version = "v1.0"), security = {

        /* @SecurityRequirement(name = "basicAuth") , */
        @SecurityRequirement(name = "bearerAuth") }
)
@Configuration
public class SwaggerConfig {
}
