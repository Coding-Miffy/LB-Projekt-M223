/**
 * <h2>
 *     Controller-Layer
 * </h2>
 *
 * <p>
 *     Dieses Package enthält alle REST-Controller der Anwendung. Sie bilden die zentrale
 *     Schnittstelle zwischen dem Client (z. B. React-Frontend) und der Geschäftslogik im
 *     Service-Layer. Die Controller validieren Eingaben, rufen Services auf und geben
 *     strukturierte DTOs an den Client zurück.
 * </p>
 *
 * <h3>
 *     Enthaltene Controller:
 * </h3>
 * <ul>
 *     <li>{@link com.wiss.backend.controller.AuthController} –
 *         Verantwortlich für Registrierung, Login, JWT-Generierung und Verfügbarkeitsprüfungen.</li>
 *
 *     <li>{@link com.wiss.backend.controller.EventController} –
 *         Hauptcontroller für Events, inklusive CRUD, Filterabfragen, Form-DTOs und Statistiken.</li>
 *
 *     <li>{@link com.wiss.backend.controller.EventFavoriteController} –
 *         Endpunkte zum Markieren von Favoriten sowie zum Auslesen des Favoritenzählers.</li>
 * </ul>
 *
 * <h3>
 *     Verwendete Technologien:
 * </h3>
 * <ul>
 *     <li>Spring Web – Bereitstellung von REST-Endpunkten</li>
 *     <li>Spring Security – Zugriffskontrolle mittels Rollen und JWT</li>
 *     <li>Bean Validation – Validierung eingehender DTOs</li>
 *     <li>Swagger/OpenAPI – Automatische API-Dokumentation</li>
 * </ul>
 *
 * <h3>
 *     Fehlerverarbeitung:
 * </h3>
 * <p>
 *     Alle Controller werden durch den globalen
 *     {@link com.wiss.backend.exception.GlobalExceptionHandler} überwacht.
 *     Fehler werden einheitlich im Format des
 *     {@link com.wiss.backend.dto.ErrorResponseDTO} zurückgegeben.
 * </p>
 *
 * <h3>
 *     Zugehörige Schichten:
 * </h3>
 * <ul>
 *     <li>{@link com.wiss.backend.service} – enthält die Geschäftslogik</li>
 *     <li>{@link com.wiss.backend.repository} – Datenzugriffsschicht</li>
 *     <li>{@link com.wiss.backend.dto} – Ein- und Ausgabestrukturen der API</li>
 *     <li>{@link com.wiss.backend.entity} – Persistenzmodell</li>
 * </ul>
 *
 * @author Natascha Blumer
 * @version 2.0
 * @since 2025-12-12
 *
 * @see com.wiss.backend.service.EventService
 * @see com.wiss.backend.repository.EventRepository
 * @see com.wiss.backend.model.EventCategory
 * @see com.wiss.backend.model.EventStatus
 * @see com.wiss.backend.entity.Event
 * @see com.wiss.backend.dto.EventDTO
 * @see com.wiss.backend.dto.EventFormDTO
 * @see com.wiss.backend.exception.GlobalExceptionHandler
 */
package com.wiss.backend.controller;