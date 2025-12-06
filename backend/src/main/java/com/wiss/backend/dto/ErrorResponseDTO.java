package com.wiss.backend.dto;

import java.time.LocalDateTime;

/**
 * <h2>
 *     ErrorResponseDTO
 * </h2>
 * <p>
 *     Repräsentiert eine standardisierte Fehlerantwort für alle REST-Endpunkte.
 *     Dieses Objekt wird vom {@link com.wiss.backend.exception.GlobalExceptionHandler}
 *     verwendet und zurückgegeben, um dem Client strukturierte und einheitliche Fehlermeldungen zu liefern.
 * </p>
 *
 * <h3>
 *     Verwendung:
 * </h3>
 * <ul>
 *   <li>Als Antwort auf validierungs- oder logikbedingte Fehler (400)</li>
 *   <li>Bei nicht gefundenen Ressourcen (404)</li>
 *   <li>Bei technischen oder unbekannten Fehlern (500)</li>
 * </ul>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-18
 * @see com.wiss.backend.exception.GlobalExceptionHandler
 */
public class ErrorResponseDTO {

    /**
     * Kurzcode zur maschinenlesbaren Beschreibung des Fehlertyps
     * (z. B. {@code "EVENT_NOT_FOUND"}, {@code "INVALID_INPUT"}, {@code "INTERNAL_SERVER_ERROR"}).
     * @see #getError()
     */
    private String error;

    /**
     * Menschlich lesbare Fehlermeldung, geeignet für Anzeige im Frontend.
     * @see #getMessage()
     */
    private String message;

    /**
     * HTTP-Statuscode, der den Typ des Fehlers beschreibt (z. B. 400, 404, 500).
     * @see #getCode()
     */
    private int code;

    /**
     * Zeitstempel des Fehlers (z. B. gesetzt mit {@link java.time.LocalDateTime#now()}).
     * @see #getTimestamp()
     */
    private LocalDateTime timestamp;

    /**
     * Der URI-Pfad der fehlerhaften Anfrage (z. B. {@code /api/events/42}).
     * @see #getPath()
     */
    private String path;

    /**
     * Konstruktor zur Initialisierung aller Felder.
     *
     * @param error Kurzcode für den Fehlertyp.
     * @param message Benutzerfreundliche Fehlermeldung.
     * @param code HTTP-Statuscode.
     * @param timestamp Zeitpunkt des Fehlers.
     * @param path API-Pfad der fehlerhaften Anfrage.
     */
    public ErrorResponseDTO(String error, String message, int code, LocalDateTime timestamp, String path) {
        this.error = error;
        this.message = message;
        this.code = code;
        this.timestamp = timestamp;
        this.path = path;
    }

    // Getter & Setter
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
}
