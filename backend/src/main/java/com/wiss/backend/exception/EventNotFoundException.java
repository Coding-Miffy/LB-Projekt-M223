package com.wiss.backend.exception;

import org.springframework.web.context.request.WebRequest;

/**
 * <h2>
 *     Exception für nicht gefundene Events
 * </h2>
 * <p>
 *     Diese Exception wird geworfen, wenn ein Event mit der
 *     angegebenen ID nicht gefunden werden konnte.
 * </p>
 * <ul>
 *     <li>
 *         Wird durch den GlobalExceptionHandler in HTTP 404 Not Found umgewandelt.
 *     </li>
 * </ul>
 * 
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-17
 * @see GlobalExceptionHandler#handleEventNotFound(EventNotFoundException, WebRequest)
 */
public class EventNotFoundException extends RuntimeException {

    /**
     * Die ID des nicht gefundenen Events.
     * <p>
     *     Diese ID wurde angefragt, konnte jedoch keinem vorhandenen Event
     *     in der Datenbank zugeordnet werden.
     * </p>
     */
    private final Long eventId;

    /**
     * Erstellt eine neue EventNotFoundException.
     *
     * @param eventId Die ID des Events, dass nicht gefunden werden konnte.
     */
    public EventNotFoundException(Long eventId) {
        super("Event mit ID " + eventId + " wurde nicht gefunden");
        this.eventId = eventId;
    }

    /**
     * @return Die ID des Events, dass nicht gefunden werden konnte.
     */
    public Long getEventId() {
        return eventId;
    }
}
