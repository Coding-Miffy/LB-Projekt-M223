package com.wiss.backend.dto;

import com.wiss.backend.model.EventCategory;
import com.wiss.backend.model.EventStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * <h2>
 *     EventFormDTO
 * </h2>
 * <p>
 *     Dieses DTO wird verwendet, um Formulardaten vom Frontend entgegenzunehmen oder für Formular-Ansichten aufzubereiten.
 *     Es eignet sich insbesondere für Create- und Update-Operationen über das Web-Frontend.
 * </p>
 *
 * <h3>
 *     Verwendung:
 * </h3>
 * <ul>
 *     <li>Als Eingabeobjekt für Formulareingaben im Frontend</li>
 *     <li>Als Rückgabeobjekt für vorbefüllte Formulare (z. B. im Bearbeitungsmodus)</li>
 *     <li>Zur Umwandlung in ein {@link EventDTO} zur Weiterverarbeitung</li>
 * </ul>
 *
 * <h3>Validierung:</h3>
 * <p>
 *     Bean-Validation-Annotationen stellen sicher, dass nur gültige Formulare verarbeitet werden.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-19
 * @see EventDTO
 * @see com.wiss.backend.controller.EventController
 */
@Schema(description = "DTO für Frontend-Formulare (Create/Update)")
public class EventFormDTO {

    /**
     * Eindeutige ID des Events, nur für bestehende Events relevant (z. B. beim Bearbeiten).
     */
    @Schema(description = "Eindeutige ID des Events", example = "1")
    private Long id;

    /**
     * Titel des Events (z. B. "Earthquake in Japan").
     * @apiNote Muss zwischen 5 und 255 Zeichen lang sein.
     * @see #getTitle()
     */
    @NotBlank(message = "Titel ist erforderlich")
    @Size(min = 5, max = 255, message = "Titel muss zwischen 5 und 255 Zeichen lang sein")
    @Schema(description = "Titel des Events", example = "Flood in Jakarta")
    private String title;

    /**
     * Datum des Events.
     * @apiNote Darf nicht in der Zukunft liegen.
     * @see #getDate()
     */
    @NotNull(message = "Datum ist erforderlich")
    @PastOrPresent(message = "Datum darf nicht in der Zukunft liegen")
    @Schema(description = "Datum des Events", example = "2025-07-01")
    private LocalDate date;

    /**
     * Kategorie des Events, z. B. {@code floods}, {@code volcanoes}.
     * @see #getCategory()
     */
    @NotNull(message = "Kategorie ist erforderlich")
    @Schema(description = "Kategorie des Events", example = "floods")
    private EventCategory category;

    /**
     * Längengrad des Event-Standorts.
     * @apiNote Muss zwischen -180.0 und 180.0 liegen.
     * @see #getLongitude()
     */
    @NotNull(message = "Längengrad ist erforderlich")
    @DecimalMin(value = "-180.0", inclusive = true, message = "Längengrad muss >= -180.0 sein")
    @DecimalMax(value = "180.0", inclusive = true, message = "Längengrad muss <= 180.0 sein")
    @Schema(description = "Längengrad des Event-Standorts", example = "106.85")
    private Double longitude;

    /**
     * Breitengrad des Event-Standorts.
     * @apiNote Muss zwischen -90.0 und 90.0 liegen.
     * @see #getLatitude()
     */
    @NotNull(message = "Breitengrad ist erforderlich")
    @DecimalMin(value = "-90.0", inclusive = true, message = "Breitengrad muss >= -90.0 sein")
    @DecimalMax(value = "90.0", inclusive = true, message = "Breitengrad muss <= 90.0 sein")
    @Schema(description = "Breitengrad des Event-Standorts", example = "-6.21")
    private Double latitude;

    /**
     * Status des Events, z. B. {@code open} oder {@code closed}.
     * @see #getStatus()
     */
    @NotNull(message = "Status ist erforderlich")
    @Schema(description = "Status des Events", example = "open")
    private EventStatus status;

    /**
     * Leerer Konstruktor (benötigt für JSON-Deserialisierung).
     */
    public EventFormDTO() {}

    /**
     * Konstruktor zum Erstellen eines neuen Events (ohne ID).
     *
     * @param title     Titel des Events
     * @param date      Datum des Events
     * @param category  Kategorie des Events
     * @param longitude Längengrad
     * @param latitude  Breitengrad
     * @param status    Status des Events
     */
    public EventFormDTO(String title, LocalDate date, EventCategory category, Double longitude, Double latitude, EventStatus status) {
        this.title = title;
        this.date = date;
        this.category = category;
        this.longitude = longitude;
        this.latitude = latitude;
        this.status = status;
    }

    /**
     * Konstruktor für die Bearbeitung bestehender Events (inkl. ID).
     *
     * @param id        ID des Events
     * @param title     Titel des Events
     * @param date      Datum des Events
     * @param category  Kategorie des Events
     * @param longitude Längengrad
     * @param latitude  Breitengrad
     * @param status    Status des Events
     */
    public EventFormDTO(Long id, String title, LocalDate date, EventCategory category, Double longitude, Double latitude, EventStatus status) {
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

    /**
     * Konvertiert dieses Formular-DTO zu einem {@link EventDTO}, z. B. zur Weitergabe an den Service.
     *
     * @param id ID des Events
     * @return Konvertiertes {@link EventDTO}
     */
    public EventDTO toEventDTO(Long id) {
        return new EventDTO(
                id,
                this.title,
                this.date,
                this.category,
                this.longitude,
                this.latitude,
                this.status
        );
    }

    /**
     * Erstellt ein {@link EventFormDTO} aus einem bestehenden {@link EventDTO}, z. B. für das Bearbeitungsformular.
     *
     * @param eventDTO Ursprüngliches {@link EventDTO}
     * @return Neues {@link EventFormDTO} mit denselben Werten
     */
    public static EventFormDTO fromEventDTO(EventDTO eventDTO) {
        return new EventFormDTO(
                eventDTO.getTitle(),
                eventDTO.getDate(),
                eventDTO.getCategory(),
                eventDTO.getLongitude(),
                eventDTO.getLatitude(),
                eventDTO.getStatus()
        );
    }

    /**
     * Gibt eine lesbare Textdarstellung des DTOs zurück (z. B. für Logging-Zwecke).
     *
     * @return String-Repräsentation des Events
     */
    @Override
    public String toString() {
        return "EventFormDTO{" +
                "title='" + title + "'" +
                ", date='" + date + "'" +
                ", category='" + category + "'" +
                ", longitude='" + longitude + "'" +
                ", latitude='" + latitude + "'" +
                ", status='" + status + "'" +
                "}";
    }
}