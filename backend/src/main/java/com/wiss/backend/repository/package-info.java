/**
 * <h2>
 *     Repository-Layer
 * </h2>
 * <p>
 *     Dieses Paket enthält die Repository-Interfaces für den Datenzugriff.
 *     Die Repositories basieren auf Spring Data JPA und ermöglichen CRUD-Operationen sowie
 *     benutzerdefinierte Abfragen auf {@link com.wiss.backend.entity.Event}-Entitäten.
 * </p>
 *
 * <h3>
 *     Besonderheiten:
 * </h3>
 * <ul>
 *     <li>Filterung von Ereignissen nach Kategorie, Status und Zeitraum</li>
 *     <li>Kombinierte Query-Methoden zur gezielten Abfrage</li>
 *     <li>Zählmethoden zur statistischen Auswertung</li>
 * </ul>
 *
 * <h3>
 *     Verwendete Technologien:
 * </h3>
 * <ul>
 *     <li>{@code JpaRepository} von Spring Data JPA</li>
 *     <li>Automatisches Query-Deriving auf Basis von Methodennamen</li>
 * </ul>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-20
 *
 * @see com.wiss.backend.entity.Event
 * @see com.wiss.backend.service.EventService
 * @see com.wiss.backend.controller.EventController
 */
package com.wiss.backend.repository;