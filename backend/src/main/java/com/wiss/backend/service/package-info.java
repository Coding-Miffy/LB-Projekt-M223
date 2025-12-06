/**
 * <h2>
 *     Service-Layer
 * </h2>
 *
 * <p>
 *     Dieses Paket enthält die zentrale Geschäftslogik der Anwendung.
 *     Die Services kapseln alle Operationen zur Erstellung, Bearbeitung,
 *     Abfrage, Filterung und Löschung von {@link com.wiss.backend.entity.Event}-Entitäten.
 *     Dabei werden die Daten durch Validierungen überprüft und bei Bedarf in
 *     geeignete DTOs ({@link com.wiss.backend.dto.EventDTO}, {@link com.wiss.backend.dto.EventFormDTO}) konvertiert.
 * </p>
 *
 * <h3>
 *     Verantwortlichkeiten:
 * </h3>
 * <ul>
 *     <li>Kommunikation mit dem {@link com.wiss.backend.repository.EventRepository}</li>
 *     <li>Datenvalidierung und Ausnahmebehandlung</li>
 *     <li>Bereitstellung von DTOs für das Frontend</li>
 *     <li>Filterlogik für Kombinationen von Kategorie, Status und Zeiträumen</li>
 * </ul>
 *
 * <h3>
 *     Verwendung:
 * </h3>
 * <p>
 *     Die Service-Klassen werden durch Spring über {@code @Service} verwaltet und
 *     per Dependency Injection in Controllern wie dem
 *     {@link com.wiss.backend.controller.EventController} verwendet.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-20
 *
 * @see com.wiss.backend.service.EventService
 * @see com.wiss.backend.dto.EventDTO
 * @see com.wiss.backend.dto.EventFormDTO
 * @see com.wiss.backend.repository.EventRepository
 * @see com.wiss.backend.controller.EventController
 * @see com.wiss.backend.mapper.EventMapper
 */
package com.wiss.backend.service;