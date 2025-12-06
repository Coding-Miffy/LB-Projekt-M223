package com.wiss.backend.exception;

import org.springframework.web.context.request.WebRequest;

/**
 * <h2>
 *     Exception für ausserhalb des Wertebereichs liegende Koordinaten
 * </h2>
 * <p>
 *     Diese Exception wird geworfen, wenn eine Koordinate -
 *     sprich Längen- oder Breitengrad - ausserhalb des gültigen
 *     Wertebereichs liegt.
 * </p>
 *<ul>
 *     <li>
 *         Wird durch den GlobalExceptionHandler in HTTP 400 Bad Request umgewandelt.
 *     </li>
 *</ul>
 * 
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-17
 * @see GlobalExceptionHandler#handleCoordinateOutOfRange(CoordinateOutOfRangeException, WebRequest)
 */
public class CoordinateOutOfRangeException extends RuntimeException{

    /**
     * Der Typ der betroffenen Koordinate
     * <p>
     * Erwartete Werte sind "longitude" (Längengrad) oder "latitude" (Breitengrad).
     * Dieses Feld dient zur Unterscheidung, welche Art von Koordinate den Fehler ausgelöst hat.
     * </p>
     */
    private final String coordinateType;

    /**
     * Der ungültige numerische Wert der Koordinate
     * <p>
     * Dieser Wert liegt ausserhalb des zulässigen Bereichs:
     * Für Längengrade [-180, 180], für Breitengrade [-90, 90].
     * </p>
     */
    private final double value;

    /**
     * Erstellt eine neue CoordinateOutOfRangeException.
     *
     * @param coordinateType "longitude" oder "latitude".
     * @param value Der ungültige Wert.
     */
    public CoordinateOutOfRangeException(String coordinateType, double value) {
        super("Ungültiger Wert für " + coordinateType + ": " + value +
                ". Erlaubte Werte: " +
                (coordinateType.equals("latitude") ? "-90 bis 90" : "-180 bis 180"));
        this.coordinateType = coordinateType;
        this.value = value;
    }

    /**
     * @return Der Typ der Koordinate ("longitude" oder "latitude").
     */
    public String getCoordinateType() {
        return coordinateType;
    }

    /**
     * @return Der ungültige Wert.
     */
    public double getValue() {
        return value;
    }
}
