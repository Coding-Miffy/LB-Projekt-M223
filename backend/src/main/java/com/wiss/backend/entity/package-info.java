/**
 * <h2>
 *     Persistenzmodell für Naturereignisse
 * </h2>
 *
 * <p>
 *     Dieses Paket enthält die JPA-Entities der Anwendung.
 *     Die Klassen in diesem Package repräsentieren die Datenbanktabellen
 *     und werden von JPA/Hibernate für die automatische Persistierung verwendet.
 *     Jede Klasse ist mit {@link jakarta.persistence.Entity} annotiert und definiert
 *     die Struktur der entsprechenden Tabelle inklusive Felder, Constraints
 *     und Relationen.
 * </p>
 *
 * <h3>
 *     Enthaltene Komponenten:
 * </h3>
 * <ul>
 *     <li>{@link com.wiss.backend.entity.Event} – Entity-Klasse zur Abbildung eines
 *     Naturereignisses mit ID, Titel, Datum, Kategorie, Koordinaten und Status.</li>
 * </ul>
 *
 * <p>
 *     Die Entity-Klassen werden ausschliesslich im Backend verwendet und nicht direkt
 *     an das Frontend übermittelt. Für den Datenaustausch mit dem Client kommen separate
 *     {@link com.wiss.backend.dto DTO}-Klassen zum Einsatz, um das Persistenzmodell vom
 *     Transportmodell klar zu trennen.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-18
 */
package com.wiss.backend.entity;