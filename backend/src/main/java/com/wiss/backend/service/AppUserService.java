package com.wiss.backend.service;

import com.wiss.backend.entity.AppUser;
import com.wiss.backend.entity.Role;
import com.wiss.backend.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AppUserService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registriert einen neuen User.
     *
     * @param username Der gewünschte Username
     * @param email Die Email-Adresse
     * @param rawPassword Das Klartext-Passwort (wird gehashed)
     * @param role Die Rolle (ADMIN oder USER)
     * @return Der gespeicherte User mit generierter ID
     * @throws IllegalArgumentException wenn Username/Email bereits existiert
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
     * Findet einen User by Username.
     *
     * @param username Der Username
     * @return Optional mit User oder empty
     */
    public Optional<AppUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Authentifiziert einen User.
     *
     * @param username Der Username
     * @param rawPassword Das eingegebene Passwort
     * @return Optional mit User wenn Login erfolgreich, sonst empty
     */
    public Optional<AppUser> authenticateUser(String username, String rawPassword) {
        // User suchen
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
     * Hilfsmethode: Email-Validation.
     *
     * @param email Die zu prüfende Email
     * @return true wenn Email-Format gültig
     */
    private boolean isValidEmail(String email) {
        return email != null &&
                email.contains("@") &&
                email.length() > 3;
    }

    /**
     * Prüft ob ein Username verfügbar ist.
     *
     * @param username Der zu prüfende Username
     * @return true wenn verfügbar
     */
    public boolean isUsernameAvailable(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Prüft ob eine Email verfügbar ist.
     *
     * @param email Die zu prüfende Email
     * @return true wenn verfügbar
     */
    public boolean isEmailAvailable(String email) {
        return userRepository.existsByEmail(email);
    }
}
