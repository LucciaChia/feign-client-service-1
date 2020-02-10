package com.example.country;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class WrapperApplication {

    public static void main(String[] args) {
        SpringApplication.run(WrapperApplication.class, args);

        System.out.println(" *********************************** Hello Lucinka *********************************** ");
    }

}
