package com.wiss.backend.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.wiss.backend.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <h2>
 *     GlobalExceptionHandler
 * </h2>
 * <p>
 *     Zentraler Exception-Handler für alle REST-Controller.
 *     Alle auftretenden Fehler werden hier zu strukturierten JSON-Fehlermeldungen
 *     umgewandelt, die dem Client eine klare und einheitliche Rückmeldung geben.
 * </p>
 * <p>
 *     Die Klasse ist mit {@code @ControllerAdvice} annotiert, wodurch sie alle
 *     Exceptions in Controllern global abfangen kann.
 * </p>
 * <p>
 *     Die Rückgaben erfolgen im Format {@link ErrorResponseDTO}, inklusive Statuscode,
 *     Fehlertyp, Nachricht, Zeitstempel und URI-Pfad.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.1
 * @since 2025-07-20
 * @see ErrorResponseDTO
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Behandelt {@link EventNotFoundException}, wenn ein Event mit der
     * angegebenen ID nicht existiert.
     *
     * @param ex Die ausgelöste EventNotFoundException.
     * @param request Der zugehörige HTTP-Request.
     * @return Strukturierte Fehlerantwort mit HTTP-Status 404 (Not Found).
     */
    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleEventNotFound(EventNotFoundException ex, WebRequest request) {
        return buildError(
                "EVENT_NOT_FOUND",
                ex.getMessage(),
                404,
                request
        );
    }

    /**
     * Behandelt {@link FutureDateException}, wenn ein Event-Datum
     * in der Zukunft liegt.
     *
     * @param ex Die ausgelöste FutureDateException.
     * @param request Der zugehörige HTTP-Request.
     * @return Strukturierte Fehlerantwort mit HTTP-Status 400 (Bad Request).
     */
    @ExceptionHandler(FutureDateException.class)
    public ResponseEntity<ErrorResponseDTO> handleFutureDate(FutureDateException ex, WebRequest request) {
        return buildError(
                "INVALID_DATE",
                ex.getMessage(),
                400,
                request
        );
    }

    /**
     * Behandelt {@link CoordinateOutOfRangeException}, wenn eine Koordinate
     * ausserhalb des gültigen Wertebereichs liegt.
     *
     * @param ex Die ausgelöste CoordinateOutOfRangeException.
     * @param request Der zugehörige HTTP-Request.
     * @return Strukturierte Fehlerantwort mit HTTP-Status 400 (Bad Request).
     */
    @ExceptionHandler(CoordinateOutOfRangeException.class)
    public ResponseEntity<ErrorResponseDTO> handleCoordinateOutOfRange(CoordinateOutOfRangeException ex, WebRequest request) {
        return buildError(
                "INVALID_COORDINATE",
                ex.getMessage(),
                400,
                request
        );
    }

    /**
     * Behandelt {@link InvalidEventDataException}, wenn Business-Logik-Regeln verletzt werden,
     * die nicht bereits durch Bean Validation abgedeckt sind.
     *
     * @param ex Die ausgelöste InvalidEventDataException.
     * @param request Der zugehörige HTTP-Request.
     * @return Strukturierte Fehlerantwort mit HTTP-Status 400 (Bad Request).
     */
    @ExceptionHandler(InvalidEventDataException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidEventData(InvalidEventDataException ex, WebRequest request) {
        return buildError(
                "INVALID_EVENT_DATA",
                ex.getMessage(),
                400,
                request
        );
    }

    /**
     * Behandelt {@link MethodArgumentTypeMismatchException}, die auftritt, wenn z. B.
     * eine ungültige ID oder ein ungültiger Enum-Wert in einem {@code @PathVariable}
     * oder {@code @RequestParam} übergeben wurde.
     *
     * @param ex Die ausgelöste MethodArgumentTypeMismatchException.
     * @param request Der zugehörige HTTP-Request.
     * @return Strukturierte Fehlerantwort mit HTTP-Status 400 (Bad Request).
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String message = String.format("Ungültiger Wert für '%s': '%s'. Erwartet: %s.",
                ex.getName(),
                ex.getValue(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unbekannt");

        return buildError(
                "TYPE_MISMATCH",
                message,
                400,
                request);
    }

    /**
     * Behandelt {@link HttpMessageNotReadableException}, z. B. bei ungültigem JSON.
     * Prüft speziell auf ungültige Enum-Werte (Kategorie, Status).
     *
     * @param ex Die ausgelöste HttpMessageNotReadableException.
     * @param request Der zugehörige HTTP-Request.
     * @return Strukturierte Fehlerantwort mit HTTP-Status 400 (Bad Request).
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, WebRequest request) {
        // Überprüfen, ob die Ursache eine InvalidFormatException ist (Enum-Fehler)
        if (ex.getCause() instanceof InvalidFormatException cause) {
            String fieldName = cause.getPath().getFirst().getFieldName();
            String invalidValue = cause.getValue().toString();

            if ("category".equals(fieldName)) {
                return buildError(
                        "INVALID_CATEGORY",
                        "Kategorie '" + invalidValue + "' ist nicht gültig. " +
                                "Gültige Kategorien: drought, dustHaze, earthquakes, floods, landslides, manmade, seaLakeIce, severeStorms, snow, volcanoes, waterColor, wildfires",
                        400,
                        request
                );
            }

            if ("status".equals(fieldName)) {
                return buildError(
                        "INVALID_STATUS",
                        "Status '" + invalidValue + "' ist nicht gültig. Erlaubt sind: open, closed.",
                        400,
                        request
                );
            }
        }

        // Allgemeiner Fallback
        return buildError(
                "MALFORMED_JSON",
                "Die Anfrage konnte nicht gelesen werden: " + ex.getMessage(),
                400,
                request
        );
    }

    /**
     * Behandelt Validierungsfehler von {@code @Valid}-annotierten Feldern.
     * Gibt pro Feld die konkrete Fehlermeldung zurück.
     *
     * @param ex Die ausgelöste MethodArgumentNotValidException mit Details zu allen fehlgeschlagenen Validierungen.
     * @param request Der ursprüngliche HTTP-Request
     * @return Strukturierte Fehlerantwort mit HTTP-Status 400 (Bad Request) und detaillierten Validierungsfehlern.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        // Sammelt alle Validierungsfehler
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // Erstellt benutzerfreundliche Fehlermeldung
        StringBuilder message = new StringBuilder("Validation exception: ");
        errors.forEach((field, error) -> message.append(field)
                .append(" - ").append(error).append("; "));

        return buildError(
                "VALIDATION_ERROR",
                message.toString(),
                400,
                request
        );
    }

    /**
     * Fängt alle nicht explizit behandelten Exceptions ab.
     * Gibt eine 500-Fehlermeldung ohne technische Details zurück.
     *
     * @param ex Die ausgelöste sonstige Exception.
     * @param request Der zugehörige HTTP-Request.
     * @return Strukturierte Fehlerantwort mit HTTP-Status 500 (Internal Server Error).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneralException(Exception ex, WebRequest request) {
        // Internes Logging
        System.err.println("Unhandled exception: " + ex.getMessage());
        ex.printStackTrace();

        return buildError(
                "INTERNAL_SERVER_ERROR",
                "Ein unerwarteter Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.",
                500,
                request
        );
    }

    /**
     * Erstellt eine strukturierte Fehlerantwort im Standardformat.
     * <p>
     *     Wird von allen spezifischen Exception-Handlern verwendet, um Redundanz zu vermeiden.
     *     Enthält Fehlertyp, Nachricht, HTTP-Status, Zeitstempel und URI-Pfad.
     * </p>
     *
     * @param type Technischer Fehlercode, z. B. "INVALID_STATUS".
     * @param message Verständliche Fehlernachricht für den Client.
     * @param status HTTP-Statuscode, z. B. 400 oder 404.
     * @param request Der zugehörige HTTP-Request zur Pfadextraktion.
     * @return Strukturierte Fehlerantwort mit {@link ErrorResponseDTO}.
     */
    private ResponseEntity<ErrorResponseDTO> buildError(String type, String message, int status, WebRequest request) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                type,
                message,
                status,
                LocalDateTime.now(),
                extractPath(request)
        );
        return new ResponseEntity<>(error, HttpStatus.valueOf(status));
    }

    /**
     * Extrahiert den URI-Pfad aus dem {@link WebRequest}, z. B. "/api/events/3".
     * Entfernt dabei das Standardpräfix "uri=".
     *
     * @param request Der HTTP-Request.
     * @return Der URI-Pfad der Anfrage.
     */
    private String extractPath(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }
}
