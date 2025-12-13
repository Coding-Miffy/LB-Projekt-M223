# Projektdokumentation
**Modul**: 223   
**Autor:innen**: Natascha Blumer & Sascha Ritter  
**Datum**: 13.12.2025    
**Version**: 1.0  

## Einleitung
Im Modul 223 erweitern wir eine bestehende Anwendung aus den Vorprojekten, um sie für mehrere Benutzer:innen gleichzeitig nutzbar zu machen. Ziel ist es, ein realitätsnahes Multi-User-System zu entwickeln, das sowohl technische als auch organisatorische Anforderungen an moderne Webanwendungen berücksichtigt.

## Projektidee
Die Grundidee des Projekts besteht darin, den bereits vorhandenen *EONET Natural Events Tracker* aus den Modulen 294 und 295 weiterzuentwickeln und um Multiuserfähigkeit zu ergänzen. Dazu gehören u. a. Authentifizierung, Rollenverwaltung und neue Interaktionsmöglichkeiten. Das Projekt untersucht, wie sich eine bestehende Anwendung so erweitern lässt, dass verschiedene Nutzergruppen klar voneinander getrennte Funktionen nutzen können.

## Anforderungsanalyse
Die Anforderungen wurden auf Basis typischer Nutzungsszenarien definiert und in Form von User Stories festgehalten. Sie bilden die Grundlage für das fachliche Verhalten der Anwendung und steuern sowohl die technische Umsetzung als auch die spätere Testabdeckung.

### User Stories
Die folgenden User Stories beschreiben typische Nutzungsszenarien aus Sicht der Anwender:innen. Sie helfen, die fachlichen Anforderungen verständlich zu formulieren und den Fokus auf die Nutzerbedürfnisse zu legen.

