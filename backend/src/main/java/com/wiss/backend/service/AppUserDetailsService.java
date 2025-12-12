package com.wiss.backend.service;

import com.wiss.backend.repository.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <h2>
 *     Service zur Benutzerauflösung für Spring Security
 * </h2>
 *
 * <p>
 *     Diese Klasse implementiert das {@link UserDetailsService}-Interface und dient als zentrale
 *     Schnittstelle für Spring Security, um Benutzerinformationen aus der Datenbank zu laden.
 *     Sie wird automatisch vom Security-Framework aufgerufen, sobald eine Authentifizierung
 *     (z. B. über einen gültigen JWT-Token) stattfindet.
 * </p>
 *
 * <h3>Hauptaufgaben:</h3>
 * <ul>
 *     <li>Laden eines Benutzers anhand des Usernames</li>
 *     <li>Übergeben eines {@link UserDetails}-Objekts an Spring Security</li>
 *     <li>Auslösen einer Fehlermeldung, falls der Benutzer nicht existiert</li>
 * </ul>
 *
 * <p>
 *     Der Service arbeitet eng mit dem {@link AppUserRepository} sowie der
 *     Sicherheitskonfiguration in {@link com.wiss.backend.config.SecurityConfig} zusammen.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-12-12
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserRepository userRepository;

    /**
     * Konstruktor zur Initialisierung des User-Repositories.
     *
     * @param userRepository Repository zum Laden von Benutzerentitäten
     */
    public AppUserDetailsService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Lädt einen Benutzer anhand des Usernames aus der Datenbank.
     *
     * <p>
     *     Diese Methode wird automatisch von Spring Security aufgerufen, wenn eine
     *     Authentifizierung durchgeführt wird. Findet das Repository keinen passenden Benutzer,
     *     wird eine {@link UsernameNotFoundException} ausgelöst.
     * </p>
     *
     * @param username der in der Datenbank zu suchende Username
     * @return ein {@link UserDetails}-Objekt mit Benutzerinformationen
     * @throws UsernameNotFoundException wenn kein Benutzer mit diesem Namen existiert
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User nicht gefunden: " + username));
    }
}
