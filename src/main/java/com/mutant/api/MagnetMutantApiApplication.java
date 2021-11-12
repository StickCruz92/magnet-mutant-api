package com.mutant.api;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class MagnetMutantApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MagnetMutantApiApplication.class, args);
	}
	
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.mutant.api.rest"))
                .build().useDefaultResponseMessages(false).apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("MUTANT Rest", "Documentación de las Apis Rest de magneto", "1.0",
                "Visita https://example.com/terms", new Contact("Magneto S.A", "www.magneto.com.co", "stick.cruz1992@gmail.com"),
                "License", "www.magneto.com.co/license", Collections.emptyList());
    }

}
