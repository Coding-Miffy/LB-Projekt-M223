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

@Service
public class JwtService {

    /**
     * Secret Key aus application.properties.
     */
    @Value("${jwt.secret}")
    private String secretKey;

    /**
     * Token Gültigkeit in Millisekunden (24h = 86400000ms).
     */
    @Value("${jwt.expiration}")
    private long expirationTime;

    /**
     * Generiert einen JWT Token für einen User.
     *
     * Claims die gesetzt werden:
     * - sub: Username (Standard JWT Claim)
     * - role: User Rolle (Custom Claim)
     * - iat: Issued At Timestamp
     * - exp: Expiration Timestamp
     *
     * @param username Der Username des Users
     * @param role Die Rolle des Users (ADMIN oder USER)
     * @return JWT Token als String
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
     * Extrahiert den Username aus einem Token.
     *
     * @param token Der JWT Token
     * @return Der Username (Subject)
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrahiert die Rolle aus einem Token.
     *
     * @param token Der JWT Token
     * @return Die Rolle als String
     */
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    /**
     * Extrahiert das Expiration Date aus einem Token.
     *
     * @param token Der JWT Token
     * @return Das Ablaufdatum
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Generische Methode um einen spezifischen Claim zu extrahieren.
     *
     * @param token Der JWT Token
     * @param claimsResolver Function die den Claim extrahiert
     * @return Der extrahierte Claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrahiert alle Claims aus einem Token.
     *
     * @param token Der JWT Token
     * @return Alle Claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Prüft, ob ein Token abgelaufen ist.
     *
     * @param token Der JWT Token
     * @return true wenn abgelaufen
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Validiert einen Token.
     *
     * @param token Der JWT Token
     * @param username Der Username zum Vergleich
     * @return true wenn Token gültig
     */
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    /**
     * Erstellt den Signing Key aus dem Secret String.
     *
     * @return Der Signing Key
     */
    private Key getSigningKey() {
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
