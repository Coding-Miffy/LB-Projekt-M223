/**
 * <h2>
 *     Zentrale Konfigurationsklassen der Anwendung
 * </h2>
 *
 * <p>
 *     Dieses Paket bündelt alle globalen Konfigurationen des Spring Boot Backends.
 *     Dazu gehören Sicherheitsregeln, CORS-Einstellungen sowie die automatische
 *     Generierung der OpenAPI/Swagger-Dokumentation. Die Klassen in diesem Paket
 *     steuern somit grundlegende Aspekte des Verhaltens der REST-API.
 * </p>
 *
 * <h3>
 *     Enthaltene Komponenten:
 * </h3>
 * <ul>
 *     <li>{@link com.wiss.backend.config.SecurityConfig} –
 *         Konfiguration von Spring Security, JWT-Authentifizierung und HTTP-Sicherheitsregeln.</li>
 *
 *     <li>{@link com.wiss.backend.config.SwaggerConfig} –
 *         Einrichtung der OpenAPI/Swagger-Dokumentation für alle REST-Endpunkte.</li>
 *
 *     <li>{@link com.wiss.backend.config.WebConfig} –
 *         Globale CORS-Konfiguration für Frontend-Backend-Kommunikation.</li>
 * </ul>
 *
 * <p>
 *     Alle Klassen sind mit {@code @Configuration} annotiert und werden beim
 *     Starten der Anwendung automatisch geladen. Änderungen in diesem Paket
 *     beeinflussen das globale Sicherheits-, Dokumentations- und Kommunikationsverhalten
 *     der API.
 * </p>
 *
 * @author Natascha Blumer
 * @version 2.0
 * @since 2025-12-12
 */
package com.wiss.backend.config;