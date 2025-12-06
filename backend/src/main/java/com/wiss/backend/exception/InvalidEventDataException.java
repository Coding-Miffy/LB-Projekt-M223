package com.wiss.backend.exception;

import org.springframework.web.context.request.WebRequest;

/**
 * <h2>
 *     Exception für ungültige oder unvollständige Event-Daten
 * </h2>
 *<p>
 *     Diese Exception wird geworfen, wenn Business-Logik-Regeln verletzt werden,
 *     die nicht bereits durch Bean Validation abgedeckt sind.
 * </p>
 * <ul>
 *     <li>
 *         Wird durch den GlobalExceptionHandler in HTTP 400 Bad Request umgewandelt.
 *     </li>
 * </ul>
 * 
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-17
 * @see GlobalExceptionHandler#handleInvalidEventData(InvalidEventDataException, WebRequest)
 */
public class InvalidEventDataException extends RuntimeException {

    /**
     * Erstellt eine neue InvalidEventDataException.
     *
     * @param message Beschreibung des Validierungsfehlers.
     */
    public InvalidEventDataException(String message) {
        super(message);
    }
}
