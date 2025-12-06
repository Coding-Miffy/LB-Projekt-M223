/**
 * <h2>
 *     Controller-Layer
 * </h2>
 * <p>
 *     Dieses Package enthält alle REST-Endpunkte zur Verwaltung von Naturereignissen im Rahmen
 *     des NASA EONET Event-Trackers. Die Controller dienen als Schnittstelle zwischen Client-Anfragen
 *     (z. B. Frontend) und der zugrunde liegenden Geschäftslogik im Service-Layer.
 * </p>
 *
 * <h3>
 *     Enthaltene Controller:
 * </h3>
 * <ul>
 *     <li>{@link com.wiss.backend.controller.EventController} – Hauptcontroller für CRUD, Filter und Formulare</li>
 * </ul>
 *
 * <h3>
 *     Verwendete Technologien:
 * </h3>
 * <ul>
 *     <li>Spring Web (REST)</li>
 *     <li>Bean Validation (@Valid)</li>
 *     <li>Swagger/OpenAPI für API-Dokumentation</li>
 *     <li>Globale Fehlerbehandlung via {@code @ControllerAdvice}</li>
 * </ul>
 *
 * <h3>
 *     Fehlerverarbeitung:
 * </h3>
 * <p>
 *     Alle auftretenden Fehler werden vom zentralen {@link com.wiss.backend.exception.GlobalExceptionHandler}
 *     verarbeitet und im standardisierten {@link com.wiss.backend.dto.ErrorResponseDTO} Format an den Client zurückgegeben.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-20
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