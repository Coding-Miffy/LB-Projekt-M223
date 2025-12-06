package com.wiss.backend.model;

/**
 * <h2>
 *     Enum zur Kategorisierung von Naturereignissen
 * </h2>
 *
 * <p>
 *     Dieses Enum listet alle unterstützten Kategorien von Naturereignissen basierend
 *     auf den offiziellen Kategorien der NASA-EONET-API.
 *     Es dient der Klassifikation der Events in der Datenbank sowie im REST-API.
 * </p>
 *
 * <p>
 *     Beispiele für Kategorien:
 *     <ul>
 *         <li><code>wildfires</code> – Waldbrände</li>
 *         <li><code>volcanoes</code> – Vulkanausbrüche</li>
 *         <li><code>floods</code> – Überschwemmungen</li>
 *     </ul>
 * </p>
 *
 * <p>
 *     Die Schreibweise folgt dem Originalformat der NASA-Datenquelle.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-18
 *
 * @see com.wiss.backend.entity.Event Verwendung als Entity-Feld
 * @see com.wiss.backend.dto.EventDTO Verwendung als Transportobjekt
 * @see com.wiss.backend.dto.EventFormDTO Validierung eingehender Nutzer:innendaten
 * @see com.wiss.backend.controller.EventController REST-Endpunkte, die mit Event-Kategorien arbeiten
 */
public enum EventCategory {

    /**
     * Waldbrände
     */
    wildfires,

    /**
     * Schwere Stürme
     */
    severeStorms,

    /**
     * Vulkanausbrüche und -aktivitäten
     */
    volcanoes,

    /**
     * Meer- und Seeeis
     */
    seaLakeIce,

    /**
     * Erdbeben
     */
    earthquakes,

    /**
     * Überschwemmungen
     */
    floods,

    /**
     * Erdrutsche
     */
    landslides,

    /**
     * Schneefall / Schneeereignisse
     */
    snow,

    /**
     * Dürren
     */
    drought,

    /**
     * Staub- und Dunstereignisse
     */
    dustHaze,

    /**
     * Vom Menschen verursachte Ereignisse
     */
    manmade,

    /**
     * Veränderung der Wasserfarbe (z. B. durch Algenblüten)
     */
    waterColor
}
