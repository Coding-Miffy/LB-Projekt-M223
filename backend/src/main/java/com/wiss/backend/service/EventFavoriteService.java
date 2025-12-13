package com.wiss.backend.service;

import com.wiss.backend.entity.Event;
import com.wiss.backend.entity.EventFavorite;
import com.wiss.backend.repository.AppUserRepository;
import com.wiss.backend.repository.EventFavoriteRepository;
import com.wiss.backend.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <h2>
 *     Service für Event-Favoriten (Like-/Merkliste-Funktion)
 * </h2>
 *
 * <p>
 *     Diese Serviceklasse implementiert die Geschäftslogik für das Favorisieren von Events.
 *     Benutzer können Events zu ihrer persönlichen Favoritenliste hinzufügen oder daraus
 *     entfernen. Zusätzlich verwaltet der Service den globalen Zähler
 *     <code>favoritesCount</code> im {@link Event}-Entity.
 * </p>
 *
 * <h3>Hauptaufgaben:</h3>
 * <ul>
 *     <li>Favorisieren / Entfavorisieren eines Events (Toggle-Mechanismus)</li>
 *     <li>Validierung von Benutzer- und Event-Existenz</li>
 *     <li>Aktualisierung des Favoritenzählers eines Events</li>
 *     <li>Abfrage, ob ein Event für einen User bereits favorisiert ist</li>
 * </ul>
 *
 * <p>
 *     Die Klasse verwendet {@link Transactional}, um Datenkonsistenz sicherzustellen:
 *     Wenn z. B. beim Speichern des Favoriten ein Fehler auftritt, wird sowohl die
 *     EventAktualisierung als auch das Favoriten-Mapping vollständig zurückgerollt.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-12-12
 *
 * @see EventFavoriteRepository
 * @see EventRepository
 * @see Event
 */
@Service
public class EventFavoriteService {

    private final EventFavoriteRepository eventFavoriteRepository;
    private final AppUserRepository appUserRepository;
    private final EventRepository eventRepository;

    /**
     * Konstruktor zur Initialisierung der benötigten Repositories.
     *
     * @param eventFavoriteRepository Repository für Favoriten-Mappings
     * @param appUserRepository       Repository zur Benutzerprüfung
     * @param eventRepository         Repository zum Laden und Aktualisieren von Events
     */
    public EventFavoriteService(EventFavoriteRepository eventFavoriteRepository, AppUserRepository appUserRepository,EventRepository eventRepository) {
        this.eventFavoriteRepository = eventFavoriteRepository;
        this.appUserRepository = appUserRepository;
        this.eventRepository = eventRepository;
    }

    /**
     * <h3>Favorit toggeln (Hinzufügen oder Entfernen)</h3>
     *
     * <p>
     *     Diese Methode implementiert einen Toggle-Mechanismus:
     * </p>
     *
     * <ul>
     *     <li>Falls der User das Event bereits favorisiert hat → Favorit wird gelöscht</li>
     *     <li>Falls nicht → Favorit wird neu gespeichert</li>
     *     <li>Der globale Zähler des Events wird angepasst</li>
     * </ul>
     *
     * <p>
     *     Der gesamte Vorgang läuft in einer Transaktion, damit Favoritenmapping und
     *     Eventzählung immer konsistent bleiben.
     * </p>
     *
     * @param userId  ID des Benutzers
     * @param eventId ID des Events
     * @return <code>true</code>, wenn das Event danach favorisiert ist;
     *         <code>false</code>, wenn es entfernt wurde
     * @throws IllegalArgumentException wenn Benutzer oder Event nicht existieren
     */
    @Transactional
    public boolean toggleFavorite(Long userId, Long eventId) {

        // Prüfen, ob User existiert
        appUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        // Prüfen, ob Event existiert
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found: " + eventId));

        // Fall 1: Favorit existiert → entfernen
        if (eventFavoriteRepository.existsByUserIdAndEventId(userId, eventId)) {
            eventFavoriteRepository.deleteByUserIdAndEventId(userId, eventId);
            event.decrementFavorites();
            eventRepository.save(event);
            return false; // jetzt kein Favorit mehr
        } else {
            // Fall 2: Favorit existiert nicht → hinzufügen
            eventFavoriteRepository.save(new EventFavorite(userId, eventId));
            event.incrementFavorites();
            eventRepository.save(event);
            return true; // jetzt Favorit
        }
    }

    /**
     * Gibt zurück, wie oft ein Event global als Favorit markiert wurde.
     *
     * @param eventId ID des Events
     * @return Anzahl der Favorisierungen
     * @throws IllegalArgumentException wenn das Event nicht existiert
     */
    @Transactional(readOnly = true)
    public int getFavoritesCount(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found: " + eventId));
        return event.getFavoritesCount();
    }

    /**
     * Prüft, ob ein bestimmtes Event bereits durch einen spezifischen User
     * als Favorit markiert wurde.
     *
     * @param userId  Benutzer-ID
     * @param eventId Event-ID
     * @return <code>true</code>, wenn Event bereits favorisiert ist
     */
    public boolean isFavorite(Long userId, Long eventId) {
        return eventFavoriteRepository.existsByUserIdAndEventId(userId, eventId);
    }
}
