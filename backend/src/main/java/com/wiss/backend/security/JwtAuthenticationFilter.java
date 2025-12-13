package com.wiss.backend.security;

import com.wiss.backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * <h2>
 *     JWT-Authentifizierungsfilter
 * </h2>
 *
 * <p>
 *     Dieser Filter wird vor jedem Request einmal ausgeführt und ist dafür zuständig,
 *     den im {@code Authorization}-Header mitgeschickten JWT-Token zu prüfen.
 *     Wenn der Token gültig ist, wird der zugehörige User im
 *     {@link SecurityContextHolder SecurityContext} von Spring Security eingetragen.
 * </p>
 *
 * <h3>Aufgaben des Filters:</h3>
 * <ul>
 *     <li>Authorization-Header auslesen und Bearer-Token extrahieren</li>
 *     <li>Username aus dem JWT über {@link JwtService} auslesen</li>
 *     <li>UserDetails über {@link UserDetailsService} laden</li>
 *     <li>Token-Signatur und Ablaufzeit validieren</li>
 *     <li>Bei Erfolg ein {@link UsernamePasswordAuthenticationToken} im SecurityContext setzen</li>
 * </ul>
 *
 * <p>
 *     In Kombination mit der {@link com.wiss.backend.config.SecurityConfig} bildet dieser Filter
 *     die Grundlage der stateless JWT-Authentifizierung im Backend.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-12-12
 *
 * @see JwtService
 * @see UserDetailsService
 * @see OncePerRequestFilter
 * @see com.wiss.backend.config.SecurityConfig
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Konstruktor mit Dependency Injection.
     *
     * @param jwtService Service zum Erstellen und Validieren von JWT-Tokens
     * @param userDetailsService Service zum Laden der Benutzer:innen aus der Datenbank
     */
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Führt die JWT-Authentifizierung für jeden eingehenden HTTP-Request durch.
     *
     * <p>Ablauf im Detail:</p>
     * <ol>
     *     <li>Authorization-Header lesen und prüfen, ob ein Bearer-Token vorhanden ist</li>
     *     <li>Username aus dem Token extrahieren</li>
     *     <li>Falls noch keine Authentication im {@link SecurityContextHolder} gesetzt ist:
     *         <ul>
     *             <li>UserDetails über {@link UserDetailsService#loadUserByUsername(String)} laden</li>
     *             <li>Token über {@link JwtService#validateToken(String, String)} validieren</li>
     *             <li>Bei Erfolg ein {@link UsernamePasswordAuthenticationToken} erzeugen
     *             und im SecurityContext setzen</li>
     *         </ul>
     *     </li>
     *     <li>Request an die restliche Filterkette weitergeben</li>
     * </ol>
     *
     * <p>
     *     Ist kein gültiger Bearer-Token vorhanden, wird der Request ohne Änderung
     *     des SecurityContext an die weitere Filterkette übergeben.
     * </p>
     *
     * @param request     der eingehende HTTP-Request
     * @param response    die HTTP-Response
     * @param filterChain die restliche Filterkette, die nach diesem Filter ausgeführt wird
     * @throws ServletException falls ein Servlet-Fehler auftritt
     * @throws IOException      falls ein IO-Fehler auftritt
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Authorization Header aus Request holen
        final String authHeader = request.getHeader("Authorization");

        // Prüfen ob Header existiert und mit "Bearer " startet
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Token aus dem Header extrahieren
        final String jwt = authHeader.substring(7);

        // Username aus dem Token extrahieren
        final String username = jwtService.extractUsername(jwt);

        // Prüfen ob User existiert und noch nicht authentifiziert ist
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // User-Details aus Datenbank laden
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Token validieren (Signatur + Ablaufdatum prüfen)
            if (jwtService.validateToken(jwt, username)) {

                // Authentication Object erstellen
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // Request-Details hinzufügen
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // User in SecurityContext setzen
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Zum nächsten Filter in der Chain
        filterChain.doFilter(request, response);
    }

}
