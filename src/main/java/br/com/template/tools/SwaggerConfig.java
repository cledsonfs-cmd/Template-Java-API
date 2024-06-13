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
//    String schemeName = "bearerAuth";
//    String bearerFormat = "JWT";
//    String scheme = "bearer";
//    @Bean
//    public OpenAPI caseOpenAPI() {
//        return new OpenAPI()
//                .addSecurityItem(new SecurityRequirement()
//                        .addList(schemeName)).components(new Components()
//                        .addSecuritySchemes(
//                                schemeName, new SecurityScheme()
//                                        .name(schemeName)
//                                        .type(SecurityScheme.Type.HTTP)
//                                        .bearerFormat(bearerFormat)
//                                        .in(SecurityScheme.In.HEADER)
//                                        .scheme(scheme)
//                        )
//                )
//                .info(new Info()
//                        .title("Case Management Service")
//                        .description("Claim Event Information")
//                        .version("1.0")
//                );
//    }
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

   /* @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("springshop-public")
                .pathsToMatch("/public/**")
                .build();
    }
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("springshop-admin")
                .pathsToMatch("/admin/**")
                .addOpenApiMethodFilter(method -> method.isAnnotationPresent(Admin.class))
                .build();
    }*/

//    @Bean
//    public OpenAPI springShopOpenAPI() {
//        return new OpenAPI()
//                .info(new Info().title("SpringShop API")
//                        .description("Spring shop sample application")
//                        .version("v0.0.1")
//                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
//                .externalDocs(new ExternalDocumentation()
//                        .description("SpringShop Wiki Documentation")
//                        .url("https://springshop.wiki.github.org/docs"));
//    }

   /* @Bean
    public Docket retornaSwagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.template"))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(informacoesApi());
    }

    private ApiInfo informacoesApi() {
        return new ApiInfo("Api Java - Template Base",
                "Templeta basico para desenvolvimento de APIs em Java",
                "V1", "Api - Template",
                new Contact("Cledson Francisco silva", "http://cledsonfs.com.br/", "cledsonfs@gemail.com"),
                null, null, Collections.emptyList());
    }*/
}
