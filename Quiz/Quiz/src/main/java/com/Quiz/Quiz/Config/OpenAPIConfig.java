package com.Quiz.Quiz.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customerAPI(){
        return new OpenAPI().info(
                new Info()
                        .title("Crud Application")
                        .version("0.1")
                        .description("Swagger for crud APIs")

        );

    }

}
