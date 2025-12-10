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

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Diese Methode wird bei jedem Request ausgeführt
     *
     * @param request  Der eingehende HTTP Request
     * @param response Die HTTP Response
     * @param filterChain Die Chain von weiteren Filtern
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
