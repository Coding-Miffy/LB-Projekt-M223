package com.wiss.backend.controller;

import com.wiss.backend.dto.EventDTO;
import com.wiss.backend.dto.EventFormDTO;
import com.wiss.backend.entity.Event;
import com.wiss.backend.model.EventCategory;
import com.wiss.backend.model.EventStatus;
import com.wiss.backend.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDate;
import java.util.List;

/**
 * <h2>
 *     Controller für Naturereignisse
 * </h2>
 * <p>
 *     REST-Controller zur Verwaltung von Naturereignissen.
 *     Bietet Endpunkte zum Erstellen, Bearbeiten, Filtern, Zählen und Löschen von Events.
 *     Die Events können sowohl im klassischen JSON-Format (DTO) als auch für Formulare verarbeitet werden.
 * </p>
 *
 * <h3>
 *     Funktionsgruppen:
 * </h3>
 * <ul>
 *     <li><b>DTO</b>: Standard-CRUD über EventDTO</li>
 *     <li><b>Filter</b>: Filterung nach Kategorie, Status, Datum</li>
 *     <li><b>Statistiken</b>: Anzahl- und Statistikabfragen</li>
 *     <li><b>Formulardaten</b>: Verarbeitung von Event-Entitäten für Frontend-Formulare</li>
 * </ul>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-20
 *
 * @see EventService
 * @see EventDTO
 * @see EventFormDTO
 * @see Event
 * @see EventCategory
 * @see EventStatus
 */
