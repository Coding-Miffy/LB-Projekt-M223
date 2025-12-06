package com.wiss.backend.entity;

import jakarta.persistence.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "app_users")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public AppUser() {}

    public AppUser(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // UserDetails-Methoden
    /**
     * Gibt die Rollen (Authorities) des Users zurück.
     *
     * @return Collection mit einer Authority (z.B. "ROLE_ADMIN")
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    /**
     * Gibt das verschlüsselte Passwort zurück.
     *
     * @return BCrypt Hash des Passworts
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Gibt den Benutzernamen zurück.
     *
     * @return Username des Users
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Ist das Konto nicht abgelaufen?
     *
     * @return true (Konto läuft nie ab)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;  // Keine Ablauf-Logik implementiert
    }

    /**
     * Ist das Konto nicht gesperrt?
     *
     * @return true (Konto ist nie gesperrt)
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;  // Keine Sperr-Logik implementiert
    }

    /**
     * Ist das Passwort nicht abgelaufen?
     *
     * @return true (Passwort läuft nie ab)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Passwörter laufen nicht ab
    }

    /**
     * Ist das Konto aktiviert?
     *
     * @return true (Konto ist sofort aktiv)
     */
    @Override
    public boolean isEnabled() {
        return true;  // Accounts sind sofort aktiv
    }

    // Getter und Setter
    public Long getId() { return id;}
    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }
    public void setVersion(Long version) {
        this.version = version;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
}
