package br.restaurante.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
		Path uploadDir = Paths.get("uploads").toAbsolutePath().normalize();
		// Usa file:// para garantir que funcione em containers Docker
		String location = "file:" + uploadDir.toString().replace("\\", "/") + "/";
		registry.addResourceHandler("/uploads/**")
			.addResourceLocations(location);
	}
}


