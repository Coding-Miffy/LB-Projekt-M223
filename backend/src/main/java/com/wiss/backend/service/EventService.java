package com.wiss.backend.service;

import com.wiss.backend.controller.EventController;
import com.wiss.backend.dto.EventDTO;
import com.wiss.backend.dto.EventFormDTO;
import com.wiss.backend.entity.Event;
import com.wiss.backend.exception.CoordinateOutOfRangeException;
import com.wiss.backend.exception.EventNotFoundException;
import com.wiss.backend.exception.FutureDateException;
import com.wiss.backend.exception.InvalidEventDataException;
import com.wiss.backend.mapper.EventMapper;
import com.wiss.backend.model.EventCategory;
import com.wiss.backend.model.EventStatus;
import com.wiss.backend.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * <h2>
 *     Service für Naturereignisse
 * </h2>
 * <p>
 *     Diese Klasse kapselt sämtliche Operationen zur Verwaltung von Naturereignissen:
 *     Erstellen, Lesen, Bearbeiten, Löschen und Filtern nach Attributen wie Kategorie,
 *     Status oder Zeitraum. Zudem werden DTOs für verschiedene Anwendungszwecke bereitgestellt.
 * </p>
 *
 * <h3>
 *     Verantwortlichkeiten:
 * </h3>
 * <ul>
 *     <li>Verbindung zur Datenbank über {@link EventRepository}</li>
 *     <li>Konvertierung zwischen {@link Event}, {@link EventDTO} und {@link EventFormDTO}</li>
 *     <li>Validierung der Eingabedaten (inkl. Ausnahmebehandlung)</li>
 * </ul>
 *
 * <h3>
 *     Verwendete Hilfsklassen:
 * </h3>
 * <ul>
 *     <li>{@link EventMapper} zur Umwandlung zwischen Entität und DTO</li>
 *     <li>{@link EventNotFoundException}, {@link InvalidEventDataException} usw. für Fehlerbehandlung</li>
 * </ul>
 *
 * <h3>
 *     Anwendungsbereich:
 * </h3>
 * <p>
 *     Diese Serviceklasse wird durch den {@code @Service}-Annotation von Spring automatisch erkannt
 *     und kann per Dependency Injection in Controllern verwendet werden.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-20
 *
 * @see Event
 * @see EventDTO
 * @see EventFormDTO
 * @see EventRepository
 * @see EventMapper
 * @see com.wiss.backend.controller.EventController
 */
@Service
public class EventService {

    /**
     * Repository für den Datenzugriff auf {@link Event}-Entitäten.
     * Wird via Konstruktor automatisch durch Spring injiziert.
     */
    private final EventRepository eventRepository;

    /**
     * Konstruktor für Dependency Injection.
     *
     * @param eventRepository Repository für Event-Datenbankoperationen
     */
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // ---------------------------------------------
    // DTO-Methoden
    // ---------------------------------------------

    /**
     * Gibt alle Events als {@link EventDTO}-Liste zurück.
     *
     * @return Liste aller Events als DTOs
     * @see EventController#getAllEvents() 
     */
    public List<EventDTO> getAllEventsAsDTO() {
        List<Event> entities = eventRepository.findAll();
        return EventMapper.toDTOList(entities);
    }

    /**
     * Gibt ein einzelnes Event als {@link EventDTO} zurück.
     *
     * @param id ID des Events
     * @return Event als DTO
     * @throws EventNotFoundException Wenn das Event nicht existiert
     * @see #validateId(Long)
     * @see EventController#getEventById(Long) 
     */
    public EventDTO getEventByIdAsDTO(Long id) {
        validateId(id);
        Event entity = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        return EventMapper.toDTO(entity);
    }

    /**
     * Gibt Events nach Kategorie gefiltert als {@link EventDTO}s zurück.
     *
     * @param category Kategorie (z. B. {@code wildfires})
     * @return Liste passender Events
     * @see EventController#getEventsByCategory(EventCategory)
     */
    public List<EventDTO> getEventsByCategoryAsDTO(EventCategory category) {
        List<Event> entities = eventRepository.findByCategory(category);
        return EventMapper.toDTOList(entities);
    }

