/**
 * <h2>
 *     Repository-Layer der Anwendung
 * </h2>
 * <p>
 *     Dieses Paket enthält alle Repository-Interfaces für den Datenzugriff.
 *     Die Repositories basieren auf Spring Data JPA und ermöglichen sowohl
 *     Standard-CRUD-Operationen als auch komplexere Abfragen über automatisch
 *     generierte Query-Methoden.
 * </p>
 *
 * <h3>Enthaltene Repositories:</h3>
 * <ul>
 *     <li>{@link com.wiss.backend.repository.AppUserRepository}
 *         – Verwaltung und Suche von Benutzerkonten</li>
 *
 *     <li>{@link com.wiss.backend.repository.EventRepository}
 *         – Datenzugriff für Naturereignisse inkl. Filter- und Statistikabfragen</li>
 *
 *     <li>{@link com.wiss.backend.repository.EventFavoriteRepository}
 *         – Verwaltung der Favoriten-Beziehungen zwischen User:innen und Events</li>
 * </ul>
 *
 * <h3>Typische Funktionen:</h3>
 * <ul>
 *     <li>CRUD-Operationen für Benutzer und Events</li>
 *     <li>Filterung von Events nach Kategorie, Status und Zeitraum</li>
 *     <li>Prüfen und Verwalten von Favoriten-Markierungen</li>
 *     <li>Zählmethoden zur statistischen Auswertung</li>
 * </ul>
 *
 * <h3>Technische Grundlagen:</h3>
 * <ul>
 *     <li>{@code JpaRepository} für generierte CRUD-Methoden</li>
 *     <li>Query-Deriving anhand deklarativer Methodennamen</li>
 *     <li>Automatische Umsetzung in SQL durch Spring Data JPA</li>
 * </ul>
 *
 * <p>
 *     Die Repositories bilden die Datenzugriffsschicht des Backends und werden
 *     vom Service-Layer verwendet, um Geschäftslogik und Datenpersistenz klar zu trennen.
 * </p>
 *
 * @author Natascha Blumer
 * @version 2.0
 * @since 2025-12-12
 *
 * @see com.wiss.backend.entity.Event
 * @see com.wiss.backend.service.EventService
 * @see com.wiss.backend.controller.EventController
 * @see com.wiss.backend.entity.AppUser
 * @see com.wiss.backend.service.AppUserService
 * @see com.wiss.backend.entity.EventFavorite
 * @see com.wiss.backend.service.EventFavoriteService
 * @see com.wiss.backend.controller.EventFavoriteController
 */
package com.wiss.backend.repository;