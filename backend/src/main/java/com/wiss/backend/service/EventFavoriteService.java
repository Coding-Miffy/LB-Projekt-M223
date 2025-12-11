package com.wiss.backend.service;

import com.wiss.backend.entity.Event;
import com.wiss.backend.entity.EventFavorite;
import com.wiss.backend.repository.AppUserRepository;
import com.wiss.backend.repository.EventFavoriteRepository;
import com.wiss.backend.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventFavoriteService {

    private final EventFavoriteRepository eventFavoriteRepository;
    private final AppUserRepository appUserRepository;
    private final EventRepository eventRepository;

    public EventFavoriteService(EventFavoriteRepository eventFavoriteRepository, AppUserRepository appUserRepository,EventRepository eventRepository) {
        this.eventFavoriteRepository = eventFavoriteRepository;
        this.appUserRepository = appUserRepository;
        this.eventRepository = eventRepository;
    }

    /**
     * Convenience: Favorit toggeln.
     */
    @Transactional
    public boolean toggleFavorite(Long userId, Long eventId) {
        appUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found: " + eventId));

        if (eventFavoriteRepository.existsByUserIdAndEventId(userId, eventId)) {
            eventFavoriteRepository.deleteByUserIdAndEventId(userId, eventId);
            event.decrementFavorites();
            eventRepository.save(event);
            return false; // jetzt kein Favorit mehr
        } else {
            eventFavoriteRepository.save(new EventFavorite(userId, eventId));
            event.incrementFavorites();
            eventRepository.save(event);
            return true; // jetzt Favorit
        }
    }

    /**
     * Gibt zurück, wie oft ein Event als Favorit markiert wurde.
     */
    @Transactional(readOnly = true)
    public int getFavoritesCount(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found: " + eventId));
        return event.getFavoritesCount();
    }

    /**
     * Prüft, ob das Event für diesen User bereits als Favorit markiert ist.
     */
    public boolean isFavorite(Long userId, Long eventId) {
        return eventFavoriteRepository.existsByUserIdAndEventId(userId, eventId);
    }
}
