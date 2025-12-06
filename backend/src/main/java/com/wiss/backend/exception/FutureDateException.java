package com.wiss.backend.exception;

import org.springframework.web.context.request.WebRequest;
import java.time.LocalDate;

/**
 * <h2>
 *     Exception für in der Zukunft liegende Event-Daten
 * </h2>
 * <p>
 *     Diese Exception wird geworfen, wenn ein Event-Datum in der Zukunft liegt.
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
 * @see GlobalExceptionHandler#handleFutureDate(FutureDateException, WebRequest)
 */
public class FutureDateException extends RuntimeException {

    /**
     * Das Datum, das in der Zukunft liegt und die Ausnahme ausgelöst hat.
     *<p>
     *     Dieses Feld enthält das von der Benutzer:in übermittelte Datum,
     *     das geprüft wurde und zum Auslösen der Exception führte,
     *     da es in der Zukunft liegt.
     *</p>
     */
    private final LocalDate date;

    /**
     * Erstellt eine neue FutureDateException.
     *
     * @param date Datum, das in der Zukunft liegt.
     */
    public FutureDateException(LocalDate date) {
        super("Das Datum '" + date + "' liegt in der Zukunft und ist nicht erlaubt.");
        this.date = date;
    }

    /**
     * @return Das ungültige Datum.
     */
    public LocalDate getDate() {
        return date;
    }
}
