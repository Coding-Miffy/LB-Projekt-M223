package com.wiss.backend.model;

/**
 * <h2>
 *     Enum zur Statusangabe eines Naturereignisses
 * </h2>
 *
 * <p>
 *     Gibt den aktuellen Zustand eines Events an:
 *     <ul>
 *         <li><code>open</code> – Das Ereignis ist noch aktiv oder laufend.</li>
 *         <li><code>closed</code> – Das Ereignis ist abgeschlossen oder nicht mehr relevant.</li>
 *     </ul>
 * </p>
 *
 * <p>
 *     Der Status wird unter anderem für Filter- und Sortieroperationen in der API verwendet.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-07-18
 *
 * @see com.wiss.backend.entity.Event Verwendung als Entity-Feld
 * @see com.wiss.backend.dto.EventDTO Verwendung als Transportobjekt
 * @see com.wiss.backend.dto.EventFormDTO Validierung eingehender Nutzer:innendaten
 * @see com.wiss.backend.controller.EventController REST-Endpunkte, die mit Event-Status arbeiten
 */
public enum EventStatus {

    /**
     * Das Ereignis ist noch aktiv.
     */
    open,

    /**
     * Das Ereignis ist abgeschlossen.
     */
    closed
}
