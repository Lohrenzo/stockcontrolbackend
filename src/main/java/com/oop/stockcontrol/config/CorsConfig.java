package com.oop.stockcontrol.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(
//                        "http://localhost:5173",
                        "https://stockcontrolfrontend-ks6w0v5l4-lohrenzo.vercel.app/"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
