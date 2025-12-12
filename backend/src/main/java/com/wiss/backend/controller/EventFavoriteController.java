package com.wiss.backend.controller;

import com.wiss.backend.entity.AppUser;
import com.wiss.backend.service.EventFavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * <h2>
 *     Controller für Favoritenfunktionen
 * </h2>
 * <p>
 *     Dieser Controller stellt Endpunkte bereit, um Events als Favoriten
 *     zu markieren oder die Favoritenanzahl auszulesen. Nur eingeloggte
 *     Benutzer:innen (USER oder ADMIN) dürfen Favoriten setzen oder entfernen.
 * </p>
 *
 * <p>
 *     Die Favoritenfunktion wird über den {@link EventFavoriteService}
 *     abgewickelt und ist vollständig transaktional, um Race Conditions
 *     beim Zählen und Ändern der Favoriten zu vermeiden.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-12-12
 *
 * @see EventFavoriteService
 */
@RestController
@RequestMapping("/api/events")
@Tag(name = "Event-Favoriten", description = "Endpunkte zum Markieren von Favoriten sowie zum Auslesen des Favoritenzählers.")
public class EventFavoriteController {

    private final EventFavoriteService favoriteService;

    public EventFavoriteController(EventFavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    /**
     * Markiert oder entfernt ein Event als Favorit für den aktuell
     * eingeloggten Benutzer. Der Endpunkt ist geschützt und nur für
     * User:innen mit Rolle USER oder ADMIN zugänglich.
     *
     * @param eventId ID des Events, das als Favorit gesetzt/entfernt werden soll
     * @param user    aktuell eingeloggter Benutzer (von Spring Security gesetzt)
     * @return Antwort mit neuem Favoritenstatus und Gesamtanzahl der Favoriten
     */
    @PostMapping("/{eventId}/favorite")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(
            summary = "Favorit ein-/ausschalten",
            description = "Markiert ein Event als Favorit für den eingeloggten User oder entfernt die Markierung wieder."
    )
    @ApiResponse(responseCode = "200", description = "Favoritenstatus erfolgreich geändert")
    @ApiResponse(responseCode = "401", description = "Nicht authentifiziert")
    @ApiResponse(responseCode = "403", description = "Keine ausreichenden Berechtigungen")
    public ResponseEntity<FavoriteResponse> toggleFavorite(
            @PathVariable Long eventId,
            @AuthenticationPrincipal AppUser user
    ) {

        boolean isFavoriteNow = favoriteService.toggleFavorite(user.getId(), eventId);
        int favoritesCount = favoriteService.getFavoritesCount(eventId);

        FavoriteResponse response = new FavoriteResponse(eventId, isFavoriteNow, favoritesCount);
        return ResponseEntity.ok(response);
    }

    /**
     * Liefert die Anzahl aller Benutzer:innen, die ein bestimmtes Event
     * favorisiert haben. Dieser Endpunkt ist öffentlich, da die Anzahl
     * keine sensitiven Informationen enthält.
     *
     * @param eventId ID des Events
     * @return Favoritenzähler für das Event
     */
    @GetMapping("/{eventId}/favorite/count")
    @Operation(
            summary = "Favoriten-Zähler eines Events",
            description = "Gibt zurück, wie oft ein Event als Favorit markiert wurde."
    )
    @ApiResponse(responseCode = "200", description = "Favoritenzähler erfolgreich abgerufen")
    public ResponseEntity<FavoriteCountResponse> getFavoritesCount(@PathVariable Long eventId) {
        int favoritesCount = favoriteService.getFavoritesCount(eventId);
        return ResponseEntity.ok(new FavoriteCountResponse(eventId, favoritesCount));
    }

    /**
     * Kompakte Response für Favoriten-Toggling.
     *
     * @param eventId Event-ID
     * @param favorite aktueller Favoritenstatus nach dem Toggle
     * @param favoritesCount Anzahl der Favoriten
     */
    public record FavoriteResponse(
            Long eventId,
            boolean favorite,
            int favoritesCount
    ) {}

    /**
     * Response nur zur Rückgabe des Favoritenzählers.
     *
     * @param eventId Event-ID
     * @param favoritesCount Anzahl der Favoriten
     */
    public record FavoriteCountResponse(
            Long eventId,
            int favoritesCount
    ) {}
}
