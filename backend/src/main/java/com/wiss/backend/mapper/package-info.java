/**
 * <h2>
 *     Mapper für Entitäten und DTOs
 * </h2>
 *
 * <p>
 *     Dieses Package enthält Hilfsklassen zur Konvertierung zwischen Domänenobjekten (z. B. {@link com.wiss.backend.entity.Event})
 *     und verschiedenen Data Transfer Objects (DTOs), wie {@link com.wiss.backend.dto.EventDTO} und {@link com.wiss.backend.dto.EventFormDTO}.
 * </p>
 *
 * <h3>
 *     Hauptverantwortung:
 * </h3>
 * <ul>
 *     <li>Trennung der Persistenz- und Präsentationsschicht durch explizite Mapping-Logik</li>
 *     <li>Konvertierung von Eingabedaten in Entitäten zur Weiterverarbeitung im Service-Layer</li>
 *     <li>Umwandlung von Entitäten in DTOs zur Rückgabe an Clients</li>
 * </ul>
 *
 * <h3>
 *     Designhinweis:
 * </h3>
 * <p>
 *     Die Klassen in diesem Package sind <strong>statische Utility-Klassen</strong> ohne Zustandsverwaltung.
 *     Sie behandeln {@code null}-Werte explizit und geben in diesem Fall {@code null} zurück.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-19
 *
 * @see com.wiss.backend.entity.Event
 * @see com.wiss.backend.dto.EventDTO
 * @see com.wiss.backend.dto.EventFormDTO
 */
package com.wiss.backend.mapper;