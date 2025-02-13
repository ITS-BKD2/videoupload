package com.ttf.VideoUpload.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Imposta la cartella di storage come una risorsa accessibile via URL
        Path uploadDir = Paths.get("storage");
        String uploadPath = uploadDir.toAbsolutePath().toUri().toString();

        registry.addResourceHandler("/storage/**")
                .addResourceLocations(uploadPath);
    }
}
