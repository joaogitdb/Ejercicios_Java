package com.ejercicio.ejercicio3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.ejercicio.ejercicio3.config.DataConfig;
import com.ejercicio.ejercicio3.config.BatchConfig;

@SpringBootApplication
@Import({ DataConfig.class, BatchConfig.class })
public class Ejercicio3Application {
    public static void main(String[] args) {
        SpringApplication.run(Ejercicio3Application.class, args);
    }
}
