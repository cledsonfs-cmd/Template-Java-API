package com.ce.template.security.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                .title("Template API - Template")
                .description("Documentação da API para o sistema Template")
                .version("1.0.0")
                .contact(new Contact()
                .name("Seu Nome")
                .email("seu@email.com")));
        }
    }
