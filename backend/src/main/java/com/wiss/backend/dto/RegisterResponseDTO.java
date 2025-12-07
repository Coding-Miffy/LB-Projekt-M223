package com.wiss.backend.dto;

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
