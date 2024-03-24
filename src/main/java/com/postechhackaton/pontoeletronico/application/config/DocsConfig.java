package com.postechhackaton.pontoeletronico.application.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocsConfig {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${spring.application.description}")
    private String appDescription;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("⏱️ Postech Hackaton" + appName)
                        .description(appDescription)
                        .contact(new Contact().name("Daniel Maria da Silva").url("https://github.com/postech-hackaton-company-sa"))
                        .license(new License().name("MIT License").url("https://opensource.org/licenses/MIT"))
                        .version("1.0.0"));
    }
}