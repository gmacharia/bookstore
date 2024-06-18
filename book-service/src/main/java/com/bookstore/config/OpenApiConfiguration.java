/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.config;

import org.springframework.context.annotation.Configuration;

/**
 *
 * @author kobe
 */
@Configuration
/*@OpenAPIDefinition(info = @Info(title = "Book API Documentation",
        description = "This documentation shows how to integrate to the book crud API.",
        version = "v1"))*/
public class OpenApiConfiguration {

    /*@Bean
    public OpenAPI customizeOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }*/
}
