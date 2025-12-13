/**
 * <h2>
 *     Persistenzmodell der Anwendung (JPA Entities)
 * </h2>
 *
 * <p>
 *     Dieses Paket enthält sämtliche JPA-Entity-Klassen der Anwendung.
 *     Die Klassen repräsentieren das fachliche Datenmodell und werden von
 *     JPA/Hibernate genutzt, um die entsprechenden Datenbanktabellen zu erzeugen,
 *     zu verwalten und mit ihnen zu interagieren.
 * </p>
 *
 * <h3>Aufgabe des Pakets:</h3>
 * <ul>
 *     <li>Abbildung der Datenbankstrukturen (Tabellen, Spalten, Relationen)</li>
 *     <li>Persistierung und Laden von Objekten über Hibernate</li>
 *     <li>Definition von fachlich relevanten Entitäten wie User, Events und Favoriten</li>
 * </ul>
 *
 * <h3>Enthaltene Entities:</h3>
 * <ul>
 *     <li>{@link com.wiss.backend.entity.AppUser}
 *         – Repräsentiert eine Benutzer:in inkl. Login-Daten, Rolle und Versionierung.</li>
 *
 *     <li>{@link com.wiss.backend.entity.Event}
 *         – Repräsentiert ein Naturereignis mit Titel, Datum, Koordinaten,
 *         Kategorie, Status und Favoritenanzahl.</li>
 *
 *     <li>{@link com.wiss.backend.entity.EventFavorite}
 *         – Modelliert die Beziehung "User favorisiert Event". Dient zur Verwaltung
 *         einer N:M-ähnlichen Relation im System.</li>
 *
 *     <li>{@link com.wiss.backend.entity.Role}
 *         – Enum mit den verfügbaren Systemrollen (ADMIN, USER).</li>
 * </ul>
 *
 * <p>
 *     Die Entities werden ausschliesslich im Backend genutzt und niemals direkt an das
 *     Frontend gesendet. Für den Austausch mit dem Client dienen eigenständige
 *     {@link com.wiss.backend.dto DTO}-Klassen, um das Persistenzmodell strikt vom
 *     Transportmodell zu trennen.
 * </p>
 *
 * @author Natascha Blumer
 * @version 2.0
 * @since 2025-12-12
 */
package com.wiss.backend.entity;