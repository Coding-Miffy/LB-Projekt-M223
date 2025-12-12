package com.wiss.backend.dto;

/**
 * <h2>
 *     DTO für erfolgreiche Benutzerregistrierung
 * </h2>
 *
 * <p>
 *     Dieses Data Transfer Object wird vom Backend an das Frontend zurückgegeben,
 *     nachdem ein neuer Benutzer erfolgreich registriert wurde.
 *     Es enthält alle relevanten Informationen über den neu erstellten Benutzer,
 *     jedoch ohne sicherheitskritische Daten wie Passwörter.
 * </p>
 *
 * <h3>Enthaltene Informationen:</h3>
 * <ul>
 *     <li><b>ID</b> – die generierte Benutzer-ID</li>
 *     <li><b>username</b> – der registrierte Benutzername</li>
 *     <li><b>email</b> – die bestätigte Email-Adresse</li>
 *     <li><b>role</b> – die zugewiesene Systemrolle (z. B. USER)</li>
 *     <li><b>message</b> – freundliche Bestätigungsnachricht für das Frontend</li>
 * </ul>
 *
 * <p>
 *     Das DTO dient der klaren Trennung zwischen Persistenzmodell
 *     ({@link com.wiss.backend.entity.AppUser}) und der API-Antwort
 *     im Registrierungsprozess.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-12-12
 *
 * @see com.wiss.backend.controller.AuthController
 * @see com.wiss.backend.dto.RegisterRequestDTO
 */
public class RegisterResponseDTO {

    private final Long id;
    private final String username;
    private final String email;
    private final String role;
    private final String message;

    /**
     * Konstruktor für erfolgreiche Registration.
     *
     * @param id Die generierte Benutzer-ID
     * @param username Der registrierte Username
     * @param email Die registrierte Email
     * @param role Die zugewiesene Rolle
     */
    public RegisterResponseDTO(Long id, String username, String email, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.message = "Registrierung erfolgreich! Willkommen "
                + username + "!";
    }

    // Getter
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getMessage() { return message; }

    /**
     * Gibt eine strukturierte Darstellung zurück.
     *
     * @return String-Repräsentation des DTOs
     */
    @Override
    public String toString() {
        return "RegisterResponseDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
