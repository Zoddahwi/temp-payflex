package com.fintechedge.payflex.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.accept.RequestedContentTypeResolverBuilder;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebfluxConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(org.springframework.web.reactive.config.CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowedHeaders("Content-Type")
                .allowedHeaders("Access-Control-Allow-Origin")
                .allowedHeaders("Access-Control-Allow-Headers")
                .allowedHeaders("Access-Control-Allow-Methods")
                .allowedHeaders("Access-Control-Allow-Credentials")
                .allowedHeaders("Access-Control-Expose-Headers")
                .allowedHeaders("POST")
                .allowedHeaders("Authorization")
                .exposedHeaders("Authorization")
                .allowCredentials(true);
    }

    @Override
    public void configureContentTypeResolver(RequestedContentTypeResolverBuilder builder) {
       builder.fixedResolver(MediaType.APPLICATION_JSON);
    }
}
