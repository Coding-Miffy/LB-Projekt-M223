package com.wiss.backend.entity;

import com.wiss.backend.model.EventCategory;
import com.wiss.backend.model.EventStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * <h2>
 *     Entity-Klasse zur Repräsentation von Naturereignissen
 * </h2>
 * <p>
 *     Diese Klasse repräsentiert ein Event (Naturereignis) und wird von JPA/Hibernate verwendet,
 *     um die gleichnamige Datenbanktabelle <code>events</code> zu verwalten.
 * </p>
 *
 * <p>
 *     Ein Event besteht aus Metadaten wie Titel, Datum, Kategorie, geografischen Koordinaten und Status.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-18
 *
 * @see EventCategory Enum mit allen unterstützten Naturereignis-Kategorien
 * @see EventStatus Enum mit allen gültigen Statuswerte für ein Naturereignis
 */
@Entity
@Table(name = "events")
public class Event {

    /**
     * Eindeutige ID des Events (Primary Key).
     * Wird automatisch von der Datenbank generiert.
     * @see #getId()
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Titel des Events (z. B. "Earthquake in Japan").
     * @see #getTitle()
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * Datum, an dem das Event stattgefunden bzw. begonnen hat.
     * @see #getDate()
     */
    @Column(name = "date", nullable = false)
    private LocalDate date;

    /**
     * Kategorie des Events (z. B. <code>floods</code>, <code>volcanoes</code>).
     * @see #getCategory()
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 50)
    private EventCategory category;

    /**
     * Längengrad des Ereignis-Standorts.
     * @apiNote Erwarteter Wertebereich: -180 bis 180.
     * @see #getLongitude()
     */
    @Column(name = "longitude", nullable = false, length = 50)
    private Double longitude;

    /**
     * Breitengrad des Ereignis-Standorts.
     * @apiNote Erwarteter Wertebereich: -90 bis 90.
     * @see #getLatitude()
     */
    @Column(name = "latitude", nullable = false, length = 50)
    private Double latitude;

    /**
     * Status des Events (offen oder abgeschlossen).
     * @see #getStatus()
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private EventStatus status;

    /**
     * Leerer Standard-Konstruktor (für JPA erforderlich).
     */
    public Event() {}

    /**
     * Konstruktor zum Erstellen eines neuen Events ohne ID.
     *
     * @param title     Titel des Events.
     * @param date      Datum des Events.
     * @param category  Kategorie (z. B. floods, drought).
     * @param longitude Längengrad.
     * @param latitude  Breitengrad.
     * @param status    Status (open/closed).
     */
    public Event(String title, LocalDate date, EventCategory category, Double longitude, Double latitude, EventStatus status) {
        this.title = title;
        this.date = date;
        this.category = category;
        this.longitude = longitude;
        this.latitude = latitude;
        this.status = status;
    }

    /**
     * Konstruktor zum Erstellen eines vollständigen Event-Objekts inkl. ID (z. B. bei Updates).
     *
     * @param id        Eindeutige ID des Events.
     * @param title     Titel des Events.
     * @param date      Datum des Events.
     * @param category  Kategorie (z. B. floods, drought).
     * @param longitude Längengrad.
     * @param latitude  Breitengrad.
     * @param status    Status (open/closed).
     */
    public Event(Long id, String title, LocalDate date, EventCategory category, Double longitude, Double latitude, EventStatus status) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.category = category;
        this.longitude = longitude;
        this.latitude = latitude;
        this.status = status;
    }

    // Getter & Setter
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public EventCategory getCategory() {
        return category;
    }
    public void setCategory(EventCategory category) {
        this.category = category;
    }

    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public EventStatus getStatus() {
        return status;
    }
    public void setStatus(EventStatus status) {
        this.status = status;
    }
}