    /**
     * Gibt Events nach Status gefiltert zurück.
     *
     * @param status Status (z. B. {@code open})
     * @return Liste gefilterter Events als DTOs
     * @see EventController#getEventsByStatus(EventStatus) 
     */
    public List<EventDTO> getEventsByStatusAsDTO(EventStatus status) {
        List<Event> entities = eventRepository.findByStatus(status);
        return EventMapper.toDTOList(entities);
    }

    /**
     * Gibt Events mit einem bestimmten Datum als {@link EventDTO}s zurück.
     *
     * @param date Zu filterndes Datum
     * @return Liste mit Events zum angegebenen Datum
     * @see EventController#getEventsByDate(LocalDate)
     */
    public List<EventDTO> getEventsByDateAsDTO(LocalDate date) {
        List<Event> entities = eventRepository.findByDate(date);
        return EventMapper.toDTOList(entities);
    }

    // ---------------------------------------------
    // FormDTO-Methoden (für Frontend-Formulare)
    // ---------------------------------------------

    /**
     * Gibt alle Events als {@link EventFormDTO}-Liste zurück.
     *
     * @return Liste aller Events als FormDTOs
     * @see EventController#getAllFormEvents()
     */
    public List<EventFormDTO> getAllEventsAsFormDTO() {
        List<Event> entities = eventRepository.findAll();
        return EventMapper.toFormDTOList(entities);
    }

    /**
     * Gibt ein Event als {@link EventFormDTO} zurück.
     *
     * @param id ID des gesuchten Events
     * @return Event als FormDTO
     * @throws EventNotFoundException Wenn das Event nicht existiert
     * @see #validateId(Long) 
     * @see EventController#getEventByIdForEdit(Long)
     */
    public EventFormDTO getEventByIdAsFormDTO(Long id) {
        validateId(id);
        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException(id);
        }

