package com.wiss.backend.mapper;

import com.wiss.backend.dto.EventDTO;
import com.wiss.backend.dto.EventFormDTO;
import com.wiss.backend.entity.Event;

import java.util.List;

/**
 * <h2>
 *     Konvertierungslogik für Event-Entitäten und DTOs
 * </h2>
 * <p>
 *     Diese Utility-Klasse enthält statische Methoden zur Konvertierung zwischen {@link Event}-Entitäten
 *     und verschiedenen DTOs wie {@link EventDTO} und {@link EventFormDTO}.
 * </p>
 *
 * <h3>
 *     Verwendung:
 * </h3>
 * <ul>
 *   <li>Konvertierung von Entitäten zu DTOs für API-Antworten</li>
 *   <li>Konvertierung von DTOs zu Entitäten für Service- oder Repository-Schichten</li>
 *   <li>Transformation von DTOs für Formulareingaben und -ausgaben</li>
 * </ul>
 *
 * <h3>
 *     Hinweis:
 * </h3>
 * <p>
 *     Die Mapper-Methoden sind null-sicher – bei {@code null}-Eingabe wird ebenfalls {@code null} zurückgegeben.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-19
 * @see Event
 * @see EventDTO
 * @see EventFormDTO
 */
public class EventMapper {

    /**
     * Konvertiert ein {@link Event}-Objekt in ein {@link EventDTO}.
     *
     * @param entity Die zu konvertierende Entität
     * @return Entsprechendes {@link EventDTO} oder {@code null}, wenn die Eingabe {@code null} ist
     * @see #toEntity(EventDTO) Umgekehrte Konvertierung
     * @see EventDTO Ziel-Klasse
     * @see Event Source-Klasse
     */
    public static EventDTO toDTO(Event entity) {
        if (entity == null) {
            return null;
        }

        return new EventDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getDate(),
                entity.getCategory(),
                entity.getLongitude(),
                entity.getLatitude(),
                entity.getStatus()
        );
    }

    /**
     * Konvertiert ein {@link Event}-Objekt in ein {@link EventFormDTO}.
     *
     * @param entity Die zu konvertierende Entität
     * @return Entsprechendes {@link EventFormDTO} oder {@code null}, wenn die Eingabe {@code null} ist
     * @see EventFormDTO Ziel-Klasse
     * @see Event Source-Klasse
     */
    public static EventFormDTO toFormDTO(Event entity) {
        if (entity == null) {
            return null;
        }

        return new EventFormDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getDate(),
                entity.getCategory(),
                entity.getLongitude(),
                entity.getLatitude(),
                entity.getStatus()
        );
    }

    /**
     * Konvertiert ein {@link EventDTO} in eine {@link Event}-Entität.
     * <p>
     *     Die ID wird dabei nicht gesetzt – dies ist gewollt, da sie bei POST ignoriert und bei PUT separat übergeben wird.
     * </p>
     *
     * @param dto Das DTO mit den Eventdaten
     * @return Entsprechende {@link Event}-Entität oder {@code null}, wenn die Eingabe {@code null} ist
     * @see #toDTO(Event) Umgekehrte Konvertierung
     * @see Event Ziel-Klasse
     * @see EventDTO Source-Klasse
     */
    public static Event toEntity(EventDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Event(
                dto.getTitle(),
                dto.getDate(),
                dto.getCategory(),
                dto.getLongitude(),
                dto.getLatitude(),
                dto.getStatus()
        );
    }

    /**
     * Konvertiert eine Liste von {@link Event}-Entitäten in eine Liste von {@link EventDTO}s.
     *
     * @param entityList Liste der zu konvertierenden Entitäten
     * @return Liste von {@link EventDTO}s, niemals {@code null}
     * @see EventDTO Ziel-Klasse
     * @see Event Source-Klasse
     */
    public static List<EventDTO> toDTOList(List<Event> entityList) {
        return entityList.stream()
                .map(EventMapper::toDTO)
                .toList();
    }

    /**
     * Konvertiert eine Liste von {@link Event}-Entitäten in eine Liste von {@link EventFormDTO}s.
     *
     * @param entityList Liste der zu konvertierenden Entitäten
     * @return Liste von {@link EventFormDTO}s, niemals {@code null}
     * @see EventFormDTO Ziel-Klasse
     * @see Event Source-Klasse
     */
    public static List<EventFormDTO> toFormDTOList(List<Event> entityList) {
        return entityList.stream()
                .map(EventMapper::toFormDTO)
                .toList();
    }
}