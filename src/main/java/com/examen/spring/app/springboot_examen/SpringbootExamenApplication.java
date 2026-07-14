package com.examen.spring.app.springboot_examen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

// Clase principal para arrancar el proyecto Spring Boot
@OpenAPIDefinition(
    info = @Info(
        title       = "API Dependencias",
        version     = "1.0",
        description = "API para la gestión de dependencias"
    )
)
@SpringBootApplication
public class SpringbootExamenApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootExamenApplication.class, args);
    }

}
