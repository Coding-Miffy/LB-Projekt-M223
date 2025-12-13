/**
 * <h2>
 *     Data Transfer Objects (DTOs) für die REST-API
 * </h2>
 *
 * <p>
 *     Dieses Package enthält sämtliche Data Transfer Objects (DTOs), die für die
 *     Kommunikation zwischen Client (z. B. dem React-Frontend) und dem Spring-Boot-Backend
 *     verwendet werden. DTOs bilden eine sichere und schlanke Schnittstelle und
 *     entkoppeln die API vollständig vom Persistenzmodell
 *     ({@link com.wiss.backend.entity}).
 * </p>
 *
 * <h3>
 *     Enthaltene DTO-Klassen::
 * </h3>
 * <ul>
 *     <li>{@link com.wiss.backend.dto.LoginRequestDTO} – Eingabemodell für Login-Daten</li>
 *     <li>{@link com.wiss.backend.dto.LoginResponseDTO} – Rückgabeobjekt nach erfolgreicher Authentifizierung (inkl. JWT)</li>
 *     <li>{@link com.wiss.backend.dto.RegisterRequestDTO} – Eingabeobjekt für Benutzerregistrierungen</li>
 *     <li>{@link com.wiss.backend.dto.RegisterResponseDTO} – Ausgabeobjekt für erfolgreiche Registrierungen</li>
 *     <li>{@link com.wiss.backend.dto.EventDTO} – DTO zur Anzeige und Rückgabe eines Naturereignisses</li>
 *     <li>{@link com.wiss.backend.dto.EventFormDTO} – Eingabe-DTO für Formulare zum Erstellen oder Bearbeiten von Events</li>
 *     <li>{@link com.wiss.backend.dto.ErrorResponseDTO} – Einheitliches Fehlerformat für alle API-Fehler</li>
 * </ul>
 *
 * <h3>
 *     Validierung:
 * </h3>
 * <p>
 *     Viele DTOs sind mit Bean Validation versehen (z. B. {@code @NotBlank}, {@code @Email}, {@code @Size}),
 *     um ungültige Eingaben möglichst früh serverseitig zu erkennen.
 * </p>
 *
 * <h3>
 *     Rollen im Architekturkonzept:
 * </h3>
 * <ul>
 *     <li>Werden von den Controllern als Eingabemodell und/oder Ausgabemodell verwendet.</li>
 *     <li>Ermöglichen eine klare Trennung zwischen Datenbankmodellen und API-Modellen.</li>
 *     <li>Schützen interne Entitäten vor ungewollter Serialisierung und Manipulation.</li>
 * </ul>
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
 * @version 2.0
 * @since 2025-12-12
 */
package com.wiss.backend.dto;