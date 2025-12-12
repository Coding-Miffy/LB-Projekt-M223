package com.wiss.backend.entity;

import jakarta.persistence.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * <h2>
 *     Benutzer-Entität für die Authentifizierung
 * </h2>
 * <p>
 *     Diese Klasse repräsentiert einen registrierten Benutzer der Anwendung.
 *     Sie wird über JPA in der Tabelle <code>app_users</code> gespeichert
 *     und implementiert {@link UserDetails}, damit Spring Security die
 *     Benutzerinformationen direkt für Login und Rollenprüfung verwenden kann.
 * </p>
 *
 * <h3>
 *     Wichtige Eigenschaften:
 * </h3>
 * <ul>
 *     <li><b>username</b>: eindeutiger Anmeldename</li>
 *     <li><b>email</b>: eindeutige Kontaktadresse</li>
 *     <li><b>password</b>: verschlüsseltes Passwort (BCrypt-Hash)</li>
 *     <li><b>role</b>: fachliche Rolle (z.B. USER oder ADMIN)</li>
 *     <li><b>version</b>: Versionsfeld für optimistische Sperren</li>
 * </ul>
 *
 * <p>
 *     Über die Methode {@link #getAuthorities()} wird die Rolle in ein
 *     Spring-Security-Role-Format (z.B. <code>ROLE_ADMIN</code>)
 *     übersetzt und kann in Security-Konfiguration und Controllern
 *     für Autorisierungsprüfungen genutzt werden.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-12-12
 *
 * @see Role
 * @see com.wiss.backend.repository.AppUserRepository
 * @see com.wiss.backend.service.AppUserService
 * @see com.wiss.backend.service.AppUserDetailsService
 */
@Entity
@Table(name = "app_users")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Versionsfeld für optimistische Sperren.
     * <p>
     *     Wird von JPA automatisch erhöht, um konkurrierende Updates
     *     auf dieselbe Benutzerzeile erkennen zu können.
     * </p>
     */
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

    /**
     * Standard-Konstruktor für JPA.
     */
    public AppUser() {}

    /**
     * Komfort-Konstruktor zum Erstellen eines neuen Benutzers.
     *
     * @param username fachlicher Benutzername (eindeutig)
     * @param email    E-Mail-Adresse (eindeutig)
     * @param password verschlüsseltes Passwort (BCrypt-Hash)
     * @param role     Zuweisung der Rolle (z.B. USER oder ADMIN)
     */
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
     * @return Collection mit einer Authority (z.B. {@code "ROLE_ADMIN"})
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    /**
     * Gibt das verschlüsselte Passwort zurück.
     *
     * @return BCrypt-Hash des Passworts
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
     * @return immer {@code true}, da keine Ablauf-Logik implementiert ist
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;  // Keine Ablauf-Logik implementiert
    }

    /**
     * Ist das Konto nicht gesperrt?
     *
     * @return immer {@code true}, da keine Sperr-Logik implementiert ist
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;  // Keine Sperr-Logik implementiert
    }

    /**
     * Ist das Passwort nicht abgelaufen?
     *
     * @return immer {@code true}, da Passwörter nicht ablaufen
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Passwörter laufen nicht ab
    }

    /**
     * Ist das Konto aktiviert?
     *
     * @return immer {@code true}, da alle Accounts sofort aktiv sind
     */
    @Override
    public boolean isEnabled() {
        return true;  // Accounts sind sofort aktiv
    }

    // Getter und Setter
    public Long getId() { return id;}
    public void setId(Long id) { this.id = id;}

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) {
        this.role = role;
    }
}
