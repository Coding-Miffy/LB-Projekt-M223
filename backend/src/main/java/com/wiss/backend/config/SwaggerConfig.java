package com.wiss.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h2>
 *     SwaggerConfig
 * </h2>
 * <p>
 *     Konfiguriert Swagger bzw. OpenAPI 3 für die automatische Dokumentation
 *     der REST-API mittels SpringDoc. Die Konfiguration wird beim Start der
 *     Applikation geladen und definiert grundlegende Metadaten für die API.
 * </p>
 * <p>
 *     Die generierte Dokumentation ist über <code>/swagger-ui.html</code> verfügbar.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-18
 */
@Configuration
public class SwaggerConfig {

    /**
     * Erstellt und konfiguriert das OpenAPI-Objekt mit Titel, Version,
     * Beschreibung und Kontaktinformationen.
     *
     * @return Das konfigurierte {@link OpenAPI} Objekt für Swagger UI.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EONET Backend API")
                        .version("1.0.0")
                        .description("REST API für Earth Natural Events Tracker mit CRUD-Operations für Events")
                        .contact(new Contact()
                                .name("Natascha")
                                .email("natascha.blumer@wiss-edu.ch")));
    }
}