/**
 * <h2>
 *     Exception-Handling für die EONET REST-API
 * </h2>
 *
 * <p>
 *     Dieses Paket enthält alle benutzerdefinierten Exceptions sowie den zentralen
 *     {@link com.wiss.backend.exception.GlobalExceptionHandler}, der diese
 *     global verarbeitet.
 * </p>
 *
 * <h3>
 *     Enthaltene Komponenten:
 * </h3>
 * <ul>
 *     <li>{@link com.wiss.backend.exception.EventNotFoundException} – Wird geworfen, wenn ein Event mit der angegebenen ID nicht gefunden wurde (HTTP 404).</li>
 *     <li>{@link com.wiss.backend.exception.InvalidEventDataException} – Wird geworfen, wenn Event-Daten unvollständig oder ungültig sind (HTTP 400).</li>
 *     <li>{@link com.wiss.backend.exception.FutureDateException} – Wird geworfen, wenn ein Event-Datum in der Zukunft liegt (HTTP 400).</li>
 *     <li>{@link com.wiss.backend.exception.CoordinateOutOfRangeException} – Wird geworfen, wenn Longitude oder Latitude ausserhalb des erlaubten Bereichs liegt (HTTP 400).</li>
 *     <li>{@link com.wiss.backend.exception.GlobalExceptionHandler} – Wandelt alle obigen Fehler in strukturierte JSON-Antworten im {@link com.wiss.backend.dto.ErrorResponseDTO}-Format um.</li>
 * </ul>
 *
 * <p>
 *     Der {@code GlobalExceptionHandler} sorgt dafür, dass Clients immer konsistente,
 *     verständliche Fehlermeldungen mit HTTP-Statuscode, Zeitstempel und URI erhalten.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-18
 */
package com.wiss.backend.exception;