@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    /**
     * Konstruktor mit Abhängigkeitsinjektion für den EventService
     *
     * @param eventService Serviceklasse zur Geschäftslogik von Events
     */
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Gibt eine Liste aller verfügbaren Events als {@link EventDTO} zurück.
     *
     * @return Liste aller Events
     */
    @GetMapping
    @Operation(
            summary = "Alle Events abrufen",
            description = "Gibt alle verfügbaren Events zurück"
    )
    @ApiResponse(responseCode = "200", description = "Events erfolgreich abgerufen")
    @Tag(name = "Events – DTO", description = "Standard-CRUD-API für Events über DTO")
    public List<EventDTO> getAllEvents() {
        return eventService.getAllEventsAsDTO();
    }

    /**
     * Gibt ein einzelnes Event basierend auf der übergebenen ID zurück.
     *
     * @param id ID des gesuchten Events
     * @return EventDTO mit den Details des Events
     * @throws MethodArgumentTypeMismatchException bei ungültiger ID (400)
     * @throws com.wiss.backend.exception.EventNotFoundException wenn Event nicht existiert (404)
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Event nach ID abrufen",
            description = "Gibt ein spezifisches Event anhand der ID zurück"
    )
    @ApiResponse(responseCode = "200", description = "Event gefunden")
    @ApiResponse(responseCode = "400", description = "Ungültige ID übergeben")
    @ApiResponse(responseCode = "404", description = "Event nicht gefunden")
    @Tag(name = "Events – DTO", description = "Standard-CRUD-API für Events über DTO")
    public EventDTO getEventById(
            @Parameter(description = "ID des Events", example = "1", required = true)
            @PathVariable Long id) {
        return eventService.getEventByIdAsDTO(id);
    }

    /**
     * Gibt alle Events einer bestimmten {@link EventCategory} zurück.
     *
     * @param category Gewählte Kategorie
     * @return Liste gefilterter Events
     * @throws MethodArgumentTypeMismatchException bei ungültiger Kategorie (400)
     */
    @GetMapping("/categories/{category}")
    @Operation(
            summary = "Events nach Kategorien abrufen",
            description = "Gibt alle Events einer Kategorie zurück"
    )
    @ApiResponse(responseCode = "200", description = "Events erfolgreich abgerufen")
    @ApiResponse(responseCode = "400", description = "Ungültige Kategorie übergeben")
    @Tag(name = "Events – Filter", description = "Filterfunktionen für Kategorie, Status, Datum & Kombinationen")
    public List<EventDTO> getEventsByCategory(
            @Parameter(description = "Kategorie", example = "wildfires", required = true)
            @PathVariable EventCategory category) {
        return eventService.getEventsByCategoryAsDTO(category);
    }

    /**
     * Gibt alle Events mit dem angegebenen {@link EventStatus} zurück.
     *
     * @param status Statuswert
     * @return Liste gefilterter Events
     * @throws MethodArgumentTypeMismatchException bei ungültigem Status (400)
     */
    @GetMapping("/status/{status}")
    @Operation(
            summary = "Events nach Status abrufen",
            description = "Gibt alle Events eines Status zurück"
    )
    @ApiResponse(responseCode = "200", description = "Events erfolgreich abgerufen")
    @ApiResponse(responseCode = "400", description = "Ungültiger Status übergeben")
    @Tag(name = "Events – Filter", description = "Filterfunktionen für Kategorie, Status, Datum & Kombinationen")
    public List<EventDTO> getEventsByStatus(
            @Parameter(description = "Status", example = "open", required = true)
            @PathVariable EventStatus status) {
        return eventService.getEventsByStatusAsDTO(status);
    }

    /**
     * Gibt alle Events zurück, die dem angegebenen {@link LocalDate} entsprechen.
     *
     * @param date Zieldatum
     * @return Liste gefilterter Events
     * @throws MethodArgumentTypeMismatchException bei ungültigem Datum (400)
     */
    @GetMapping("/date/{date}")
    @Operation(
            summary = "Events nach Datum abrufen",
            description = "Gibt alle Events eines bestimmten Datums zurück"
    )
    @ApiResponse(responseCode = "200", description = "Events erfolgreich abgerufen")
    @ApiResponse(responseCode = "400", description = "Ungültiges Datum übergeben")
    @Tag(name = "Events – Filter", description = "Filterfunktionen für Kategorie, Status, Datum & Kombinationen")
    public List<EventDTO> getEventsByDate(
            @Parameter(description = "Datum", example = "2025-07-20", required = true)
            @PathVariable LocalDate date) {
        return eventService.getEventsByDateAsDTO(date);
    }

    /**
     * Gibt die Gesamtanzahl aller gespeicherten Events zurück.
     *
     * @return Anzahl aller Events
     */
    @GetMapping("/count")
    @Operation(
            summary = "Anzahl aller Events abrufen",
            description = "Gibt die Anzahl aller Events zurück"
    )
    @ApiResponse(responseCode = "200", description = "Anzahl Events erfolgreich abgerufen")
    @Tag(name = "Events – Statistiken", description = "Zählfunktionen nach Kategorie, Status oder Zeitraum")
    public long getEventCount() {
        return eventService.getTotalEventsCount();
    }

    /**
     * Speichert ein neues Event basierend auf den übergebenen {@link EventDTO}-Daten.
     *
     * @param eventDTO Daten des neuen Events
     * @return Erstelltes Event mit ID
     * @throws com.wiss.backend.exception.InvalidEventDataException bei unvollständigen Daten (400)
     * @throws org.springframework.http.converter.HttpMessageNotReadableException bei ungültigen Daten (400)
     */
    @PostMapping
    @Operation(
            summary = "Neues Event erstellen",
            description = "Erstellt ein neues EONET-Event"
    )
    @ApiResponse(responseCode = "200", description = "Event erfolgreich erstellt")
    @ApiResponse(responseCode = "400", description = "Ungültige oder unvollständige Daten übergeben")
    @Tag(name = "Events – DTO", description = "Standard-CRUD-API für Events über DTO")
    public EventDTO createEvent(
            @Valid @RequestBody EventDTO eventDTO) {
        return eventService.createEvent(eventDTO);
    }

    /**
     * Aktualisiert ein bestehendes Event anhand der ID und der übergebenen {@link EventDTO}-Daten.
     *
     * @param id ID des zu aktualisierenden Events
     * @param eventDTO Neue Eventdaten
     * @return Aktualisiertes Event
     * @throws com.wiss.backend.exception.InvalidEventDataException bei unvollständigen Daten (400)
     * @throws org.springframework.http.converter.HttpMessageNotReadableException bei ungültigen Daten (400)
     * @throws com.wiss.backend.exception.EventNotFoundException wenn Event nicht existiert (404)
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Event aktualisieren",
            description = "Aktualisiert ein bestehendes Event"
    )
    @ApiResponse(responseCode = "200", description = "Event erfolgreich aktualisiert")
    @ApiResponse(responseCode = "400", description = "Ungültige oder unvollständige Daten übergeben")
    @ApiResponse(responseCode = "404", description = "Event nicht gefunden")
    @Tag(name = "Events – DTO", description = "Standard-CRUD-API für Events über DTO")
    public EventDTO updateEvent(
            @Parameter(description = "ID des Events", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody EventDTO eventDTO) {
        return eventService.updateEvent(id, eventDTO);
    }

    /**
     * Löscht ein Event anhand der übergebenen ID.
     *
     * @param id ID des zu löschenden Events
     * @throws com.wiss.backend.exception.EventNotFoundException wenn Event nicht existiert (404)
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Event löschen",
            description = "Löscht ein bestehendes Event"
    )
    @ApiResponse(responseCode = "200", description = "Event erfolgreich gelöscht")
    @ApiResponse(responseCode = "404", description = "Event nicht gefunden")
    @Tag(name = "Events – DTO", description = "Standard-CRUD-API für Events über DTO")
    public void deleteEvent(
            @Parameter(description = "ID des Events", example = "1", required = true)
            @PathVariable Long id) {
        eventService.deleteEvent(id);
    }

    /**
     * Filtert Events nach Kombination von Kategorie, Status und Zeitraum.
     * Gibt alle Events zurück, falls keine Filter gesetzt sind.
     *
     * @param category Kategorie
     * @param status Status
     * @param start Startdatum
     * @param end Enddatum
     * @return Liste der gefilterten Events
     * @throws MethodArgumentTypeMismatchException bei ungültigen Daten (400)
     */
    @GetMapping("/filter")
    @Operation(
            summary = "Events filtern",
            description = "Filtert bestehende Events"
    )
    @ApiResponse(responseCode = "200", description = "Events erfolgreich gefiltert")
    @ApiResponse(responseCode = "400", description = "Ungültige Daten übergeben")
    @Tag(name = "Events – Filter", description = "Filterfunktionen für Kategorie, Status, Datum & Kombinationen")
    public List<EventDTO> getEventsByFilter(
            @Parameter(description = "Kategorie", example = "wildfires")
            @RequestParam(required = false) EventCategory category,
            @Parameter(description = "Status", example = "open")
            @RequestParam(required = false) EventStatus status,
            @Parameter(description = "Startdatum", example = "2025-07-01")
            @RequestParam(required = false) LocalDate start,
            @Parameter(description = "Enddatum", example = "2025-07-15")
            @RequestParam(required = false) LocalDate end) {
        if (category != null && status != null && start != null && end != null) {
            return eventService.getEventsByCategoryAndStatusAndDateBetween(category, status, start, end);
        } else if (category != null && start != null && end != null) {
            return eventService.getEventsByCategoryAndDateBetween(category, start, end);
        } else if (category != null && status != null) {
            return eventService.getEventsByCategoryAndStatus(category, status);
        } else if (status != null && start != null && end != null) {
            return eventService.getEventsByStatusAndDateBetween(status, start, end);
        } else if (start != null && end != null) {
            return eventService.getEventsByDateBetween(start, end);
        } else {
            return eventService.getAllEventsAsDTO();
        }
    }

    /**
     * Gibt die Anzahl aller Events einer Kategorie zurück.
     *
     * @param category Kategorie
     * @return Anzahl der Events mit dieser Kategorie
     * @throws MethodArgumentTypeMismatchException bei ungültiger Kategorie (400)
     */
    @GetMapping("/stats/categories/{category}")
    @Operation(
            summary = "Anzahl aller Events einer Kategorie abrufen",
            description = "Gibt die Anzahl aller Events einer Kategorie zurück"
    )
    @ApiResponse(responseCode = "200", description = "Anzahl Events erfolgreich abgerufen")
    @ApiResponse(responseCode = "400", description = "Ungültige Kategorie übergeben")
    @Tag(name = "Events – Statistiken", description = "Zählfunktionen nach Kategorie, Status oder Zeitraum")
    public long getEventCountByCategory(
            @Parameter(description = "Kategorie", example = "wildfires", required = true)
            @PathVariable EventCategory category) {
        return eventService.getTotalEventsByCategory(category);
    }

    /**
     * Gibt die Anzahl aller Events eines Status zurück.
     *
     * @param status Status
     * @return Anzahl der Events mit diesem Status
     * @throws MethodArgumentTypeMismatchException bei ungültigem Status (400)
     */
    @GetMapping("/stats/status/{status}")
    @Operation(
            summary = "Anzahl aller Events eines Status abrufen",
            description = "Gibt die Anzahl aller Events eines Status zurück"
    )
    @ApiResponse(responseCode = "200", description = "Anzahl Events erfolgreich abgerufen")
    @ApiResponse(responseCode = "400", description = "Ungültiger Status übergeben")
    @Tag(name = "Events – Statistiken", description = "Zählfunktionen nach Kategorie, Status oder Zeitraum")
    public long getEventCountByStatus(
            @Parameter(description = "Status", example = "open", required = true)
            @PathVariable EventStatus status) {
        return eventService.getTotalEventsByStatus(status);
    }

    /**
     * Gibt die Anzahl aller Events innerhalb eines Datumsbereichs zurück.
     *
     * @param start Startdatum
     * @param end Enddatum
     * @return Anzahl der Events im Zeitraum
     * @throws MethodArgumentTypeMismatchException bei ungültigen Daten (400)
     */
    @GetMapping("/stats/date/{start}/{end}")
    @Operation(
            summary = "Anzahl aller Events eines Zeitraums abrufen",
            description = "Gibt die Anzahl aller Events innerhalb eines Zeitraums zurück"
    )
    @ApiResponse(responseCode = "200", description = "Anzahl Events erfolgreich abgerufen")
    @ApiResponse(responseCode = "400", description = "Ungültiges Datum übergeben")
    @Tag(name = "Events – Statistiken", description = "Zählfunktionen nach Kategorie, Status oder Zeitraum")
    public long getEventCountByDateBetween(
            @Parameter(description = "Startdatum", example = "2025-07-01", required = true)
            @PathVariable LocalDate start,
            @Parameter(description = "Enddatum", example = "2025-07-15", required = true)
            @PathVariable LocalDate end) {
        return eventService.getTotalEventsByDateBetween(start, end);
    }

    /**
     * Gibt alle Events als Formulardaten zurück (für das Frontend optimiert).
     *
     * @return Liste aller Events als EventFormDTO
     */
    @GetMapping("/all")
    @Operation(
            summary = "Alle Events als Formulardaten abrufen",
            description = "Gibt alle Events als strukturierte Daten für Formulare zurück"
    )
    @ApiResponse(responseCode = "200", description = "Events erfolgreich abgerufen")
    @Tag(name = "Events – Formulardaten (Frontend)", description = "Spezielle Endpunkte für die Formularverwendung im Frontend")
    public List<EventFormDTO> getAllFormEvents() {
        return eventService.getAllEventsAsFormDTO();
    }

    /**
     * Gibt ein spezifisches Event als EventFormDTO zurück.
     *
     * @param id ID des Events
     * @return EventFormDTO zum Bearbeiten im Frontend-Formular
     * @throws MethodArgumentTypeMismatchException bei ungültiger ID (400)
     * @throws com.wiss.backend.exception.EventNotFoundException wenn Event nicht existiert (404)
     */
    @GetMapping("/{id}/edit")
    @Operation(
            summary = "Einzelnes Event für Bearbeitung im Formular abrufen",
            description = "Gibt ein spezifisches Event anhand der ID zurück, optimiert für das Frontend-Formular"
    )
    @ApiResponse(responseCode = "200", description = "Event gefunden")
    @ApiResponse(responseCode = "400", description = "Ungültige ID übergeben")
    @ApiResponse(responseCode = "404", description = "Event nicht gefunden")
    @Tag(name = "Events – Formulardaten (Frontend)", description = "Spezielle Endpunkte für die Formularverwendung im Frontend")
    public EventFormDTO getEventByIdForEdit(
            @Parameter(description = "ID des Events", example = "1", required = true)
            @PathVariable Long id) {
        return eventService.getEventByIdAsFormDTO(id);
    }

    /**
     * Erstellt ein neues Event über das Frontend-Formular.
     *
     * @param event zu erstellendes Event (Formulardaten)
     * @return erstelltes Event als EventFormDTO
     * @throws com.wiss.backend.exception.InvalidEventDataException bei unvollständigen Daten (400)
     * @throws org.springframework.http.converter.HttpMessageNotReadableException bei ungültigen Daten (400)
     */
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Neues Event erstellen über Formular",
            description = "Erstellt ein neues EONET-Event über das Frontend-Formular"
    )
    @ApiResponse(responseCode = "201", description = "Event erfolgreich erstellt")
    @ApiResponse(responseCode = "400", description = "Ungültige oder unvollständige Daten übergeben")
    @Tag(name = "Events – Formulardaten (Frontend)", description = "Spezielle Endpunkte für die Formularverwendung im Frontend")
    public EventFormDTO createEventFromForm(
            @Parameter(description = "Event-Daten", required = true)
            @Valid @RequestBody Event event) {
        return eventService.createEventFromForm(event);
    }

    /**
     * Aktualisiert ein Event über das Frontend-Formular.
     *
     * @param id ID des Events
     * @param event neue Eventdaten
     * @return aktualisiertes Event als EventFormDTO
     * @throws com.wiss.backend.exception.InvalidEventDataException bei unvollständigen Daten (400)
     * @throws org.springframework.http.converter.HttpMessageNotReadableException bei ungültigen Daten (400)
     * @throws com.wiss.backend.exception.EventNotFoundException wenn Event nicht existiert (404)
     */
    @PutMapping("/{id}/update")
    @Operation(
            summary = "Event aktualisieren über Formular",
            description = "Aktualisiert ein bestehendes Event über das Frontend-Formular"
    )
    @ApiResponse(responseCode = "200", description = "Event erfolgreich aktualisiert")
    @ApiResponse(responseCode = "400", description = "Ungültige oder unvollständige Daten übergeben")
    @ApiResponse(responseCode = "404", description = "Event nicht gefunden")
    @Tag(name = "Events – Formulardaten (Frontend)", description = "Spezielle Endpunkte für die Formularverwendung im Frontend")
    public EventFormDTO updateEventFromForm(
            @Parameter(description = "ID des Events", example = "1", required = true)
            @PathVariable Long id,
            @Parameter(description = "Event-Daten", required = true)
            @Valid @RequestBody Event event) {
        return eventService.updateEventFromForm(id, event);
    }
}