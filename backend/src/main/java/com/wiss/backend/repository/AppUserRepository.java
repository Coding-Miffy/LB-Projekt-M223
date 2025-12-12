package com.wiss.backend.repository;

import com.wiss.backend.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <h2>
 *     Repository für Benutzerkonten
 * </h2>
 *
 * <p>
 *     Dieses Repository stellt den Datenbankzugriff für {@link AppUser}-Entitäten bereit.
 *     Es erweitert {@link JpaRepository} und bietet zusätzlich spezifische Methoden zum
 *     Auffinden und Validieren von Benutzern anhand von Username oder Email.
 * </p>
 *
 * <h3>
 *     Hauptfunktionen:
 * </h3>
 * <ul>
 *     <li>Auffinden von Usern via Username oder Email</li>
 *     <li>Prüfen der Existenz eines Benutzers (für Registrierung und Validierung)</li>
 *     <li>Backend-Unterstützung der Authentifizierung</li>
 * </ul>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-12-12
 *
 * @see com.wiss.backend.entity.AppUser
 * @see com.wiss.backend.service.AppUserService
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    /**
     * Sucht einen Benutzer anhand des Usernames.
     *
     * @param username Benutzername
     * @return Optional mit Benutzer oder leer, falls nicht gefunden
     */
    Optional<AppUser> findByUsername(String username);

    /**
     * Sucht einen Benutzer anhand der Email-Adresse.
     *
     * @param email Email des Benutzers
     * @return Optional mit Benutzer oder leer, falls nicht gefunden
     */
    Optional<AppUser> findByEmail(String email);

    /**
     * Prüft, ob ein Username bereits in der Datenbank existiert.
     *
     * @param username zu prüfender Username
     * @return true, wenn Username vergeben ist
     */
    boolean existsByUsername(String username);

    /**
     * Prüft, ob eine Email bereits registriert ist.
     *
     * @param email zu prüfende Email
     * @return true, wenn Email vergeben ist
     */
    boolean existsByEmail(String email);
}
