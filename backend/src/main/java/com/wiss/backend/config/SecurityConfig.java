package com.wiss.backend.config;

import com.wiss.backend.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <h2>
 *     Sicherheitskonfiguration der Anwendung (Spring Security)
 * </h2>
 *
 * <p>
 *     Diese Klasse definiert alle sicherheitsrelevanten Einstellungen des Backends.
 *     Dazu gehören:
 * </p>
 * <ul>
 *     <li>Passwortverschlüsselung (BCrypt)</li>
 *     <li>Konfiguration der HTTP-Sicherheitsregeln</li>
 *     <li>Integration des JWT-Filters</li>
 *     <li>Aktivierung von rollenbasierten Berechtigungen</li>
 *     <li>Definition des AuthenticationManagers</li>
 * </ul>
 *
 * <p>
 *     Da das Backend vollständig mit JWT arbeitet, ist die Anwendung
 *     <b>zustandslos (stateless)</b> und verwendet weder HTTP-Sessions noch Cookies für Authentifizierung.
 *     Jeder Request muss daher ein gültiges JWT im Authorization-Header liefern.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-12-12
 *
 * @see com.wiss.backend.security.JwtAuthenticationFilter
 * @see org.springframework.security.config.annotation.web.builders.HttpSecurity
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    /**
     * Konstruktor injiziert den benutzerdefinierten JWT-Filter.
     *
     * @param jwtAuthFilter Filter, der bei jedem Request den Authorization-Header prüft
     */
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    /**
     * <h3>PasswordEncoder Bean</h3>
     *
     * <p>
     *     Verwendet <b>BCrypt</b>, um Passwörter sicher zu hashen.
     * </p>
     *
     * @return sicherer PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * <h3>AuthenticationManager Bean</h3>
     *
     * <p>
     *     Der AuthenticationManager delegiert Login-Vorgänge an Spring Security.
     *     Wird im AuthController benötigt, um User zu authentifizieren.
     * </p>
     *
     * @param config Standardkonfiguration von Spring
     * @return konfigurierter AuthenticationManager
     * @throws Exception bei fehlerhafter Konfiguration
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * <h3>SecurityFilterChain Bean</h3>
     *
     * <p>
     *     Definiert alle HTTP-Sicherheitsregeln der Anwendung:
     * </p>
     * <ul>
     *     <li>CSRF deaktiviert (für APIs notwendig)</li>
     *     <li>CORS aktiviert</li>
     *     <li>Public Endpoints (Login, Register, Swagger, Event-Filter)</li>
     *     <li>Alle anderen Endpoints benötigen JWT-Authentifizierung</li>
     *     <li>Session-Management auf <b>STATELESS</b></li>
     *     <li>JWT-Filter wird vor dem UsernamePasswordAuthenticationFilter ausgeführt</li>
     * </ul>
     *
     * @param http HTTP-Sicherheitsobjekt
     * @return konfigurierte SecurityFilterChain
     * @throws Exception bei fehlerhafter Konfiguration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configure(http))

                // Zugriffskontrolle für Endpoints
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/api/events/filter"
                        ).permitAll() // öffentlich zugänglich

                        // Alle anderen Endpoints benötigen JWT
                        .anyRequest().authenticated()
                )

                // JWT → keine Server-Sessions
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // JWT-Filter vor den Standard-Authentifizierungsfilter einfügen
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
