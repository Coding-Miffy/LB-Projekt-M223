/**
 * <h2>
 *     Service-Layer – Geschäftslogik und Sicherheitsfunktionen
 * </h2>
 *
 * <p>
 *     Dieses Paket enthält die zentralen Service-Klassen der Anwendung.
 *     Sie kapseln die komplette Geschäftslogik rund um Benutzerverwaltung,
 *     Authentifizierung, Favoritenfunktionen und Event-Management.
 *     Alle Services interagieren mit dem Repository-Layer und stellen
 *     validierte, verarbeitete Daten für die Controller bereit.
 * </p>
 *
 * <h3>Enthaltene Komponenten:</h3>
 * <ul>
 *     <li>{@link com.wiss.backend.service.AppUserService} – Registrierung,
 *         Login-Validierung und Benutzerverwaltung.</li>
 *
 *     <li>{@link com.wiss.backend.service.AppUserDetailsService} – Integration
 *         mit Spring Security zum Laden von Benutzerdaten.</li>
 *
 *     <li>{@link com.wiss.backend.service.JwtService} – Erstellung, Analyse und
 *         Validierung von JWT-Tokens für die Authentifizierung.</li>
 *
 *     <li>{@link com.wiss.backend.service.EventService} – Verwaltung,
 *         Filterung und Bereitstellung von Naturereignissen,
 *         inkl. DTO-Konvertierung.</li>
 *
 *     <li>{@link com.wiss.backend.service.EventFavoriteService} – Multi-User-Favoritenlogik,
 *         inkl. Transaktionen für Favoriten-Toggles.</li>
 * </ul>
 *
 * <h3>Verantwortlichkeiten:</h3>
 * <ul>
 *     <li>Kapselung der Geschäftslogik (Business Layer)</li>
 *     <li>Validierung und Verarbeitung von Benutzereingaben</li>
 *     <li>Schnittstelle zwischen Controllern und Repositories</li>
 *     <li>Sicherheitsrelevante Funktionen (JWT, UserDetails)</li>
 *     <li>Transaktionen für konsistente Datenänderungen</li>
 * </ul>
 *
 * <p>
 *     Die Services werden durch Spring mittels {@code @Service} verwaltet und
 *     per Dependency Injection in Controllern wie
 *     {@link com.wiss.backend.controller.EventController} oder
 *     {@link com.wiss.backend.controller.AuthController} verwendet.
 * </p>
 *
 * @author Natascha Blumer
 * @version 2.0
 * @since 2025-12-12
 *
 * @see com.wiss.backend.service.EventService
 * @see com.wiss.backend.dto.EventDTO
 * @see com.wiss.backend.dto.EventFormDTO
 * @see com.wiss.backend.repository.EventRepository
 * @see com.wiss.backend.controller.EventController
 * @see com.wiss.backend.mapper.EventMapper
 */
package com.wiss.backend.service;