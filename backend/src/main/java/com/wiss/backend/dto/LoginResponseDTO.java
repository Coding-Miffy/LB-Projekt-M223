package com.wiss.backend.dto;

/**
 * <h2>
 *     DTO für Login-Antworten (JWT Response)
 * </h2>
 *
 * <p>
 *     Dieses Data Transfer Object wird nach einem erfolgreichen Login
 *     an das Frontend zurückgegeben. Es enthält den generierten JWT,
 *     Benutzerinformationen sowie Metadaten zur Token-Gültigkeit.
 * </p>
 *
 * <h3>
 *     Enthaltene Informationen:
 * </h3>
 * <ul>
 *     <li>JWT-Token (im Bearer-Format)</li>
 *     <li>User-ID, Username und Email</li>
 *     <li>Benutzerrolle (z. B. <code>ADMIN</code>, <code>USER</code>)</li>
 *     <li>Ablaufzeit des Tokens in Millisekunden</li>
 * </ul>
 *
 * <p>
 *     Das Passwort wird selbstverständlich nicht zurückgegeben.
 *     Der Token wird im {@link #toString()} vollständig maskiert.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-12-12
 *
 * @see com.wiss.backend.controller.AuthController
 * @see com.wiss.backend.dto.LoginRequestDTO
 * @see com.wiss.backend.service.JwtService
 */
public class LoginResponseDTO {

    private final String token;
    private final String tokenType = "Bearer";
    private final Long userId;
    private final String username;
    private final String email;
    private final String role;
    private final long expiresIn;

    /**
     * Constructor für erfolgreichen Login.
     *
     * @param token Der generierte JWT Token
     * @param userId Die User ID
     * @param username Der Username
     * @param email Die Email
     * @param role Die Rolle (ADMIN oder PLAYER)
     * @param expiresIn Token Gültigkeit in ms
     */
    public LoginResponseDTO(String token, Long userId, String username, String email, String role, long expiresIn) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
        this.expiresIn = expiresIn;
    }

    // Getter
    public String getToken() { return token; }
    public String getTokenType() { return tokenType; }
    public Long getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public long getExpiresIn() { return expiresIn; }

    /**
     * Gibt eine textuelle Repräsentation zurück, bei der
     * der Token aus Sicherheitsgründen nicht angezeigt wird.
     *
     * @return Objektbeschreibung mit maskiertem Token
     */
    @Override
    public String toString() {
        return "LoginResponseDTO{" +
                "token='[HIDDEN]'" +
                ", tokenType='" + tokenType + '\'' +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }
}
