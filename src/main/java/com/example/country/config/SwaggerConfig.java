package com.example.country.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2).select()
//                .apis(RequestHandlerSelectors.basePackage("com.example.country.controllers"))
//                .paths(PathSelectors.ant("/countries/country/*"))
//                .build()
//                .apiInfo(apiInfo())
//                .useDefaultResponseMessages(false)
//                .globalResponseMessage(RequestMethod.GET, newArrayList(new ResponseMessageBuilder().code(500)
//                                .message("500 message")
//                                .responseModel(new ModelRef("Error"))
//                                .build(),
//                        new ResponseMessageBuilder().code(403)
//                                .message("Forbidden!!!!!")
//                                .build()));
//    }
//
//    private ApiInfo apiInfo() {
//        ApiInfo apiInfo = new ApiInfo("My REST API", "Some custom description of API.", "API TOS", "Terms of service", new Contact("John Doe", "www.example.com", "myeaddress@company.com"), "License of API", "API license URL", Collections.emptyList());
//        return apiInfo;
//    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.ant("/countries/country/*")).build()
                .apiInfo(new ApiInfoBuilder()
                        .title("Country project")
                        .version("0.1.1-SNAPSHOT")
                        .build())
                .host("localhost:8088")
                .useDefaultResponseMessages(false);
    }

    @Override
    // enabling swagger-ui
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
