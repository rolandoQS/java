package com.example.java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDateTime;
import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.java.controllers"))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(LocalDateTime.class, String.class)
                .apiInfo(getApiInformation());
    }

    private ApiInfo getApiInformation(){
        return new ApiInfo("Demo REST API",
                "Demo API created using Spring Boot",
                "1.0",
                "API Terms of Service URL",
                new Contact("Rolando", "qwerty.com", "rolandoq.2011@gmail.com"),
                "API License",
                "API License URL",
                Collections.emptyList()
        );
    }
}
