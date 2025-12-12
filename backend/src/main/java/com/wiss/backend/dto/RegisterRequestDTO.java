package com.wiss.backend.dto;

import jakarta.validation.constraints.*;

/**
 * <h2>
 *     DTO für Registrierungsanfragen
 * </h2>
 *
 * <p>
 *     Dieses Data Transfer Object enthält die Eingabedaten,
 *     die ein neuer Benutzer beim Registrieren übermittelt.
 *     Die Felder sind mit Bean Validation annotiert, sodass
 *     ungültige oder unvollständige Daten frühzeitig abgefangen
 *     und dem Client gemeldet werden.
 * </p>
 *
 * <h3>Validierte Felder:</h3>
 * <ul>
 *     <li><b>username</b> – Pflichtfeld, 3–50 Zeichen</li>
 *     <li><b>email</b> – Pflichtfeld, gültiges Email-Format, max. 100 Zeichen</li>
 *     <li><b>password</b> – Pflichtfeld, mindestens 6 Zeichen</li>
 * </ul>
 *
 * <p>
 *     Das Passwort wird im {@link #toString()} vollständig maskiert,
 *     um sicherheitsrelevante Daten nicht in Logs erscheinen zu lassen.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-12-12
 *
 * @see com.wiss.backend.controller.AuthController
 * @see com.wiss.backend.dto.RegisterResponseDTO
 */
public class RegisterRequestDTO {

    /**
     * Benutzername des neuen Users.
     * @apiNote Muss zwischen 3 und 50 Zeichen lang sein.
     * @see #getUsername()
     */
    @NotBlank(message = "Username ist erforderlich")
    @Size(min = 3, max = 50, message = "Username muss zwischen 3 und 50 Zeichen haben")
    private String username;

    /**
     * Email-Adresse des Users.
     * @apiNote Darf nicht mehr als 100 Zeichen lang sein.
     * @see #getEmail()
     */
    @NotBlank(message = "Email ist erforderlich")
    @Email(message = "Email muss ein gültiges Format haben")
    @Size(max = 100, message = "Email darf maximal 100 Zeichen haben")
    private String email;

    /**
     * Passwort des Users.
     * @apiNote Darf nicht weniger als 6 Zeichen lang sein.
     * @see #getPassword()
     */
    @NotBlank(message = "Passwort ist erforderlich")
    @Size(min = 6, message = "Passwort muss mindestens 6 Zeichen haben")
    private String password;

    /**
     * Leerer Konstruktor (benötigt für JSON-Deserialisierung).
     */
    public RegisterRequestDTO() {}

    /**
     * Konstruktor zum Erstellen eines Registrierungs-DTOs.
     *
     * @param username Benutzername
     * @param email Email-Adresse
     * @param password Passwort im Klartext (wird im Service gehasht)
     */
    public RegisterRequestDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getter & Setter
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    /**
     * Gibt eine textuelle Repräsentation zurück,
     * wobei das Passwort aus Sicherheitsgründen maskiert wird.
     *
     * @return Objektbeschreibung mit verstecktem Passwort
     */
    @Override
    public String toString() {
        return "RegisterRequestDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='[HIDDEN]'" +
                '}';
    }
}
