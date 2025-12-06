/**
 * <h2>
 *     Modellierung von Domänenwerten für Naturereignisse
 * </h2>
 *
 * <p>
 *     Dieses Paket enthält domänenspezifische Enumerationen, die in der Anwendung zur
 *     Kategorisierung und Statusverwaltung von Naturereignissen verwendet werden.
 *     Sie dienen als Typsicherheit für die Entity- und DTO-Klassen.
 * </p>
 *
 * <h3>
 *     Enthaltene Komponenten:
 * </h3>
 * <ul>
 *     <li>{@link com.wiss.backend.model.EventCategory} – Definiert die Kategorie eines Naturereignisses
 *         gemäss der NASA-EONET-API (z. B. floods, volcanoes).</li>
 *     <li>{@link com.wiss.backend.model.EventStatus} – Gibt an, ob ein Event aktuell <code>open</code>
 *         oder bereits <code>closed</code> ist.</li>
 * </ul>
 *
 * <p>
 *     Diese Enums werden sowohl in der Datenbank, in DTOs als auch
 *     im REST-API eingesetzt. Sie ermöglichen eine konsistente Validierung und Filterung.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-18
 *
 * @see com.wiss.backend.entity.Event Hauptentität, die diese Enums verwendet
 * @see com.wiss.backend.dto.EventDTO Transportobjekt mit Kategorie und Status
 * @see com.wiss.backend.dto.EventFormDTO Eingabevalidierung inkl. Enums
 * @see com.wiss.backend.controller.EventController REST-Endpunkte mit direkter Enum-Nutzung
 */
package com.wiss.backend.model;