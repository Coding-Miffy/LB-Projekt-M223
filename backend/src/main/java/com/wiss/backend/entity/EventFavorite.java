package com.wiss.backend.entity;

import jakarta.persistence.*;

/**
 * <h2>
 *     Entity-Klasse zur Repräsentation von Event-Favorisierungen
 * </h2>
 *
 * <p>
 *     Diese Entität speichert eine Favoritenbeziehung zwischen einem User und einem Event.
 *     Jedes Objekt der Klasse {@code EventFavorite} entspricht einem Eintrag in der Tabelle
 *     <code>event_favorites</code> und stellt damit eine N:M-Beziehung logisch dar:
 * </p>
 *
 * <ul>
 *     <li>Ein User kann mehrere Events favorisieren</li>
 *     <li>Ein Event kann von mehreren Usern favorisiert werden</li>
 * </ul>
 *
 * <p>
 *     Die Entität wird u. a. verwendet für:
 * </p>
 * <ul>
 *     <li>Favoriten hinzufügen und entfernen</li>
 *     <li>Prüfung, ob ein Event vom User bereits favorisiert wurde</li>
 *     <li>Auslesen aller Favoriten eines Users</li>
 * </ul>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-12-12
 *
 * @see com.wiss.backend.entity.Event
 * @see com.wiss.backend.entity.AppUser
 * @see com.wiss.backend.repository.EventFavoriteRepository
 */
@Entity
@Table(name = "event_favorites")
public class EventFavorite {

    /**
     * Primärschlüssel der Favoriten-Relation.
     * Wird automatisch generiert.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID des Users, der das Event favorisiert hat.
     * <p>
     * Hinweis: Um zyklische Ladevorgänge zu vermeiden, wird hier bewusst nur die ID gespeichert
     * und keine direkte ManyToOne-Beziehung verwendet.
     * </p>
     */
    @Column(nullable = false)
    private Long userId;

    /**
     * ID des favorisierten Events.
     */
    @Column(nullable = false)
    private Long eventId;

    /**
     * Geschützter Standardkonstruktor für JPA.
     */
    protected EventFavorite() {}

    /**
     * Erstellt eine neue Favoritenbeziehung zwischen einem User und einem Event.
     *
     * @param userId  ID des Users
     * @param eventId ID des Events
     */
    public EventFavorite(Long userId, Long eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    // Getter und Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
}
