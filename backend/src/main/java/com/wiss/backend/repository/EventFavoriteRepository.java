package com.wiss.backend.repository;

import com.wiss.backend.entity.EventFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <h2>
 *     Repository für Favoriten-Markierungen von Events
 * </h2>
 *
 * <p>
 *     Dieses Repository verwaltet alle {@link EventFavorite}-Einträge,
 *     welche die Beziehung zwischen Benutzer:innen und favorisierten Events darstellen.
 *     Es dient dazu, Favoriten abzufragen, hinzuzufügen, zu löschen und zu zählen.
 * </p>
 *
 * <h3>Hauptfunktionen:</h3>
 * <ul>
 *     <li>Abrufen aller Favoriten eines bestimmten Users</li>
 *     <li>Prüfen, ob ein Event bereits favorisiert wurde</li>
 *     <li>Entfernen eines Favoriten</li>
 *     <li>Zählen, wie oft ein Event favorisiert wurde</li>
 * </ul>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-12-12
 *
 * @see EventFavorite
 */
@Repository
public interface EventFavoriteRepository extends JpaRepository<EventFavorite, Long> {

    /**
     * Liefert alle Favoriten eines bestimmten Users zurück.
     *
     * @param userId ID des Users
     * @return Liste der favorisierten Events dieses Users
     */
    List<EventFavorite> findByUserId(Long userId);

    /**
     * Prüft, ob ein bestimmter User ein bestimmtes Event bereits favorisiert hat.
     *
     * @param userId  ID des Users
     * @param eventId ID des Events
     * @return true, wenn der Favorit existiert
     */
    boolean existsByUserIdAndEventId(Long userId, Long eventId);

    /**
     * Löscht die Favoriten-Markierung eines bestimmten Users für ein bestimmtes Event.
     *
     * @param userId  ID des Users
     * @param eventId ID des Events
     */
    void deleteByUserIdAndEventId(Long userId, Long eventId);

    /**
     * Zählt, wie oft ein Event insgesamt favorisiert wurde.
     *
     * @param eventId ID des Events
     * @return Anzahl der Favoriten-Einträge
     */
    long countByEventId(Long eventId);

}
