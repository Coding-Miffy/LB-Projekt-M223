package com.wiss.backend.dto;

import com.wiss.backend.model.EventCategory;
import com.wiss.backend.model.EventStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * <h2>
 *     EventDTO
 * </h2>
 * <p>
 *     Dieses Data Transfer Object (DTO) repräsentiert ein Naturereignis im System.
 *     Es wird verwendet, um Event-Daten zwischen Client und Server zu übertragen.
 * </p>
 *
 * <h3>
 *     Verwendung:
 * </h3>
 * <ul>
 *   <li>Als Rückgabeobjekt in REST-Endpunkten</li>
 *   <li>Als Eingabeobjekt beim Erstellen oder Aktualisieren eines Events</li>
 * </ul>
 *
 * <h3>
 *     Validierung:
 * </h3>
 * <p>
 *     Die Felder sind mit Bean Validation Annotationen versehen, um sicherzustellen,
 *     dass nur gültige Daten verarbeitet werden.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-19
 * @see com.wiss.backend.controller.EventController
 * @see com.wiss.backend.entity.Event
 */
@Schema(description = "DTO für Events")
public class EventDTO {

    /**
     * Eindeutige ID des Events. Wird bei neu erstellten Events vom System generiert.
     */
    @Schema(description = "Eindeutige ID des Events", example = "1")
    private Long id;

    /**
     * Titel des Events (z. B. "Earthquake in Japan").
     * @apiNote Muss zwischen 5 und 255 Zeichen lang sein.
     * @see #getTitle()
     */
    @NotBlank(message = "Titel darf nicht leer sein")
    @Size(min = 5, max = 255, message = "Titel muss zwischen 5 und 255 Zeichen lang sein")
    @Schema(description = "Titel des Events", example = "Flood in Jakarta")
    private String title;

    /**
     * Datum des Events.
     * @apiNote Darf nicht in der Zukunft liegen.
     * @see #getDate()
     */
    @NotBlank(message = "Datum darf nicht leer sein")
    @PastOrPresent(message = "Datum darf nicht in der Zukunft liegen")
    @Schema(description = "Datum des Events", example = "2025-07-01")
    private LocalDate date;

    /**
     * Kategorie des Events, z. B. {@code floods}, {@code volcanoes}.
     * @see #getCategory()
     */
    @NotNull(message = "Kategorie darf nicht leer sein")
    @Schema(description = "Kategorie des Events", example = "floods")
    private EventCategory category;

    /**
     * Längengrad des Event-Standorts.
     * @apiNote Muss zwischen -180.0 und 180.0 liegen.
     * @see #getLongitude()
     */
    @NotNull(message = "Längengrad darf nicht null sein")
    @DecimalMin(value = "-180.0", inclusive = true, message = "Längengrad muss >= -180.0 sein")
    @DecimalMax(value = "180.0", inclusive = true, message = "Längengrad muss <= 180.0 sein")
    @Schema(description = "Längengrad des Event-Standorts", example = "106.85")
    private Double longitude;

    /**
     * Breitengrad des Event-Standorts.
     * @apiNote Muss zwischen -90.0 und 90.0 liegen.
     * @see #getLatitude()
     */
    @NotNull(message = "Breitengrad darf nicht null sein")
    @DecimalMin(value = "-90.0", inclusive = true, message = "Breitengrad muss >= -90.0 sein")
    @DecimalMax(value = "90.0", inclusive = true, message = "Breitengrad muss <= 90.0 sein")
    @Schema(description = "Breitengrad des Event-Standorts", example = "-6.21")
    private Double latitude;

    /**
     * Status des Events, z. B. {@code open} oder {@code closed}.
     * @see #getStatus()
     */
    @NotNull(message = "Status darf nicht leer sein")
    @Schema(description = "Status des Events", example = "open")
    private EventStatus status;

    /**
     * Leerer Konstruktor (benötigt für JSON-Deserialisierung).
     */
    public EventDTO() {}

    /**
     * Vollständiger Konstruktor zur Initialisierung aller Felder.
     *
     * @param id        ID des Events
     * @param title     Titel des Events
     * @param date      Datum des Events
     * @param category  Kategorie des Events
     * @param longitude Längengrad
     * @param latitude  Breitengrad
     * @param status    Status des Events
     */
    public EventDTO(Long id, String title, LocalDate date, EventCategory category, Double longitude, Double latitude, EventStatus status) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.category = category;
        this.longitude = longitude;
        this.latitude = latitude;
        this.status = status;
    }

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public EventCategory getCategory() { return category; }
    public void setCategory(EventCategory category) { this.category = category; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public EventStatus getStatus() { return status; }
    public void setStatus(EventStatus status) { this.status = status; }
}