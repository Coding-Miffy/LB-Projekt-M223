/**
 * <h2>
 *     Sicherheitsschicht der Anwendung (JWT Security Layer)
 * </h2>
 *
 * <p>
 *     Dieses Paket enthält sicherheitsrelevante Komponenten, die für die
 *     Authentifizierung und Autorisierung innerhalb der Anwendung zuständig sind.
 *     Der Fokus liegt auf einer vollständig stateless implementierten
 *     JWT-Authentifizierung, die von Spring Security verwaltet wird.
 * </p>
 *
 * <h3>Enthaltene Komponenten:</h3>
 * <ul>
 *     <li>{@link com.wiss.backend.security.JwtAuthenticationFilter} –
 *         Ein globaler Security-Filter, der jeden eingehenden Request abfängt,
 *         den übergebenen JWT validiert und – falls gültig – den Benutzer im
 *         {@code SecurityContext} von Spring Security authentifiziert.</li>
 * </ul>
 *
 * <h3>Aufgaben dieses Pakets:</h3>
 * <ul>
 *     <li>Auslesen und Validieren von Bearer-Tokens</li>
 *     <li>Extrahieren des Usernamens aus dem JWT</li>
 *     <li>Laden der Benutzerinformationen über den {@code UserDetailsService}</li>
 *     <li>Setzen der Authentifizierung im {@code SecurityContextHolder}</li>
 *     <li>Unterstützung einer vollständig sessionlosen Authentifizierung
 *         (STATELESS-Architektur)</li>
 * </ul>
 *
 * <p>
 *     Dieses Paket arbeitet eng mit der JWT-Logik aus
 *     {@link com.wiss.backend.service.JwtService} sowie der globalen
 *     Sicherheitskonfiguration aus
 *     {@link com.wiss.backend.config.SecurityConfig} zusammen.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-12-12
 */
package com.wiss.backend.security;