package com.wiss.backend.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * <h2>
 *     DTO für Login-Anfragen
 * </h2>
 *
 * <p>
 *     Dieses Data Transfer Object enthält die benötigten Eingabedaten,
 *     um einen Benutzer zu authentifizieren. Der Login kann sowohl über
 *     den Benutzernamen als auch über die Email-Adresse erfolgen.
 * </p>
 *
 * <h3>
 *     Validierung:
 * </h3>
 * <ul>
 *     <li>{@code usernameOrEmail} darf nicht leer sein</li>
 *     <li>{@code password} darf nicht leer sein</li>
 * </ul>
 *
 * <p>
 *     Sensible Daten wie das Passwort werden im {@link #toString()} bewusst
 *     nicht im Klartext ausgegeben.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-12-12
 *
 * @see com.wiss.backend.controller.AuthController
 * @see com.wiss.backend.dto.LoginResponseDTO
 */
public class LoginRequestDTO {

    /**
     * Benutzername oder Email des Users.
     * @apiNote Darf nicht leer sein.
     * @see #getUsernameOrEmail()
     */
    @NotBlank(message = "Username oder Email ist erforderlich")
    private String usernameOrEmail;

    /**
     * Passwort des Users.
     * @apiNote Darf nicht leer sein.
     * @see #getPassword()
     */
    @NotBlank(message = "Passwort ist erforderlich")
    private String password;

    /**
     * Leerer Standardkonstruktor (z. B. für JSON-Deserialisierung).
     */
    public LoginRequestDTO() {}

    /**
     * Konstruktor zum Setzen aller Felder.
     *
     * @param usernameOrEmail Benutzername oder Email des Users
     * @param password Passwort (Klartext; wird später gehasht)
     */
    public LoginRequestDTO(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }

    // Getter & Setter
    public String getUsernameOrEmail() { return usernameOrEmail; }
    public void setUsernameOrEmail(String usernameOrEmail) { this.usernameOrEmail = usernameOrEmail; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    /**
     * Gibt eine stringbasierte Repräsentation zurück,
     * ohne das Passwort preiszugeben.
     *
     * @return String-Darstellung mit anonymisiertem Passwort
     */
    @Override
    public String toString() {
        return "LoginRequestDTO{" +
                "usernameOrEmail='" + usernameOrEmail + '\'' +
                ", password='[HIDDEN]'" +
                '}';
    }
}