- **User Story 1**: [Live-Events für Besucher:innen](https://github.com/Coding-Miffy/LB-Projekt-M223/issues/8)
- **User Story 2**: [Events favorisieren für eingeloggte User](https://github.com/Coding-Miffy/LB-Projekt-M223/issues/9)
- **User Story 3**: [Event-Verwaltung für Admins](https://github.com/Coding-Miffy/LB-Projekt-M223/issues/10)

## Backend
Das Backend dient als zentrale Schicht der Anwendung und stellt sämtliche Funktionen über eine REST-API bereit. Es folgt einer klar strukturierten Architektur mit Controller-, Service- und Repository-Layern und bildet damit die technische Grundlage für Authentifizierung, Datenverarbeitung und Kommunikation mit dem Frontend.

### Layer-Architektur-Diagramm
Die Backend-Architektur folgt einem klar strukturierten Layered Architecture Pattern, um Verantwortlichkeiten sauber zu trennen und die Wartbarkeit des Systems sicherzustellen. Das React-Frontend kommuniziert ausschliesslich über eine REST-API mit dem Backend, welches aus Controller-, Service- und Repository-Schichten besteht.

**Jede Schicht übernimmt dabei eine klar definierte Aufgabe**: 

- **Controller Layer**: Verarbeitet HTTP-Requests und delegiert die Geschäftslogik an die Services.
- **Service Layer**: Enthält die zentrale Business-Logik, einschliesslich Authentifizierung, Event-Verwaltung und Favoriten-Transaktionen.
- **Repository Layer**: Ist für den Zugriff auf die PostgreSQL-Datenbank zuständig und abstrahiert die Persistenz mittels JPA.

Die Datenbank speichert alle Benutzer:innen, Events und Favoritenbeziehungen. Durch diese Aufteilung entsteht eine übersichtliche und erweiterbare Backend-Struktur, die eine klare Trennung zwischen Präsentation, Logik und Datenzugriff gewährleistet.

- [Zum Layer-Architektur-Diagramm](/resources/backend-architecture-diagram.jpg)

### ER-Diagramm
Das Datenbankmodell bildet die zentralen Objekte der Applikation ab und basiert auf einer relationalen Struktur in PostgreSQL.
Benutzer:innen (`app_users`) und Events (`events`) stehen in einer **Many-to-Many-Beziehung**, die über die Tabelle `event_favorites` aufgelöst wird. Diese Tabelle speichert, welche Benutzer:innen welche Events als Favoriten markiert haben.

Jedes Event kann von mehreren Benutzer:innen favorisiert werden, während ein:e Benutzer:in mehrere Events als Favoriten speichern kann. Die Verwendung einer separaten Favoriten-Tabelle ermöglicht eine flexible Erweiterung und stellt eine saubere Normalisierung der Daten sicher. Fremdschlüsselbeziehungen gewährleisten dabei die referenzielle Integrität zwischen Benutzern, Events und Favoriten.

- [Zum ER-Diagramm](/resources/database-diagram.jpg)

### JWT-Authentifizierungs-Flow
Die Authentifizierung der Applikation basiert auf **JSON Web Tokens (JWT)** und folgt einem **stateless Security-Modell**. Nach einem erfolgreichen Login erhält das Frontend einen signierten JWT-Token, der die Benutzeridentität und Rolle enthält. Der Token wird im Browser gespeichert und bei jedem weiteren API-Aufruf im Authorization-Header mitgesendet. Das Backend prüft den Token bei jeder Anfrage über einen Security-Filter, validiert seine Signatur und extrahiert die Benutzerinformationen. Auf diese Weise können geschützte Endpoints nur von authentifizierten und autorisierten Benutzer:innen aufgerufen werden, ohne dass das Backend eine Session verwalten muss.

- [Zum JWT-Authentifizierungs-Flow](/resources/jwt-auth-flow.jpg)

### Technologie-Stack

| Technologie | Version | Verwendung |
|:--|:-:|:--|
| Java | 21 | Programmiersprache für das Backend |
| Spring Boot | 3.3.7 | Zentrales Backend-Framework |
| Spring Web | 3.3.7 | Implementierung der REST-API |
| Spring Security | 6.x | Authentifizierung und Autorisierung |
| JWT (JJWT) | 0.11.5 | Token-basierte Authentifizierung |
| BCrypt | - | Sicheres Hashing von Passwörtern |
| Spring Data JPA / Hibernate | 6.x | ORM für Datenbankzugriffe |
| PostgreSQL | 16 | Relationale Produktivdatenbank |
| H2 | - | In-Memory-Datenbank für Tests |
| Maven | 3.x | Build-Tool und Dependency-Management |
| Springdoc OpenAPI | 2.3.0 | API-Dokumentation (Swagger UI) |
| Spring Boot Test | 3.3.7 | Backend-Tests |
| Spring Security Test | 6.x | Tests für Security-Konfiguration |

#### Beschreibung:

Das Backend wurde mit **Java 21** und **Spring Boot** umgesetzt und stellt eine REST-basierte API für das Frontend bereit. Über **Spring Web** werden HTTP-Anfragen verarbeitet und an Controller und Services weitergeleitet.  
Die **Authentifizierung und Autorisierung** erfolgt mit **Spring Security** und **JWT**. Nach dem Login wird ein JWT-Token generiert, welcher bei geschützten Requests im HTTP-Header mitgesendet und serverseitig validiert wird. Passwörter werden dabei sicher mit **BCrypt** gehasht gespeichert.  
Der Datenbankzugriff erfolgt über **Spring Data JPA** mit **Hibernate** als ORM. Als Produktivdatenbank wird **PostgreSQL** verwendet, während für automatisierte Tests eine **H2 In-Memory-Datenbank** eingesetzt wird. Der Build-Prozess wird mit **Maven** gesteuert. Zusätzlich ermöglicht **Springdoc OpenAPI** eine übersichtliche Dokumentation der REST-Schnittstellen. Backend- und Security-Tests werden mit **Spring Boot Test** und **Spring Security Test** umgesetzt.

## Frontend
tbd

### Component-Hierarchie-Diagramm
- [Zum Component-Hierarchie-Diagramm](/resources/component-architecture-diagram.jpg)

### State-Management-Flow
tbd  
[Diagramm]

### API-Integration-Diagramm
tbd  
[Diagramm]

### Technologie-Stack

| Technologie               | Version        | Verwendung                                     |
|:--------------------------|:--------------:|:--------------------------------------------- |
| JavaScript                | -              | Programmiersprache für das Frontend           |
| React                     | 19.x           | Haupt-Framework für die Benutzeroberfläche     |
| React Router DOM          | 7.x            | Client-seitiges Routing                        |
| Axios                     | 1.x            | HTTP-Client zur API-Kommunikation              |
| Leaflet                   | 1.x            | Anzeigen interaktiver Karten                   |
| React-Leaflet             | 5.x            | Integration der Leaflet-Bibliothek in React    |
| Vite                      | 6.x            | Build-Tool und lokaler Entwicklungsserver      |
| ESLint                    | 9.x            | Überprüfung des Codes auf Best Practices       |
| Vitest                    | 3.x            | Test-Framework für Unit Tests                 |
| Testing Library           | 16.x           | Test-Framework für Komponenten-Tests           |

#### Beschreibung:

Das Frontend wurde mit **React 19.x** entwickelt, um eine moderne, performante und wartbare Benutzeroberfläche zu ermöglichen. Mithilfe von **React Router DOM** wird ein flexibles Routing-System für client-seitige Navigation eingebettet.

Zur API-Kommunikation mit dem Backend wird **Axios** eingesetzt, und für die Darstellung interaktiver Karten wird **Leaflet** in Kombination mit **React-Leaflet** verwendet. Das Styling von Karten sowie deren Integration in die Benutzeroberfläche profitiert dabei von dieser nahtlosen React-Library.

Als Entwickler-Tool wird **Vite** verwendet, das schnelle Builds und Live-Reload-Funktionen für die Entwicklung bietet. Für die Codequalität und Einhaltung von Best Practices sorgt **ESLint**.

Die Tests des Frontends stützen sich auf **Vitest**, ein modernes Unit-Test-Framework, sowie auf die **Testing Library**, die pragmatische Werkzeuge für das Testen von Komponenten bereitstellt.

Durch die Verwendung von **TypeScript-Typdefinitionspaketen (@types)** wird sichergestellt, dass die Entwicklung typsicher ist, auch wenn der Typsicherheit explizit kein Fokus auf **TypeScript** gelegt wird.

## Sicherheitskonzept
Die Anwendung nutzt ein **JWT-basiertes Security-Modell**, um Benutzer eindeutig zu authentifizieren und Rollen sauber zu trennen. Passwörter werden mit **BCrypt** gehasht gespeichert, sodass keine Klartextdaten in der Datenbank liegen. Beim Login erstellt das Backend über den `JwtService` ein signiertes JWT, das Benutzername, Rolle und Ablaufzeit enthält. Der Token wird im Frontend gespeichert und bei jedem Request automatisch per **Authorization-Header** mitgesendet.

Die **SecurityConfig** schützt alle Endpoints standardmässig und erlaubt nur definierte öffentliche Routen. Ein eigener `JwtAuthenticationFilter` prüft bei jedem Request den Token, lädt die Benutzerinformationen und setzt bei Gültigkeit die Authentifizierung in den SecurityContext. Das System arbeitet vollständig **stateless**, da keine Server-Sessions verwendet werden.

Über die Rollen `USER` und `ADMIN` wird gesteuert, wer Events ansehen oder verwalten darf. Das Frontend setzt zusätzlich **Protected Routes** ein, damit nur berechtigte Benutzer:innen sensible Seiten aufrufen können. Dadurch entsteht ein klarer, mehrfach abgesicherter Authentifizierungs- und Autorisierungsfluss.

## Testplan Backend
Der Backend-Testplan überprüft die wichtigsten sicherheitsrelevanten Funktionen der API. Dabei werden insbesondere Rollenrechte (`USER`/`ADMIN`) und der Zugriff auf geschützte Endpunkte automatisiert getestet.

### Testfälle
| ID | Name | Klasse | Testziel | Status |
|:-:|:--|:--|:--|:-:|
| BE-01 | Zugriff auf Archiv-Events für eingeloggte Benutzer:innen | `EventControllerSecurity` | Sicherstellen, dass User:innen mit Rolle USER geschlossene Events (`closed`) abrufen dürfen | ✅ |
| BE-02 | Event-Erstellung durch Rolle USER wird blockiert | `EventControllerSecurity` | Sicherstellen, dass normale User:innen keine Events erstellen dürfen | ✅ |
| BE-03 | Event-Erstellung durch Rolle ADMIN | `EventControllerSecurity` | Sicherstellen, dass Admins Events erfolgreich erstellen können | ✅ |

### Testumgebung
- **Test-Framework**: JUnit 5
- **Mocking**: Mockito
- **Datenbank**: H2 (In-Memory)
- **HTTP-Simulation**: Spring MockMvc
- **Testprofil**: `test`-Profil mit isolierter Konfiguration

### Durchführung: BE-01 - Zugriff auf Archiv-Events für eingeloggte Benutzer:innen
**Methode**: `getEventsByStatus_asUser_shouldReturn200()`  

Ein Mock-User mit Rolle `USER` sendet einen GET-Request auf `/api/events/status/closed`. Der Test prüft, ob der Zugriff erlaubt ist und der Server mit **200 OK** antwortet.

### Durchführung: BE-02 - Event-Erstellung durch Rolle USER wird blockiert
**Methode**: `createEvent_asUser_shouldReturn403()`  

Ein Mock-User mit Rolle `USER` versucht per POST-Request ein neues Event zu erstellen. Erwartet wird eine **403 Forbidden**-Antwort, da nur Admins Events anlegen dürfen.

### Durchführung: BE-03 - Event-Erstellung durch Rolle ADMIN
**Methode**: `createEvent_asAdmin_shouldReturn201()`

Ein Mock-User mit Rolle `ADMIN` sendet einen gültigen POST-Request an `/api/events/create`. Der Test überprüft, dass das Event erfolgreich erstellt wird und der Server **201 Created** zurückgibt.

## Testplan Frontend
Der Frontend-Testplan überprüft die grundlegenden Funktionalitäten und die Benutzerinteraktion mit den wichtigsten Komponenten der Anwendung. Dabei wird sichergestellt, dass Formulare korrekt validieren, die Navigation je nach Benutzerrolle angepasst wird und Events zuverlässig erstellt werden können.

### Testfälle
| ID     | Name                                        | Komponente         | Testziel                                                                 | Status |
|:------:|:------------------------------------------ |:------------------ |:----------------------------------------------------------------------- |:-----: |
| FE-01  | Validierung und Login                      | `LoginForm`        | Überprüfen der Formularvalidierung und Aufruf der `onLogin`-Funktion.   | ✅     |
| FE-02  | Dynamische Navigation                      | `Navigation`       | Anzeigen von Login-Link oder Benutzerinfos abhängig von der Authentifizierung. | ✅     |
| FE-03  | Validierung und Event-Erstellung           | `CreateEventForm`  | Prüfen der Formularvalidierung und Übergabe der Event-Daten an `onEventSubmit`. | ✅     |

### Testumgebung
- **Test-Framework:** Vitest
- **Testing Library:** React Testing Library
- **Utility:** jsdom für DOM-Tests
- **Testprofil:** Lokale Entwicklungsumgebung mit isolierter Konfiguration

### Durchführung: FE-01 - Validierung und Login
**Komponente:** `LoginForm`  
**Methode:** `shows validation errors and calls onLogin when form valid()`  

In diesem Test wird überprüft, ob das Login-Formular korrekt validiert und auf Benutzereingaben reagiert. Zunächst wird ein Absenden ohne Eingaben simuliert. Erwartung: Fehlermeldungen für fehlende Felder werden angezeigt, und die `onLogin`-Funktion wird nicht aufgerufen. Danach wird das Formular ausgefüllt, und die Funktion `onLogin` wird geprüft. Erwartung: Sie wird mit den korrekten Eingabewerten aufgerufen.

### Durchführung: FE-02 - Dynamische Navigation
**Komponente:** `Navigation`  
**Methode:** `shows Login link when not authenticated()`  
**Methode:** `shows user info and logout when authenticated and calls logout()`  

Dieser Test überprüft die Navigationsleiste unter verschiedenen Authentifizierungszuständen. Erwartung: Nicht-authentifizierte Benutzer sehen einen Login-Link, während authentifizierte Benutzer ihren Namen, ihre Rolle und einen Logout-Button angezeigt bekommen. Das Klicken auf den Logout-Button ruft die `logout`-Funktion einmalig auf.

### Durchführung: FE-03 - Validierung und Event-Erstellung
**Komponente:** `CreateEventForm`  
**Methode:** `validates required fields and calls onEventSubmit with event data()`  

Hier wird das Formular zur Erstellung von Events getestet. Erwartung: Beim Absenden eines leeren Formulars werden Fehlermeldungen für fehlende Felder angezeigt, und die Funktion `onEventSubmit` wird nicht aufgerufen. Nach korrektem Ausfüllen wird geprüft, ob die `onEventSubmit`-Funktion mit den eingegebenen Event-Daten aufgerufen wird.

---

Dieser Abschnitt stellt die wichtigsten Tests des Frontends dar und orientiert sich an einer klaren Struktur, die für eine gute Nachvollziehbarkeit sorgt. Falls die Testfälle oder Details erweitert werden sollen, lass es mich wissen!  

## Installationsanleitung
Diese Anleitung beschreibt die vollständige lokale Einrichtung der Anwendung. Sie führt durch die Installation und Konfiguration von Backend und Frontend, das Aufsetzen der Datenbank sowie das Setzen aller benötigten Umgebungsvariablen, sodass das Projekt anschliessend direkt gestartet und genutzt werden kann.

### Voraussetzungen
Für die Ausführung werden folgende Komponenten benötigt:

**Backend**:
- **Java Development Kit (JDK) 17** oder neuer
- **Maven**
- **Git** (zum Klonen des Repositories)
- **Docker**
- **Docker Compose**

**Frontend**:
- **Node.js** (empfohlene LTS-Version)
- **npm** (wird automatisch mit Node.js installiert)

**Empfohlene IDE**:
- **Backend**: IntelliJ
- **Frontend**: Visual Studio Code

### Projekt vorbereiten
**Projektverzeichnis klonen oder entpacken**:

```bash
git clone https://github.com/Coding-Miffy/LB-Projekt-M223.git
cd LB-Projekt-M223
```

### Backend installieren

#### PostgreSQL-Datenbank via Docker Compose starten
Die PostgreSQL-Datenbank wird über Docker Compose gestartet. Dadurch wird automatisch ein Container mit der korrekten Konfiguration bereitgestellt. Zuerst müssen aber Benutzername und Passwort für die Datenbank definiert werden.  

**Benutzername und Passwort im `docker-compose.yml` definieren**:

```yaml
POSTGRES_USER: [YOUR-DB-USER] # TODO: Mit tatsächlichem Usernamen ersetzen
POSTGRES_PASSWORD: [YOUR-DB-PASSWORD] # TODO: Mit tatsächlichem Passwort ersetzen
```

**Ins Docker-Verzeichnis wechseln und Container starten**:

```bash
cd backend/docker
docker-compose up -d
```

**Verbindungsdetails der Datenbank**:

```text
Host: localhost
Port: 5432
Datenbank: eonet_multiuser_app
Benutzer: [YOUR-DB-USER]
Passwort: [YOUR-DB-PASSWORD]
```

#### Beispiel-Daten einmalig einfügen
Im Verzeichnis `src/main/resources/` befindet sich die Datei `data.sql`, mit der einige Beispiel-Events und Userkonten in die Datenbank eingefügt werden können. Dieser Schritt ist optional und sollte **nur einmalig** ausgeführt werden.

**Im Terminal ins Verzeichnis wechseln und folgenden Befehl ausführen**:

```bash
Get-Content data.sql | docker exec -i eonet_multiuser_postgres psql -U [YOUR-DB-USER] -d eonet_multiuser_app
```

#### Login-Daten für die Beispiel-User

```txt
username: admin, password: admin123, role: ADMIN;
username: admin2, password: admin123, role: ADMIN;
username: user1, password: player123, role: USER;
username: user2, password: player123, role: USER;
```

#### Environment-Variablen in IntelliJ setzen
Das Backend verwendet sensible Konfigurationswerte wie Datenbank-Zugangsdaten und den JWT-Secret Key nicht direkt im Code.  
Stattdessen werden diese Werte über Environment-Variablen eingelesen (siehe `application.properties`):

```properties
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

jwt.secret=${JWT_SECRET}
jwt.expiration=86400000
```

Damit das Backend korrekt startet, müssen alle drei Variablen gesetzt werden:

- `DB_USERNAME` – Benutzername der PostgreSQL-Datenbank
- `DB_PASSWORD` – Passwort der PostgreSQL-Datenbank
- `JWT_SECRET` – sicherer Schlüssel zur Signierung der JWT-Tokens

**In IntelliJ müssen diese Variablen vor dem Start folgendermassen definiert werden**:

1. Menü **Run > Edit Configurations**
2. Run-Konfiguration auswählen
3. Unter **Environment variables** eintragen:

```txt
DB_USERNAME=[YOUR-DB-USER]; DB_PASSWORD=[YOUR-DB-PASSWORD]; JWT_SECRET=[YOUR-JWT-SECRET]
```

>[!IMPORTANT]
>`DB_USERNAME` und `DB_PASSWORD` müssen exakt den Werten entsprechen, die im `docker-compose.yml` für PostgreSQL festgelegt wurden.

>[!TIP]
>Der `JWT_SECRET` sollte ein langer zufälliger Schlüssel (min. 32 Zeichen) sein. Er kann mit dem [JWT Secret Key Generator](https://jwtsecrets.com/) generiert werden. 

### Backend starten
**Zurück ins Projektverzeichnis wechseln und das Backend mit Maven starten**:

```bash
mvn spring-boot:run
```

Die Anwendung ist anschliessend unter [http://localhost:8080](http://localhost:8080) erreichbar.

### Frontend installieren

#### Voraussetzungen
Für die Ausführung und Entwicklung des Frontends werden folgende Komponenten benötigt:
- **Node.js** (empfohlene LTS-Version)
- **npm** (wird automatisch mit Node.js installiert)
- **Git** (zum Klonen des Repositories)

#### Projekt vorbereiten

1. **Projektverzeichnis klonen**  
   Wechsle in das gewünschte Verzeichnis und klone das Repository:
   ```bash
   git clone https://github.com/Coding-Miffy/LB-Projekt-M223.git
   cd LB-Projekt-M223/frontend
   ```

2. **Abhängigkeiten installieren**  
   Stelle sicher, dass alle notwendigen Abhängigkeiten aus dem `package.json` installiert werden:
   ```bash
   npm install
   ```

#### Entwicklungsserver starten
Starte den Entwicklungsserver mit folgendem Befehl:
```bash
npm run dev
```
Anschließend ist das Frontend unter [http://localhost:5173](http://localhost:5173) verfügbar (Standard-Port von Vite).

#### Build für die Produktion erstellen
Um einen optimierten Build für die Produktion zu erstellen, führe den folgenden Befehl aus:
```bash
npm run build
```
Die optimierten Dateien werden im Ordner `dist/` gespeichert und können anschließend z. B. mit einem Webserver bereitgestellt werden.

#### Vorschau des Builds starten
Um den Build vor der Bereitstellung zu testen, nutze diesen Befehl:
```bash
npm run preview
```
Der optimierte Build wird auf einem lokalen Vorschau-Server bereitgestellt.

#### Weitere Skripte
- **Codequalität sicherstellen**:
  ```bash
  npm run lint
  ```
  Dieser Befehl führt **ESLint** aus, um den Code auf Best Practices und Richtlinien zu prüfen.

- **Tests ausführen**:
  ```bash
  npm run test
  ```
  Dieser Befehl führt die Unit- und Komponenten-Tests mit **Vitest** aus.

#### Empfohlene IDE
Für die Entwicklung des Frontends wird **Visual Studio Code** empfohlen.  
Folgende Plugins können zusätzlich helfen:
- ESLint: Für die Überprüfung des Codes.
- React Developer Tools: Debugging von React-Komponenten.

#### Beispiel-Konfiguration
Optional kannst du Umgebungsvariablen in einer `.env`-Datei im `frontend`-Ordner hinterlegen. Die Datei könnte z. B. so aussehen:

```env
VITE_API_BASE_URL=http://localhost:8080/api
```
Hierbei ersetzt du den Wert von `VITE_API_BASE_URL` durch die URL des Backend-Servers.

>[!TIP]
>Sichere sensible Daten (wie API-Keys) nicht direkt im Code. Nutzen stattdessen `.env`-Dateien, die aus der Versionskontrolle ausgeschlossen werden können.

## Dokumentation
Die Applikation verfügt über zwei Arten technischer Dokumentation: **Swagger / OpenAPI** und **JavaDoc**. Beide werden nachfolgend kurz erläutert.

### Swagger / OpenAPI
Die OpenAPI-Dokumentation wurde mithilfe der `springdoc-openapi`-Bibliothek umgesetzt und durch gezielte Annotations wie `@Operation` und `@ApiResponse` ergänzt.
Nach dem Start der Applikation ist die Dokumentation unter folgender URL aufrufbar:

```bash
http://localhost:8080/swagger-ui/index.html
```

Dort sind alle verfügbaren Endpunkte übersichtlich dargestellt, inklusive Parameter, Rückgabewerten und HTTP-Statuscodes. Diese Oberfläche kann zur Exploration und zum Testen der API verwendet werden.

### JavaDoc
Der Quellcode ist ausführlich mit JavaDoc-Kommentaren versehen. Diese Dokumentation beschreibt die wichtigsten Klassen, DTOs und Methoden inklusive ihrer Aufgaben, Parameter und Rückgabewerte.

Um die JavaDoc-Dokumentation lokal zu generieren, kann folgender Maven-Befehl verwendet werden:

```bash
mvn javadoc:javadoc
```

Die generierte HTML-Dokumentation befindet sich anschliessend in diesem Verzeichnis:

```bash
target/site/apidocs/index.html
```

Diese Datei kann im Browser geöffnet werden, um einen detaillierten Überblick über die Klassenstruktur und deren Beziehungen zu erhalten.

## Hilfestellungen
Während der Entwicklung dieses Projekts haben wir auf verschiedene externe Hilfsmittel zurückgegriffen, um gezielt Unterstützung bei der Umsetzung, Strukturierung und Dokumentation zu erhalten.  
Sie trugen dazu bei, fachliche Unsicherheiten zu klären, bewährte Vorgehensweisen zu übernehmen und die Qualität der Lösung zu verbessern.

### ChatGPT
ChatGPT wurde punktuell als Unterstützung eingesetzt, insbesondere in folgenden Bereichen:

- **Rechtschreib- und Stilkorrektur** sowie Hilfestellung beim Verfassen von Textabschnitten der Projektdokumentation
- Unterstützung bei der **Formulierung von JavaDoc-Kommentaren** für zentrale Klassen und Methoden

### Graziano Laveder (Dozent M 223)
- Stand bei Rückfragen zu den Anforderungen unterstützend zur Seite und half dabei, den Rahmen des Projekts zu klären und einzuordnen.

### SideQuests M 223
Die im Rahmen des Moduls 223 bereitgestellten SideQuests dienten als wertvolle Orientierungshilfe und Grundlage für die Umsetzung:

- Beispiele und Lösungsansätze in den Übungsaufgaben gaben Struktur vor
- Technische Vorgaben und empfohlene Herangehensweisen konnten direkt übernommen oder angepasst werden
