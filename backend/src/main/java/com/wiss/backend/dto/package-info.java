/**
 * <h2>
 *     Data Transfer Objects (DTOs) für die REST-API
 * </h2>
 *
 * <p>
 *     Dieses Package enthält sämtliche Data Transfer Objects (DTOs), die zur Kommunikation
 *     zwischen Client (z. B. Frontend) und Server verwendet werden. DTOs dienen der
 *     Kapselung von Daten, die über REST-Endpunkte empfangen oder zurückgegeben werden.
 *     Sie bilden eine schlanke und sichere Schnittstelle zur Präsentationsschicht und
 *     entkoppeln diese von den internen Entitäten und Geschäftslogiken.
 * </p>
 *
 * <h3>
 *     Enthaltene Klassen:
 * </h3>
 * <ul>
 *     <li>{@link com.wiss.backend.dto.EventDTO} – Repräsentiert ein Naturereignis für die Anzeige im Frontend</li>
 *     <li>{@link com.wiss.backend.dto.EventFormDTO} – Eingabeobjekt für das Erfassen oder Bearbeiten eines Events im Frontend. Validiert Formulardaten und kann in ein {@link com.wiss.backend.dto.EventDTO} umgewandelt werden.</li>
 *     <li>{@link com.wiss.backend.dto.ErrorResponseDTO} – Einheitliches Format für Fehlermeldungen, z. B. bei Validierungsfehlern oder Ausnahmen</li>
 * </ul>
 *
 * <h3>
 *     Validierung:
 * </h3>
 * <p>
 *     Alle Form- und Rückgabeobjekte sind mit Bean Validation Annotationen versehen
 *     (z. B. {@code @NotNull}, {@code @Size}, {@code @PastOrPresent}),
 *     um Eingaben frühzeitig serverseitig abzufangen.
 * </p>
 *
 * <h3>
 *     Schnittstellen und Konvertierung:
 * </h3>
 * <p>
 *     Das Package enthält Konvertierungsmethoden zwischen den Form- und Anzeige-DTOs,
 *     z. B. {@code toEventDTO()} und {@code fromEventDTO()}.
 * </p>
 *
 * <h3>
 *     Zugehörige Packages:
 * </h3>
 * <ul>
 *     <li>{@code com.wiss.backend.entity} – enthält die JPA-Entities</li>
 *     <li>{@code com.wiss.backend.controller} – verwendet DTOs als Eingabe- und Rückgabetypen</li>
 *     <li>{@code com.wiss.backend.exception} – stellt globale Fehlerbehandlung und strukturierte Fehlermeldungen bereit</li>
 * </ul>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-19
 */
package com.wiss.backend.dto;