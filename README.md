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

- **User Story 1**: [User Story 1 tbd](https://github.com/Coding-Miffy/LB-Projekt-M223/issues/8)
- **User Story 2**: [User Story 2 tbd](https://github.com/Coding-Miffy/LB-Projekt-M223/issues/9)
- **User Story 3**: [User Story 3 tbd](https://github.com/Coding-Miffy/LB-Projekt-M223/issues/10)

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
tbd  
[Diagramm]

### JWT-Authentifizierungs-Flow
tbd  
[Diagramm]

### Technologie-Stack
tbd 
| Technologie | Version | Verwendung |
|:--|:-:|:--|
| tbd | tbd | tbd |

tbd (Beschreibung in eigenen Worten)

>[!NOTE]
>Abschnitt "Backend" vielleicht noch nicht vollständig
> - REST-Schnittstellen?

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
tbd 
| Technologie | Version | Verwendung |
|:--|:-:|:--|
| tbd | tbd | tbd |

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
