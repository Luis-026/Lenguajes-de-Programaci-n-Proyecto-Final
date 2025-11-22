package com.example.panaderia.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir:uploads/productos}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Obtener la ruta absoluta del directorio del proyecto
        String projectPath = System.getProperty("user.dir");
        String fullUploadPath = "file:" + projectPath + "/" + uploadDir + "/";

        // Configurar para servir las imágenes desde la URL /uploads/**
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(fullUploadPath);
    }
}