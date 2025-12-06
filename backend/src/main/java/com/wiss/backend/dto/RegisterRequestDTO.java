package com.wiss.backend.dto;

import jakarta.validation.constraints.*;

public class RegisterRequestDTO {

    @NotBlank(message = "Username ist erforderlich")
    @Size(min = 3, max = 50, message = "Username muss zwischen 3 und 50 Zeichen haben")
    private String username;

    @NotBlank(message = "Email ist erforderlich")
    @Email(message = "Email muss ein gültiges Format haben")
    @Size(max = 100, message = "Email darf maximal 100 Zeichen haben")
    private String email;

    @NotBlank(message = "Passwort ist erforderlich")
    @Size(min = 6, message = "Passwort muss mindestens 6 Zeichen haben")
    private String password;

    public RegisterRequestDTO() {}

    public RegisterRequestDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "RegisterRequestDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='[HIDDEN]'" +
                '}';
    }
}
