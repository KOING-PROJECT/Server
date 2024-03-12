package com.koing.server.koing_server.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.Servers;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "KOING API",
                description = "KOING API 문서입니다"
        ),
        servers = {
                @Server(url = "https://koing.store", description = "Default Server Url"),
                @Server(url = "http://localhost:8080", description = "Default Local Server Url")
        }
)
@RequiredArgsConstructor
@Configuration
public class Swagger3Config {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("USER API")
                .pathsToMatch("/**")
                .pathsToExclude("/admin/**")
                .build();
    }

//    @Bean
//    public GroupedOpenApi openApiV2() {
//        return GroupedOpenApi.builder()
//                .group("V2 API")
//                .pathsToMatch("/v2/**")
//                .build();
//    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("ADMIN API")
                .pathsToMatch("/admin/**")
                .build();
    }

    @Bean
    public OpenAPI openAPI(){
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("Authorization");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("Authorization", securityScheme))
                .security(Arrays.asList(securityRequirement));
    }
}
