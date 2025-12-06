package com.wiss.backend.controller;

import com.wiss.backend.dto.RegisterRequestDTO;
import com.wiss.backend.dto.RegisterResponseDTO;
import com.wiss.backend.entity.AppUser;
import com.wiss.backend.entity.Role;
import com.wiss.backend.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AppUserService appUserService;

    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    /**
     * POST /api/auth/register
     *
     * Registriert einen neuen User.
     *
     * @Valid triggert die Bean Validation
     * @RequestBody parsed JSON zu Java Object
     */

    @PostMapping("/register")
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
     * GET /api/auth/check-username/{username}
     *
     * Prüft, ob ein Username verfügbar ist.
     */
    @GetMapping("/check-username/{username}")
    public ResponseEntity<Map<String, Object>> checkUsername(@PathVariable String username) {
        boolean available = appUserService.isUsernameAvailable(username);

        Map<String, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("available", available);

        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/auth/check-email/{email}
     *
     * Prüft ob eine Email verfügbar ist.
     */
    @GetMapping("/check-email/{email}")
    public ResponseEntity<Map<String, Object>> checkEmail(@PathVariable String email) {
        boolean available = appUserService.isEmailAvailable(email);

        Map<String, Object> response = new HashMap<>();
        response.put("email", email);
        response.put("available", available);

        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/auth/test
     *
     * Simple Test Endpoint.
     * Prüft ob der Controller erreichbar ist.
     */
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth Controller funktioniert!");
    }
}