        Event entity = getEventById(id);
        return EventMapper.toFormDTO(entity);
    }

    /**
     * Erstellt ein neues Event auf Basis des FormDTOs.
     *
     * @param event Event-Entity mit Formdaten
     * @return Gespeichertes Event als FormDTO
     * @see #validateEventData(String, LocalDate, EventCategory, Double, Double, EventStatus) 
     * @see EventController#createEventFromForm(Event) 
     */
    public EventFormDTO createEventFromForm(Event event) {
        validateEventData(
                event.getTitle(),
                event.getDate(),
                event.getCategory(),
                event.getLongitude(),
                event.getLatitude(),
                event.getStatus()
        );

        Event savedEntity = eventRepository.save(event);
        return EventMapper.toFormDTO(savedEntity);
    }

    /**
     * Aktualisiert ein bestehendes Event über Formdaten.
     *
     * @param id ID des zu aktualisierenden Events
     * @param event Neue Daten als Event-Entity
     * @return Aktualisiertes Event als FormDTO
     * @throws EventNotFoundException Wenn das Event nicht existiert
     * @see #validateEventData(String, LocalDate, EventCategory, Double, Double, EventStatus) 
     * @see EventController#updateEventFromForm(Long, Event)
     */
    public EventFormDTO updateEventFromForm(Long id, Event event) {
        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException(id);
        }
        event.setId(id);

        validateEventData(
                event.getTitle(),
                event.getDate(),
                event.getCategory(),
                event.getLongitude(),
                event.getLatitude(),
                event.getStatus()
        );

        Event updatedEntity = eventRepository.save(event);
        return EventMapper.toFormDTO(updatedEntity);
    }

    // ---------------------------------------------
    // Entity-Methoden
    // ---------------------------------------------

    /**
     * Gibt alle gespeicherten Events als vollständige {@link Event}-Entities zurück.
     *
     * @return Liste aller Events aus der Datenbank
     */
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    /**
     * Ruft ein einzelnes Event aus der Datenbank als {@link Event}-Entity ab.
     *
     * @param id ID des gesuchten Events
     * @return Event als vollständige Entity
     * @throws EventNotFoundException Wenn das Event nicht existiert
     * @see #validateId(Long)
     */
    public Event getEventById(Long id) {
        validateId(id);
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
    }

    /**
     * Gibt alle Events zurück, die der angegebenen {@link EventCategory} entsprechen.
     *
     * @param category Kategorie (z. B. {@code severeStorms}, {@code volcanoes})
     * @return Liste der gefilterten Events
     */
    public List<Event> getEventsByCategory(EventCategory category) {
        return eventRepository.findByCategory(category);
    }

    /**
     * Gibt alle Events mit dem angegebenen {@link EventStatus} zurück.
     *
     * @param status Status (z. B. {@code open}, {@code closed})
     * @return Liste der gefilterten Events
     */
    public List<Event> getEventsByStatus(EventStatus status) {
        return eventRepository.findByStatus(status);
    }

    /**
     * Gibt alle Events zurück, die am angegebenen Datum stattfanden.
     *
     * @param date Datum zur Filterung
     * @return Liste der gefilterten Events
     */
    public List<Event> getEventsByDate(LocalDate date) {
        return eventRepository.findByDate(date);
    }

    /**
     * Zählt alle gespeicherten Events in der Datenbank.
     *
     * @return Gesamtanzahl der Events
     * @see EventController#getEventCount()
     */
    public long getTotalEventsCount() {
        return eventRepository.count();
    }

    // ---------------------------------------------
    // CRUD-Methoden
    // ---------------------------------------------

    /**
     * Erstellt ein neues Event basierend auf einem {@link EventDTO}.
     *
     * @param dto DTO mit den Eventdaten
     * @return Das neu erstellte Event als DTO
     * @throws InvalidEventDataException Wenn die Eingabedaten ungültig sind
     * @see #validateEventData(String, LocalDate, EventCategory, Double, Double, EventStatus) 
     * @see EventController#createEvent(EventDTO)
     */
    public EventDTO createEvent(EventDTO dto) {
        validateEventData(
                dto.getTitle(),
                dto.getDate(),
                dto.getCategory(),
                dto.getLongitude(),
                dto.getLatitude(),
                dto.getStatus()
        );

        Event entity = EventMapper.toEntity(dto);
        Event newEvent = eventRepository.save(entity);
        return EventMapper.toDTO(newEvent);
    }

    /**
     * Aktualisiert ein bestehendes Event mit neuen Daten.
     *
     * @param id ID des zu aktualisierenden Events
     * @param dto Neues {@link EventDTO} mit aktualisierten Informationen
     * @return Aktualisiertes Event als DTO
     * @throws EventNotFoundException Wenn das Event nicht existiert
     * @throws InvalidEventDataException Wenn die Eingabedaten ungültig sind
     * @see #validateEventData(String, LocalDate, EventCategory, Double, Double, EventStatus) 
     * @see EventController#updateEvent(Long, EventDTO)
     */
    public EventDTO updateEvent(Long id, EventDTO dto) {
        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException(id);
        }

        validateEventData(
                dto.getTitle(),
                dto.getDate(),
                dto.getCategory(),
                dto.getLongitude(),
                dto.getLatitude(),
                dto.getStatus()
        );

        Event entity = EventMapper.toEntity(dto);
        entity.setId(id); // <- Wichtig: id setzen für UPDATE-Erkennung
        Event updatedEntity = eventRepository.save(entity);
        return EventMapper.toDTO(updatedEntity);
    }

    /**
     * Löscht ein Event anhand seiner ID.
     *
     * @param id ID des zu löschenden Events
     * @throws EventNotFoundException Wenn das Event nicht existiert
     * @see EventController#deleteEvent(Long)
     */
    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException(id);
        }
        
        eventRepository.deleteById(id);
    }

    // ---------------------------------------------
    // Custom-Queries-Methoden
    // ---------------------------------------------

    /**
     * Gibt alle Events zurück, die der angegebenen Kategorie und dem angegebenen Status entsprechen.
     *
     * @param category Kategorie zur Filterung
     * @param status Status zur Filterung
     * @return Liste der gefilterten Events
     * @see EventController#getEventsByFilter(EventCategory, EventStatus, LocalDate, LocalDate) 
     */
    public List<EventDTO> getEventsByCategoryAndStatus(EventCategory category, EventStatus status) {
        List<Event> entities = eventRepository.findByCategoryAndStatus(category, status);
        return EventMapper.toDTOList(entities);
    }

    /**
     * Gibt alle Events zurück, deren Datum zwischen dem angegebenen Start- und Enddatum liegt.
     *
     * @param start Startdatum (inklusive)
     * @param end Enddatum (inklusive)
     * @return Liste der gefilterten Events
     * @see EventController#getEventsByFilter(EventCategory, EventStatus, LocalDate, LocalDate)
     */
    public List<EventDTO> getEventsByDateBetween(LocalDate start, LocalDate end) {
        List<Event> entities = eventRepository.findByDateBetween(start, end);
        return EventMapper.toDTOList(entities);
    }

    /**
     * Gibt alle Events zurück, die der angegebenen Kategorie entsprechen
     * und deren Datum zwischen dem angegebenen Start- und Enddatum liegt.
     *
     * @param category Kategorie zur Filterung
     * @param start Startdatum (inklusive)
     * @param end Enddatum (inklusive)
     * @return Liste der gefilterten Events
     * @see EventController#getEventsByFilter(EventCategory, EventStatus, LocalDate, LocalDate)
     */
    public List<EventDTO> getEventsByCategoryAndDateBetween(EventCategory category, LocalDate start, LocalDate end) {
        List<Event> entities = eventRepository.findByCategoryAndDateBetween(category, start, end);
        return EventMapper.toDTOList(entities);
    }

    /**
     * Gibt alle Events zurück, die dem angegebenen Status entsprechen
     * und deren Datum zwischen dem angegebenen Start- und Enddatum liegt.
     *
     * @param status Status zur Filterung
     * @param start Startdatum (inklusive)
     * @param end Enddatum (inklusive)
     * @return Liste der gefilterten Events
     * @see EventController#getEventsByFilter(EventCategory, EventStatus, LocalDate, LocalDate)
     */
    public List<EventDTO> getEventsByStatusAndDateBetween(EventStatus status, LocalDate start, LocalDate end) {
        List<Event> entities = eventRepository.findByStatusAndDateBetween(status, start, end);
        return EventMapper.toDTOList(entities);
    }

    /**
     * Gibt alle Events zurück, die der angegebenen Kategorie und dem angegebenen
     * Status entsprechen und deren Datum zwischen dem angegebenen Start- und Enddatum liegt.
     *
     * @param category Kategorie zur Filterung
     * @param status Status zur Filterung
     * @param start Startdatum (inklusive)
     * @param end Enddatum (inklusive)
     * @return Liste der gefilterten Events
     * @see EventController#getEventsByFilter(EventCategory, EventStatus, LocalDate, LocalDate)
     */
    public List<EventDTO> getEventsByCategoryAndStatusAndDateBetween(EventCategory category, EventStatus status, LocalDate start, LocalDate end) {
        List<Event> entities = eventRepository.findByCategoryAndStatusAndDateBetween(category, status, start, end);
        return EventMapper.toDTOList(entities);
    }

    // ---------------------------------------------
    // Zählmethoden
    // ---------------------------------------------

    /**
     * Zählt alle Events mit der angegebenen {@link EventCategory}.
     *
     * @param category Kategorie zur Filterung
     * @return Anzahl der Events in dieser Kategorie
     * @see EventController#getEventCountByCategory(EventCategory) 
     */
    public long getTotalEventsByCategory(EventCategory category) {
        return eventRepository.countByCategory(category);
    }

    /**
     * Zählt alle Events mit dem angegebenen {@link EventStatus}.
     *
     * @param status Status zur Filterung
     * @return Anzahl der Events mit diesem Status
     * @see EventController#getEventCountByStatus(EventStatus) 
     */
    public long getTotalEventsByStatus(EventStatus status) {
        return eventRepository.countByStatus(status);
    }

    /**
     * Zählt alle Events, deren Datum innerhalb des angegebenen Zeitraums liegt.
     *
     * @param start Startdatum (inklusive)
     * @param end Enddatum (inklusive)
     * @return Anzahl der Events im Zeitraum
     * @see EventController#getEventCountByDateBetween(LocalDate, LocalDate)
     */
    public long getTotalEventsByDateBetween(LocalDate start, LocalDate end) {
        return eventRepository.countByDateBetween(start, end);
    }

    // ---------------------------------------------
    // Validierung
    // ---------------------------------------------

    /**
     * Validiert die ID eines Events.
     *
     * @param id ID des Events
     * @throws InvalidEventDataException Wenn die ID null ist
     */
    private void validateId(Long id) {
        if (id == null) {
            throw new InvalidEventDataException("ID darf nicht null sein.");
        }
    }

    /**
     * Validiert den Titel eines Events.
     *
     * @param title Titel des Events
     * @throws InvalidEventDataException Wenn der Titel null oder leer ist
     */
    private void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidEventDataException("Titel darf nicht null oder leer sein.");
        }
    }

    /**
     * Validiert das Datum eines Events.
     *
     * @param date Datum des Events
     * @throws InvalidEventDataException Wenn das Datum null ist
     * @throws FutureDateException Wenn das Datum in der Zukunft liegt
     */
    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new InvalidEventDataException("Datum darf nicht null sein.");
        }

        if (date.isAfter(LocalDate.now())) {
            throw new FutureDateException(date);
        }
    }

    /**
     * Validiert die Kategorie eines Events.
     *
     * @param category Kategorie des Events
     * @throws InvalidEventDataException Wenn die Kategorie null ist
     */
    private void validateCategory(EventCategory category) {
        if (category == null) {
            throw new InvalidEventDataException("Kategorie darf nicht null sein.");
        }
    }

    /**
     * Validiert den Längengrad eines Event-Standorts.
     *
     * @param longitude Längengrad des Event-Standorts
     * @throws InvalidEventDataException Wenn der Längengrad null ist
     * @throws CoordinateOutOfRangeException Wenn der Längengrad ausserhalb des gültigen Wertebereichs liegt
     */
    private void validateLongitude(Double longitude) {
        if (longitude == null) {
            throw new InvalidEventDataException("Longitude darf nicht null sein.");
        }

        if (longitude < -180 || longitude > 180) {
            throw new CoordinateOutOfRangeException("longitude", longitude);
        }
    }

    /**
     * Validiert den Breitengrad eines Event-Standorts.
     *
     * @param latitude Breitengrad des Event-Standorts
     * @throws InvalidEventDataException Wenn der Breitengrad null ist
     * @throws CoordinateOutOfRangeException Wenn der Breitengrad ausserhalb des gültigen Wertebereichs liegt
     */
    private void validateLatitude(Double latitude) {
        if (latitude == null) {
            throw new InvalidEventDataException("Latitude darf nicht null sein.");
        }

        if (latitude < -90 || latitude > 90) {
            throw new CoordinateOutOfRangeException("latitude", latitude);
        }
    }

    /**
     * Validiert den Status eines Events.
     *
     * @param status Status des Events
     * @throws InvalidEventDataException Wenn der Status null ist
     */
    private void validateStatus(EventStatus status) {
        if (status == null) {
            throw new InvalidEventDataException("Status darf nicht null sein.");
        }
    }

    /**
     * Validiert die Daten eines Events.
     *
     * @param title Titel des Events
     * @param date Datum des Events
     * @param category Kategorie des Events
     * @param longitude Längengrad des Event-Standorts
     * @param latitude Breitengrad des Event-Standorts
     * @param status Status des Events
     */
    private void validateEventData(String title, LocalDate date, EventCategory category, Double longitude, Double latitude, EventStatus status) {
        validateTitle(title);
        validateDate(date);
        validateCategory(category);
        validateLongitude(longitude);
        validateLatitude(latitude);
        validateStatus(status);
    }
}