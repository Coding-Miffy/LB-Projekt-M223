package com.wiss.backend.controller;

import com.wiss.backend.dto.LoginRequestDTO;
import com.wiss.backend.dto.LoginResponseDTO;
import com.wiss.backend.dto.RegisterRequestDTO;
import com.wiss.backend.dto.RegisterResponseDTO;
import com.wiss.backend.entity.AppUser;
import com.wiss.backend.entity.Role;
import com.wiss.backend.service.AppUserService;
import com.wiss.backend.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <h2>
 *     Authentifizierungs- und Registrierungs-Controller
 * </h2>
 * <p>
 *     Stellt Endpunkte für die Benutzerregistrierung, den Login sowie
 *     Verfügbarkeitsprüfungen von Username und Email bereit.
 *     Beim erfolgreichen Login wird ein JWT-Token generiert, der im Frontend
 *     gespeichert und für weitere Requests verwendet werden kann.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-12-12
 *
 * @see com.wiss.backend.dto.LoginRequestDTO
 * @see com.wiss.backend.dto.LoginResponseDTO
 * @see com.wiss.backend.dto.RegisterRequestDTO
 * @see com.wiss.backend.dto.RegisterResponseDTO
 * @see com.wiss.backend.service.AppUserService
 * @see com.wiss.backend.service.JwtService
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Authentifizierung", description = "Endpunkte für Registrierung, Login und Verfügbarkeitsprüfungen von Benutzerkonten.")
public class AuthController {

    private final AppUserService appUserService;
    private final JwtService jwtService;

    public AuthController(AppUserService appUserService, JwtService jwtService) {
        this.appUserService = appUserService;
        this.jwtService = jwtService;
    }

    /**
     * Registriert einen neuen User mit der Default-Rolle {@link Role#USER}.
     *
     * @param request Registrierungsdaten (Username, Email, Passwort)
     * @return Erfolgsantwort mit {@link RegisterResponseDTO} oder
     *         Fehlermeldung bei Validierungs- / Business-Fehlern
     */
    @PostMapping("/register")
    @Operation(
            summary = "Neuen Benutzer registrieren",
            description = "Legt einen neuen Benutzer mit der Rolle USER an, inkl. BCrypt-Hashing des Passworts."
    )
    @ApiResponse(responseCode = "200", description = "Registrierung erfolgreich")
    @ApiResponse(responseCode = "400", description = "Username oder Email bereits vergeben / ungültige Daten")
    @ApiResponse(responseCode = "500", description = "Interner Fehler bei der Registrierung")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO request) {
        try {
            // Service Layer aufrufen
            AppUser newUser = appUserService.registerUser(
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword(),
                    Role.USER  // Default Role für neue User
            );

            // Response DTO erstellen
            RegisterResponseDTO response = new RegisterResponseDTO(
                    newUser.getId(),
                    newUser.getUsername(),
                    newUser.getEmail(),
                    newUser.getRole().name()
            );

            // HTTP 200 OK mit Response Body
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            // HTTP 400 Bad Request bei Validation Errors
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);

        } catch (Exception e) {
            // HTTP 500 bei unerwarteten Fehlern
            Map<String, String> error = new HashMap<>();
            error.put("error", "Registrierung fehlgeschlagen");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Authentifiziert einen Benutzer anhand Benutzername oder Email und Passwort
     * und gibt bei Erfolg ein JWT-Token zurück.
     *
     * @param request Login-Daten (Username/Email + Passwort)
     * @return {@link LoginResponseDTO} mit JWT-Token oder 401/500-Fehler
     */
    @PostMapping("/login")
    @Operation(
            summary = "Benutzer einloggen",
            description = "Validiert Benutzername/Email und Passwort und gibt bei Erfolg ein JWT-Token zurück."
    )
    @ApiResponse(responseCode = "200", description = "Login erfolgreich, Token generiert")
    @ApiResponse(responseCode = "401", description = "Ungültige Anmeldedaten")
    @ApiResponse(responseCode = "500", description = "Interner Fehler bei der Authentifizierung")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request) {
        try {
            // User finden (Username oder Email)
            Optional<AppUser> userOpt;

            // Prüfen ob Email oder Username
            if (request.getUsernameOrEmail().contains("@")) {
                userOpt = appUserService.findByEmail(request.getUsernameOrEmail());
            } else {
                userOpt = appUserService.findByUsername(request.getUsernameOrEmail());
            }

            // User existiert nicht
            if (userOpt.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Ungültige Anmeldedaten"));
            }

            AppUser user = userOpt.get();

            // Passwort prüfen
            Optional<AppUser> authenticatedUser =
                    appUserService.authenticateUser(user.getUsername(),
                            request.getPassword());

            if (authenticatedUser.isEmpty()) {
                // Passwort falsch
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Ungültige Anmeldedaten"));
            }

            // JWT Token generieren
            String token = jwtService.generateToken(
                    user.getUsername(),
                    user.getRole().name()
            );

            // Response DTO erstellen
            LoginResponseDTO response = new LoginResponseDTO(
                    token,
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole().name(),
                    86400000L  // 24 Stunden in ms
            );

            // Success Response
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Unerwartete Fehler
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Ein Fehler ist aufgetreten: " + e.getMessage()));
        }
    }

    /**
     * Prüft, ob ein bestimmter Username verfügbar ist.
     *
     * @param username Der gewünschte Username
     * @return JSON mit Username und Boolean-Feld <code>available</code>
     */
    @GetMapping("/check-username/{username}")
    @Operation(
            summary = "Username auf Verfügbarkeit prüfen",
            description = "Prüft, ob ein gewünschter Username bereits vergeben ist."
    )
    @ApiResponse(responseCode = "200", description = "Verfügbarkeitsstatus erfolgreich ermittelt")
    public ResponseEntity<Map<String, Object>> checkUsername(@PathVariable String username) {
        boolean available = appUserService.isUsernameAvailable(username);

        Map<String, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("available", available);

        return ResponseEntity.ok(response);
    }

    /**
     * Prüft, ob eine bestimmte Email-Adresse bereits registriert ist.
     *
     * @param email Die zu prüfende Email-Adresse
     * @return JSON mit Email und Boolean-Feld <code>available</code>
     */
    @GetMapping("/check-email/{email}")
    @Operation(
            summary = "Email auf Verfügbarkeit prüfen",
            description = "Prüft, ob eine Email-Adresse bereits registriert ist."
    )
    @ApiResponse(responseCode = "200", description = "Verfügbarkeitsstatus erfolgreich ermittelt")
    public ResponseEntity<Map<String, Object>> checkEmail(@PathVariable String email) {
        boolean available = appUserService.isEmailAvailable(email);

        Map<String, Object> response = new HashMap<>();
        response.put("email", email);
        response.put("available", available);

        return ResponseEntity.ok(response);
    }

    /**
     * Einfacher Health-Check-Endpunkt für den Auth-Controller.
     *
     * @return Textantwort, falls der Controller erreichbar ist
     */
    @GetMapping("/test")
    @Operation(
            summary = "Auth-Controller testen",
            description = "Gibt einen einfachen Text zurück, um die Erreichbarkeit des Auth-Controllers zu prüfen."
    )
    @ApiResponse(responseCode = "200", description = "Auth-Controller ist erreichbar")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth Controller funktioniert!");
    }
}
