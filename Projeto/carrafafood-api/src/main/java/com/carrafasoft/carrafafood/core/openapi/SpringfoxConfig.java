package com.carrafasoft.carrafafood.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringfoxConfig {

    @Bean
    public Docket apiDoket() {

        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.carrafasoft.carrafafood.api"))
                .build()
                .apiInfo(apiInfo());
    }

    public ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("CarrafaFood API")
                .description("Api de estudos da Algaworks para clientes e restaurantes")
                .version("1.0")
                .contact(new Contact("CarrafaFood", "http://localhost:8080", "lcarrafa.br@gmail.com"))
                .build();
    }
}
