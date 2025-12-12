# Projektdokumentation
**Modul**: 223   
**Autor:innen**: Natascha Blumer & Sascha Ritter  
**Datum**: tbd  
**Version**: 1.0  

## Einleitung
tbd

## Projektidee
tbd

## Anforderungsanalyse
tbd

### User Stories
Die folgenden User Stories beschreiben typische Nutzungsszenarien aus Sicht der Anwender:innen. Sie helfen, die fachlichen Anforderungen verständlich zu formulieren und den Fokus auf die Nutzerbedürfnisse zu legen.

- **User Story 1**: [Live-Events für Besucher:innen](https://github.com/Coding-Miffy/LB-Projekt-M223/issues/8)
- **User Story 2**: [Events favorisieren für eingeloggte User](https://github.com/Coding-Miffy/LB-Projekt-M223/issues/9)
- **User Story 3**: [Event-Verwaltung für Admins](https://github.com/Coding-Miffy/LB-Projekt-M223/issues/10)

### Use Case
tbd

>[!NOTE]
>Abschnitt "Anforderungsanalyse" vielleicht noch nicht vollständig
>- Kernaufgaben?

## Backend
tbd

### Layer-Architektur-Diagramm
Die Backend-Architektur folgt einem klar strukturierten Layered Architecture Pattern, um Verantwortlichkeiten sauber zu trennen und die Wartbarkeit des Systems sicherzustellen. Das React-Frontend kommuniziert ausschliesslich über eine REST-API mit dem Backend, welches aus Controller-, Service- und Repository-Schichten besteht.

**Jede Schicht übernimmt dabei eine klar definierte Aufgabe**: 

- **Controller Layer**: Verarbeitet HTTP-Requests und delegiert die Geschäftslogik an die Services.
- **Service Layer**: Enthält die zentrale Business-Logik, einschliesslich Authentifizierung, Event-Verwaltung und Favoriten-Transaktionen.
- **Repository Layer**: Ist für den Zugriff auf die PostgreSQL-Datenbank zuständig und abstrahiert die Persistenz mittels JPA.

Die Datenbank speichert alle Benutzer:innen, Events und Favoritenbeziehungen. Durch diese Aufteilung entsteht eine übersichtliche und erweiterbare Backend-Struktur, die eine klare Trennung zwischen Präsentation, Logik und Datenzugriff gewährleistet.

![Layer-Architektur-Diagramm](/resources/backend-architecture-diagram.jpg)

### ER-Diagramm
Das Datenbankmodell bildet die zentralen Objekte der Applikation ab und basiert auf einer relationalen Struktur in PostgreSQL.
Benutzer:innen (`app_users`) und Events (`events`) stehen in einer **Many-to-Many-Beziehung**, die über die Tabelle `event_favorites` aufgelöst wird. Diese Tabelle speichert, welche Benutzer:innen welche Events als Favoriten markiert haben.

Jedes Event kann von mehreren Benutzer:innen favorisiert werden, während ein:e Benutzer:in mehrere Events als Favoriten speichern kann. Die Verwendung einer separaten Favoriten-Tabelle ermöglicht eine flexible Erweiterung und stellt eine saubere Normalisierung der Daten sicher. Fremdschlüsselbeziehungen gewährleisten dabei die referenzielle Integrität zwischen Benutzern, Events und Favoriten.

![ER-Diagramm](/resources/database-diagram.jpg)

### JWT-Authentifizierungs-Flow
Die Authentifizierung der Applikation basiert auf **JSON Web Tokens (JWT)** und folgt einem **stateless Security-Modell**. Nach einem erfolgreichen Login erhält das Frontend einen signierten JWT-Token, der die Benutzeridentität und Rolle enthält. Der Token wird im Browser gespeichert und bei jedem weiteren API-Aufruf im Authorization-Header mitgesendet. Das Backend prüft den Token bei jeder Anfrage über einen Security-Filter, validiert seine Signatur und extrahiert die Benutzerinformationen. Auf diese Weise können geschützte Endpoints nur von authentifizierten und autorisierten Benutzer:innen aufgerufen werden, ohne dass das Backend eine Session verwalten muss.

![JWT-Authentifizierungs-Flow](/resources/jwt-auth-flow.jpg)

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

>[!NOTE]
>Abschnitt "Backend" vielleicht noch nicht vollständig
> - REST-Schnittstellen?
> - Vielleicht die Diagramme über Links einbinden? (sind gross, brauchen viel Platz)

## Frontend
tbd

### Component-Hierarchie-Diagramm
tbd  
[Diagramm]

### State-Management-Flow
tbd  
[Diagramm]

### API-Integration-Diagramm
tbd  
[Diagramm]

### Technologie-Stack
| Technologie | Version | Verwendung |
|:--|:-:|:--|
| tbd | tbd | tbd |

#### Beschreibung:

tbd (Beschreibung in eigenen Worten)

## Sicherheitskonzept
tbd

## Testplan
tbd

### Testfälle Backend
| ID | Name | Klasse | Testziel |
|:-:|:--|:--|:--|
| BE1 | tbd | tbd | tbd |

### Testfälle Frontend
| ID | Name | Komponente | Testziel |
|:-:|:--|:--|:--|
| FE1 | tbd | tbd | tbd |

### Durchführung der Tests
tbd

## Installationsanleitung
tbd

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
Im Verzeichnis `src/main/resources/data/` befindet sich die Datei `data.sql`, mit der einige Beispiel-Events und Userkonten in die Datenbank eingefügt werden können. Dieser Schritt ist optional und sollte **nur einmalig** ausgeführt werden.

**Im Terminal ins Verzeichnis wechseln und folgenden Befehl ausführen**:

```bash
Get-Content data.sql | docker exec -i eonet_multiuser_postgres psql -U [YOUR-DB-USER] -d eonet_multiuser_app
```

#### Environment-Variablen in IntelliJ setzen
Das Backend liest `DB_USERNAME` und `DB_PASSWORD` aus der Umgebung (siehe `application.properties`). 

**In IntelliJ müssen diese Variablen vor dem Start folgendermassen definiert werden**:

- Menü **Run > Edit Configurations**
- Run-Konfiguration auswählen
- Unter **Environment variables** eintragen:

```txt
DB_USERNAME=[YOUR-DB-USER]; DB_PASSWORD=[YOUR-DB-PASSWORD]
```

>[!IMPORTANT]
>Diese Variablen müssen zwingend mit dem im `docker-compose.yml` definierten Benutzernamen und Passwort für die Datenbank übereinstimmen.

### Backend starten
**Zurück ins Projektverzeichnis wechseln und das Backend mit Maven starten**:

```bash
mvn spring-boot:run
```

Die Anwendung ist anschliessend unter [http://localhost:8080](http://localhost:8080) erreichbar.

>[!NOTE]
>JWT-Token-Secret-Key?

### Frontend installieren
tbd

## Dokumentation
tbd

### Swagger / OpenAPI
tbd

### JavaDoc
tbd

## Hilfestellungen
Während der Entwicklung dieses Projekts haben wir auf verschiedene externe Hilfsmittel zurückgegriffen, um gezielt Unterstützung bei der Umsetzung, Strukturierung und Dokumentation zu erhalten.  
Sie trugen dazu bei, fachliche Unsicherheiten zu klären, bewährte Vorgehensweisen zu übernehmen und die Qualität der Lösung zu verbessern.

### ChatGPT
ChatGPT wurde punktuell als Unterstützung eingesetzt, insbesondere in folgenden Bereichen:

- tbd

### SideQuests M 223
Die im Rahmen des Moduls 223 bereitgestellten SideQuests dienten als wertvolle Orientierungshilfe und Grundlage für die Umsetzung:

- Beispiele und Lösungsansätze in den Übungsaufgaben gaben Struktur vor
- Technische Vorgaben und empfohlene Herangehensweisen konnten direkt übernommen oder angepasst werden
