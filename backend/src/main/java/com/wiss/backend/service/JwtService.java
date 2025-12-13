package com.wiss.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * <h2>
 *     Service für JWT-Erstellung und -Validierung
 * </h2>
 *
 * <p>
 *     Diese Klasse kapselt alle sicherheitsrelevanten Operationen rund um
 *     JSON Web Tokens (JWT). Sie generiert Tokens für eingeloggte User,
 *     extrahiert Claims wie Username oder Rolle und validiert eingehende Tokens.
 * </p>
 *
 * <h3>Hauptaufgaben:</h3>
 * <ul>
 *     <li>Erstellen signierter JWT-Tokens mit Benutzerinformationen</li>
 *     <li>Validieren von Tokens (Signatur & Ablaufzeit)</li>
 *     <li>Extraktion einzelner Claims (z. B. Username, Rolle, Ablaufdatum)</li>
 *     <li>Bereitstellen eines sicheren Signierschlüssels</li>
 * </ul>
 *
 * <p>
 *     Der Service wird vom {@link com.wiss.backend.security.JwtAuthenticationFilter}
 *     verwendet, um bei jedem Request den Token auszulesen und den Benutzer zu authentifizieren.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-12-12
 *
 * @see com.wiss.backend.security.JwtAuthenticationFilter
 */
@Service
public class JwtService {

    /**
     * Geheimschlüssel zur Signierung des Tokens.
     * Wird über Environment Variables gesetzt (z. B. <code>jwt.secret</code>).
     */
    @Value("${jwt.secret}")
    private String secretKey;

    /**
     * Gültigkeitsdauer eines Tokens in Millisekunden.
     * Beispiel: 24h = 86 400 000 ms.
     */
    @Value("${jwt.expiration}")
    private long expirationTime;

    /**
     * <h3>Generiert einen neuen JWT-Token für einen User.</h3>
     *
     * <p>
     *     Enthaltene Claims:
     * </p>
     * <ul>
     *     <li><b>sub</b>: Username (Standard-Claim)</li>
     *     <li><b>role</b>: Benutzerrolle (Custom Claim)</li>
     *     <li><b>iat</b>: Ausstellungszeitpunkt</li>
     *     <li><b>exp</b>: Ablaufzeit</li>
     * </ul>
     *
     * @param username Username des eingeloggten Users
     * @param role     Rolle des Users (USER oder ADMIN)
     * @return signierter JWT als String
     */
    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        // Token bauen
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(System.currentTimeMillis()
                                + expirationTime))
                .signWith(getSigningKey(),
                        SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrahiert den Username (Subject) aus einem Token.
     *
     * @param token gültiger JWT
     * @return Username als String
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrahiert die Benutzerrolle aus dem Custom Claim <code>role</code>.
     *
     * @param token gültiger JWT
     * @return Rolle als String
     */
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    /**
     * Extrahiert das Ablaufdatum aus dem Token.
     *
     * @param token JWT
     * @return Ablaufzeitpunkt
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Generischer Helfer zum Auslesen beliebiger Claims.
     *
     * @param token Token, dessen Claims extrahiert werden sollen
     * @param claimsResolver Funktion, die den gewünschten Claim extrahiert
     * @param <T> Typ des Rückgabewerts
     * @return Claim-Wert
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Gibt alle Claims des Tokens zurück.
     *
     * @param token JWT
     * @return vollständige Claims-Struktur
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Prüft, ob der JWT bereits abgelaufen ist.
     *
     * @param token JWT
     * @return true, wenn Token nicht mehr gültig ist
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Prüft, ob Username im Token mit erwartetem Username übereinstimmt
     * und das Token nicht abgelaufen ist.
     *
     * @param token JWT
     * @param username erwarteter Username
     * @return true, wenn Token gültig und Benutzer korrekt ist
     */
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    /**
     * Erzeugt den kryptographischen Signierschlüssel aus dem konfigurierten Secret.
     *
     * @return HMAC-SHA Key
     */
    private Key getSigningKey() {
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
