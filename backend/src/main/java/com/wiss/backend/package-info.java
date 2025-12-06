/**
 * <h2>
 *     Backend-Grundstruktur der EONET Earth-Natural-Events-Tracker-Applikation
 * </h2>
 * <p>
 *     Dieses Package stellt die Hauptstruktur des Backends dar und bildet die technische Basis
 *     für alle Submodule wie Controller, Service, Repository, Exception-Handling und DTOs.
 *     Es implementiert eine REST-API zur Verwaltung von Naturereignissen basierend auf der
 *     NASA-EONET-Datenstruktur.
 * </p>
 *
 * <h3>
 *     Architekturübersicht:
 * </h3>
 * <ul>
 *     <li><b>{@link com.wiss.backend.controller.EventController Controller}</b> – REST-Endpunkte für CRUD, Filterung und Formulare</li>
 *     <li><b>{@link com.wiss.backend.service.EventService Service}</b> – Geschäftslogik und Datenvalidierung</li>
 *     <li><b>{@link com.wiss.backend.repository.EventRepository Repository}</b> – JPA-Schnittstelle zur Datenbank</li>
 *     <li><b>{@link com.wiss.backend.entity Entity}</b> – JPA-Entitäten</li>
 *     <li><b>{@link com.wiss.backend.dto DTO}</b> – Datenübertragungsobjekte für API und Fehlerstruktur</li>
 *     <li><b>{@link com.wiss.backend.exception Exception}</b> – Globale Fehlerbehandlung</li>
 *     <li><b>{@link com.wiss.backend.mapper.EventMapper Mapper}</b> – Umwandlung zwischen Entität und DTO</li>
 *     <li><b>{@link com.wiss.backend.model Model}</b> – Domänenmodelle (Enums für Kategorie und Status)</li>
 * </ul>
 *
 * <h3>
 *     Technologien:
 * </h3>
 * <ul>
 *     <li>Spring Boot</li>
 *     <li>Spring Web (REST)</li>
 *     <li>Spring Data JPA</li>
 *     <li>Bean Validation</li>
 *     <li>OpenAPI (Swagger)</li>
 *     <li>Jackson für JSON-Verarbeitung</li>
 * </ul>
 *
 * <h3>
 *     Projektziel:
 * </h3>
 * <p>
 *     Das Projekt dient dem Erfassen, Verwalten und Auswerten von Naturereignissen durch Forscher:innen
 *     und interessierte Nutzer:innen. Es unterstützt verschiedene Filtermöglichkeiten und
 *     ein strukturiertes Fehler-Handling.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-20
 */
package com.wiss.backend;