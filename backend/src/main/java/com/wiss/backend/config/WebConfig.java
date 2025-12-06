package com.wiss.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <h2>
 *     WebConfig
 * </h2>
 * <p>
 *     Diese Konfigurationsklasse definiert die CORS-Richtlinien (Cross-Origin Resource Sharing)
 *     für REST-Endpunkte der Anwendung. Sie erlaubt gezielt Anfragen vom Frontend,
 *     das z. B. unter <code>http://localhost:5173</code> läuft (z. B. Vite/React).
 * </p>
 * <p>
 *     Die Klasse ist mit {@code @Configuration} annotiert und implementiert {@link WebMvcConfigurer},
 *     wodurch sie Spring Boot erlaubt, zusätzliche Webkonfigurationen einzubinden.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-18
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Konfiguriert die CORS-Mappings für die REST-API.
     * <ul>
     *     <li>Erlaubt Anfragen von <code>http://localhost:5173</code>.</li>
     *     <li>Unterstützt HTTP-Methoden: GET, POST, PUT, DELETE, OPTIONS.</li>
     *     <li>Erlaubt alle Header.</li>
     *     <li>Erlaubt keine Cookies (allowCredentials = false).</li>
     *     <li>Legt die CORS-Konfiguration für <code>/api/events/**</code> fest.</li>
     * </ul>
     *
     * @param registry Die CORS-Registry, in die die Regeln eingetragen werden.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/events/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
    }
}