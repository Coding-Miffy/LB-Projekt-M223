package com.wiss.backend.entity;

/**
 * <h2>
 *     Rollenmodell für Benutzer:innen des Systems
 * </h2>
 *
 * <p>
 *     Dieses Enum definiert die beiden verfügbaren Rollen innerhalb der Anwendung.
 *     Die Rolle bestimmt, welche Berechtigungen ein User besitzt und welche
 *     Aktionen im Backend ausgeführt werden dürfen.
 * </p>
 *
 * <h3>Rollenbeschreibung:</h3>
 * <ul>
 *     <li><b>ADMIN</b> – darf Events erstellen, bearbeiten und löschen</li>
 *     <li><b>USER</b> – kann Events ansehen und favorisieren</li>
 * </ul>
 *
 * <p>
 *     Die Rolle wird beim Registrieren eines neuen Users gesetzt und im JWT-Token
 *     als Claim gespeichert. Spring Security interpretiert die Rollen im Format
 *     <code>ROLE_ADMIN</code> bzw. <code>ROLE_USER</code>.
 * </p>
 *
 * @author Natascha Blumer
 * @version 1.0
 * @since 2025-12-12
 *
 * @see com.wiss.backend.entity.AppUser
 * @see org.springframework.security.core.GrantedAuthority
 */
public enum Role {
    ADMIN,
    USER
}
