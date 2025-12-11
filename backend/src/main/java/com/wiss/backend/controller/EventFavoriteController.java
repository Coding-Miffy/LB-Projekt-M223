package com.wiss.backend.controller;

import com.wiss.backend.entity.AppUser;
import com.wiss.backend.service.EventFavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventFavoriteController {

    private final EventFavoriteService favoriteService;

    public EventFavoriteController(EventFavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    /**
     * Toggle-Favorit für das aktuell eingeloggte Benutzerkonto.
     *
     * @param eventId ID des Events, das (un-)favorisiert werden soll
     * @param user    aktuell eingeloggter Benutzer
     * @return Antwort mit aktuellem Favoritenstatus und Favoritenzähler
     */
    @PostMapping("/{eventId}/favorite")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(
            summary = "Favorit ein-/ausschalten",
            description = "Markiert ein Event als Favorit für den eingeloggten User oder entfernt die Markierung wieder."
    )
    public ResponseEntity<FavoriteResponse> toggleFavorite(
            @PathVariable Long eventId,
            @AuthenticationPrincipal AppUser user
    ) {
        // Spring Security garantiert hier: user ist authentifiziert (wegen @PreAuthorize)

        boolean isFavoriteNow = favoriteService.toggleFavorite(user.getId(), eventId);
        int favoritesCount = favoriteService.getFavoritesCount(eventId);

        FavoriteResponse response = new FavoriteResponse(eventId, isFavoriteNow, favoritesCount);
        return ResponseEntity.ok(response);
    }

    /**
     * Liefert den aktuellen Favoritenzähler für ein Event.
     * Kann z.B. für eine separate Anzeige im Frontend genutzt werden.
     */
    @GetMapping("/{eventId}/favorite/count")
    @Operation(
            summary = "Favoriten-Zähler eines Events",
            description = "Gibt zurück, wie oft ein Event als Favorit markiert wurde."
    )
    public ResponseEntity<FavoriteCountResponse> getFavoritesCount(@PathVariable Long eventId) {
        int favoritesCount = favoriteService.getFavoritesCount(eventId);
        return ResponseEntity.ok(new FavoriteCountResponse(eventId, favoritesCount));
    }

    /**
     * Kompakte Response für den Toggle-Endpoint.
     */
    public record FavoriteResponse(
            Long eventId,
            boolean favorite,
            int favoritesCount
    ) {}

    /**
     * Kompakte Response nur für den Zähler.
     */
    public record FavoriteCountResponse(
            Long eventId,
            int favoritesCount
    ) {}
}
