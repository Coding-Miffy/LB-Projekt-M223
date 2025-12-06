package com.wiss.backend.repository;

import com.wiss.backend.entity.Event;
import com.wiss.backend.model.EventCategory;
import com.wiss.backend.model.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * <h2>
 *     Repository für Naturereignisse
 * </h2>
 * <p>
 *     Diese Schnittstelle verwaltet den Zugriff auf {@link Event}-Entitäten
 *     in der Datenbank. Sie erweitert {@link JpaRepository} und bietet
 *     sowohl Standardmethoden (CRUD) als auch benutzerdefinierte
 *     Abfragen für gezielte Filterungen und Auswertungen.
 * </p>
 *
 * <h3>
 *     Benutzerdefinierte Abfragen:
 * </h3>
 * <ul>
 *     <li>Filterung nach Kategorie, Status und Datum</li>
 *     <li>Kombinierte Filter (z. B. Kategorie + Zeitraum)</li>
 *     <li>Zählmethoden zur Auswertung nach Status oder Zeitspanne</li>
 * </ul>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-20
 * @see Event
 * @see com.wiss.backend.service.EventService
 * @see com.wiss.backend.controller.EventController
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // ---------------------------------------------
    // Einfache Filterabfragen
    // ---------------------------------------------

    /**
     * Gibt alle Events zurück, die der angegebenen Kategorie entsprechen.
     *
     * @param category gewünschte {@link EventCategory}
     * @return Liste passender Events
     * @see com.wiss.backend.service.EventService#getEventsByCategory(EventCategory)
     * @see com.wiss.backend.service.EventService#getEventsByCategoryAsDTO(EventCategory)
     */
    List<Event> findByCategory(EventCategory category);

    /**
     * Gibt alle Events zurück, die den angegebenen Status besitzen.
     *
     * @param status gewünschter {@link EventStatus}
     * @return Liste passender Events
     * @see com.wiss.backend.service.EventService#getEventsByStatus(EventStatus) 
     * @see com.wiss.backend.service.EventService#getEventsByStatusAsDTO(EventStatus)
     */
    List<Event> findByStatus(EventStatus status);

    /**
     * Gibt alle Events zurück, die an einem bestimmten Datum stattgefunden haben.
     *
     * @param date gewünschtes Datum
     * @return Liste der Events am angegebenen Tag
     * @see com.wiss.backend.service.EventService#getEventsByDate(LocalDate) 
     * @see com.wiss.backend.service.EventService#getEventsByDateAsDTO(LocalDate)
     */
    List<Event> findByDate(LocalDate date);

    // ---------------------------------------------
    // Kombinierte Filterabfragen
    // ---------------------------------------------

    /**
     * Gibt alle Events zurück, die einer bestimmten Kategorie und einem bestimmten Status entsprechen.
     *
     * @param category Kategorie des Events
     * @param status   Status des Events
     * @return Liste passender Events
     * @see com.wiss.backend.service.EventService#getEventsByCategoryAndStatus(EventCategory, EventStatus) 
     */
    List<Event> findByCategoryAndStatus(EventCategory category, EventStatus status);

    /**
     * Gibt alle Events zurück, die in einem bestimmten Zeitraum liegen.
     *
     * @param start Startdatum (inklusive)
     * @param end   Enddatum (inklusive)
     * @return Liste aller Events im gegebenen Zeitraum
     * @see com.wiss.backend.service.EventService#getEventsByDateBetween(LocalDate, LocalDate)
     */
    List<Event> findByDateBetween(LocalDate start, LocalDate end);

    /**
     * Gibt alle Events zurück, die zu einer bestimmten Kategorie gehören und innerhalb eines bestimmten Zeitraums liegen.
     *
     * @param category Kategorie des Events
     * @param start    Startdatum (inklusive)
     * @param end      Enddatum (inklusive)
     * @return Liste passender Events
     * @see com.wiss.backend.service.EventService#getEventsByCategoryAndDateBetween(EventCategory, LocalDate, LocalDate)
     */
    List<Event> findByCategoryAndDateBetween(EventCategory category, LocalDate start, LocalDate end);

    /**
     * Gibt alle Events zurück, die einen bestimmten Status besitzen und innerhalb eines bestimmten Zeitraums liegen.
     *
     * @param status Status des Events
     * @param start  Startdatum (inklusive)
     * @param end    Enddatum (inklusive)
     * @return Liste passender Events
     * @see com.wiss.backend.service.EventService#getEventsByStatusAndDateBetween(EventStatus, LocalDate, LocalDate)
     */
    List<Event> findByStatusAndDateBetween(EventStatus status, LocalDate start, LocalDate end);

    /**
     * Gibt alle Events zurück, die einer bestimmten Kategorie und einem bestimmten Status entsprechen
     * und innerhalb eines bestimmten Zeitraums liegen.
     *
     * @param category Kategorie des Events
     * @param status   Status des Events
     * @param start    Startdatum (inklusive)
     * @param end      Enddatum (inklusive)
     * @return Liste passender Events
     * @see com.wiss.backend.service.EventService#getEventsByCategoryAndStatusAndDateBetween(EventCategory, EventStatus, LocalDate, LocalDate)
     */
    List<Event> findByCategoryAndStatusAndDateBetween(EventCategory category, EventStatus status, LocalDate start, LocalDate end);

    // ---------------------------------------------
    // Zählmethoden
    // ---------------------------------------------

    /**
     * Zählt alle Events, die der angegebenen Kategorie entsprechen.
     *
     * @param category Kategorie der Events
     * @return Anzahl der passenden Events
     * @see com.wiss.backend.service.EventService#getTotalEventsByCategory(EventCategory)
     */
    long countByCategory(EventCategory category);

    /**
     * Zählt alle Events mit dem angegebenen Status.
     *
     * @param status Status der Events
     * @return Anzahl der passenden Events
     * @see com.wiss.backend.service.EventService#getTotalEventsByStatus(EventStatus)
     */
    long countByStatus(EventStatus status);

    /**
     * Zählt alle Events, die innerhalb eines bestimmten Zeitraums stattfinden.
     *
     * @param start Startdatum (inklusive)
     * @param end   Enddatum (inklusive)
     * @return Anzahl der Events im gegebenen Zeitraum
     * @see com.wiss.backend.service.EventService#getTotalEventsByDateBetween(LocalDate, LocalDate)
     */
    long countByDateBetween(LocalDate start, LocalDate end);
}