package br.com.template.tools;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.Collections;
import java.util.List;

@Configuration
@OpenAPIDefinition()
public class SwaggerConfig {
    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("Cledson Francisco Silva");
        myContact.setEmail("cledsonfs@gmail.com");

        Info information = new Info()
                .title("Api Java - Template Base")
                .version("1.0")
                .description("Templeta basico para desenvolvimento de APIs em Java.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
