package com.springspeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * SpringSpeed Application Main Class
 * E-commerce Intelligent Recommendation System
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.springspeed")
public class SpringSpeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSpeedApplication.class, args);
        System.out.println("\\n\\n\\n" +
                "╔══════════════════════════════════════════════════════════════╗\\n" +
                "║                  SpringSpeed Recommendation                  ║\\n" +
                "║                    System Started Successfully                ║\\n" +
                "╚══════════════════════════════════════════════════════════════╝\\n" +
                "\\n" +
                "Application is running at: http://localhost:8080/springspeed\\n" +
                "Swagger UI: http://localhost:8080/springspeed/swagger-ui.html\\n" +
                "\\n\\n");
    }
}
