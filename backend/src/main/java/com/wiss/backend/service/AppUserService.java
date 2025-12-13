package com.wiss.backend.service;

import com.wiss.backend.entity.AppUser;
import com.wiss.backend.entity.Role;
import com.wiss.backend.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * <h2>
 *     Service für Benutzerverwaltung und Authentifizierung
 * </h2>
 *
 * <p>
 *     Diese Serviceklasse enthält die zentrale Geschäftslogik für Benutzerkonten.
 *     Sie übernimmt Aufgaben wie Registrierung, Validierung, Passwort-Hashing
 *     sowie Authentifizierung anhand von Username und Passwort.
 *     Der Service wird vom {@link com.wiss.backend.controller.AuthController}
 *     sowie von Spring Security genutzt.
 * </p>
 *
 * <h3>Hauptfunktionen:</h3>
 * <ul>
 *     <li>Registrieren neuer Benutzer (inkl. Validierung & Passwort-Hashing)</li>
 *     <li>Benutzersuche per Username oder Email</li>
 *     <li>Passwortvalidierung für Login-Vorgänge</li>
 *     <li>Verfügbarkeitsprüfung von Usernamen und Emails</li>
 * </ul>
 *
 * <p>
 *     Die Klasse ist mit {@link Transactional} annotiert, sodass alle
 *     Operationen atomar ausgeführt werden. Dadurch werden Race Conditions
 *     bei parallelen Registrierungen vermieden.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-12-12
 *
 * @see AppUserRepository
 * @see AppUser
 * @see Role
 */
@Service
@Transactional
public class AppUserService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Konstruktor zur Initialisierung der benötigten Komponenten.
     *
     * @param userRepository Repository zum Zugriff auf Benutzerentitäten
     * @param passwordEncoder BCrypt-Encoder zum sicheren Hashen von Passwörtern
     */
    public AppUserService(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registriert einen neuen Benutzer.
     *
     * <p>
     *     Der Vorgang umfasst:
     * </p>
     * <ul>
     *     <li>Validierung der Eindeutigkeit von Username und Email</li>
     *     <li>Hashing des Passworts mit BCrypt</li>
     *     <li>Persistieren der neuen Benutzerentität</li>
     * </ul>
     *
     * @param username gewünschter Username
     * @param email    Email-Adresse des neuen Benutzers
     * @param rawPassword Passwort im Klartext (wird gehasht gespeichert)
     * @param role     gewünschte Benutzerrolle
     * @return gespeicherter Benutzer als {@link AppUser}
     * @throws IllegalArgumentException wenn Username oder Email bereits existieren
     */
    public AppUser registerUser(String username, String email, String rawPassword, Role role) {

        // Validation 1: Username bereits vergeben?
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException(
                    "Username " + username + " already exists."
            );
        }

        // Validation 2: Email bereits registriert?
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(
                    "Email " + email + " already exists."
            );
        }

        // Password hashen
        String hashedPassword = passwordEncoder.encode(rawPassword);

        // User Entity erstellen
        AppUser newUser = new AppUser(username, email, hashedPassword, role);

        // Speichern und zurückgeben
        return userRepository.save(newUser);
    }

    /**
     * Sucht einen User anhand des Usernames.
     *
     * @param username Username des gesuchten Benutzers
     * @return Optional mit Benutzer oder empty, falls nicht vorhanden
     */
    public Optional<AppUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Sucht einen User anhand der Email-Adresse.
     *
     * @param email Email des gesuchten Benutzers
     * @return Optional mit Benutzer oder empty
     */
    public Optional<AppUser> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Authentifiziert einen User anhand Username und Passwort.
     *
     * <p>
     *     Diese Methode prüft mittels BCrypt, ob das eingegebene Passwort
     *     dem gespeicherten Hash entspricht.
     * </p>
     *
     * @param username Username des Benutzers
     * @param rawPassword eingegebenes Passwort im Klartext
     * @return Optional mit Benutzer bei Erfolg, sonst empty
     */
    public Optional<AppUser> authenticateUser(String username, String rawPassword) {

        // User suchenanhand Username suchen
        Optional<AppUser> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            AppUser user = userOpt.get();

            // Password prüfen mit BCrypt
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return userOpt; // Login erfolgreich
            }
        }

        return Optional.empty(); // Login fehlgeschlagen
    }

    /**
     * Prüft, ob eine Email-Adresse syntaktisch gültig ist.
     *
     * @param email zu prüfende Email-Adresse
     * @return true, wenn Format plausibel, sonst false
     */
    private boolean isValidEmail(String email) {
        return email != null &&
                email.contains("@") &&
                email.length() > 3;
    }

    /**
     * Prüft, ob ein Username bereits vergeben ist.
     *
     * @param username Username zur Prüfung
     * @return true, wenn Username bereits existiert
     */
    public boolean isUsernameAvailable(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Prüft, ob eine Email bereits registriert ist.
     *
     * @param email Email-Adresse zur Prüfung
     * @return true, wenn Email bereits existiert
     */
    public boolean isEmailAvailable(String email) {
        return userRepository.existsByEmail(email);
    }
}
