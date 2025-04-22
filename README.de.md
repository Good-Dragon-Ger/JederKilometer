# JederKilometer 🚴‍♂️

_Jeder Kilometer zählt – tracke deine Aktivitäten und unterstütze dein Team!_

## 🧠 Projektbeschreibung

**JederKilometer** ist eine moderne Spring Boot Anwendung, die Aktivitäten aus einem Strava-Club automatisch abruft, speichert und analysiert. Ziel ist es, sportliche Leistungen sichtbar zu machen und Teams sowie Einzelpersonen zu motivieren – z. B. durch Spenden pro Kilometer.

### Hauptfunktionen:

- Integration mit der **Strava API**
- Automatisches Abrufen von Aktivitäten eines Clubs
- Unterstützung mehrerer Sportarten
- Kilometerberechnung pro Aktivität/Sportart
- Visualisierung über Diagramme
- Benutzer- und Teamverwaltung
- Optionale Umrechnung von Kilometern in Geldbeträge

## 🧰 Verwendete Technologien

- Java 21
- Spring Boot 3.2.4
- Spring Security mit OAuth2 (GitHub Login, später Strava)
- Spring Data JDBC
- Thymeleaf
- PostgreSQL & H2 (für lokale Entwicklung)
- Flyway für Migrationsmanagement
- Docker & Docker Compose

## 🧱 Architektur

Das Projekt verwendet eine angepasste **Onion-Architektur**:

- **Domain Layer** – Geschäftslogik & Modelle  
  - `domain.model` – z. B. `Aufzeichnung`, `Sportler`, `Sportart`
  - `domain.service` – z. B. `KMBerechnung`

- **Application Layer** – Koordination & Use Cases  
  - `application.service` – Services wie `StravaService`
  - `application.service.repository` – Schnittstellen zu Repositories

- **Adapter Layer** – Kommunikation mit der Außenwelt  
  - `adapter.web` – REST-Controller, Security etc.  
  - `adapter.database` – Implementierungen für Datenzugriff

## ⚙️ Einrichtung & Konfiguration

### Voraussetzungen

- Java 21 oder höher
- PostgreSQL (für Produktion) oder H2 (lokal)
- Strava API Zugangsdaten
- Docker (optional)

### Konfiguration (`application.properties`)

Die Konfiguration kann entweder über `application.properties` oder vollständig über **Umgebungsvariablen** erfolgen (empfohlen für Deployment).


```properties
# Datenbank
spring.datasource.url=jdbc:postgresql://localhost:5432/jederkilometer
spring.datasource.username=your_username
spring.datasource.password=your_password

# Strava API
jederKilometer.strava.client-id=your_client_id
jederKilometer.strava.client-secret=your_client_secret
jederKilometer.strava.refresh-token=your_refresh_token
jederKilometer.strava.clubId=your_club_id
jederKilometer.strava.clubPage=1
jederKilometer.strava.pageEntries=30
```

## 🚀 Anwendung starten

### Mit Gradle

```bash
./gradlew build
./gradlew bootRun
```

### Mit Docker

```bash
docker build -t jederkilometer .
docker-compose up
```

Anwendung läuft dann unter `http://localhost:8080`

## 🔐 Nutzung

1. Öffne `http://localhost:8080`
2. Die Hauptanwendung mit Statistiken ist öffentlich zugänglich.
3. **Ein Login (aktuell via GitHub) ist nur für den Adminbereich erforderlich.** über `/login` erreichbar 
4. **Strava-Login ist geplant und wird GitHub zukünftig ersetzen.**
5. Admin-Funktionen sind über `/admin` erreichbar und wird man nach erfolgreichem Login weitergeleitet

## 🧪 Testing & Native Build

```bash
# Tests
./gradlew test

```

## 📈 Weiterentwicklung

Wenn du Bugs findest oder neue Features vorschlagen möchtest, eröffne bitte ein [Issue](https://github.com/Good-Dragon-Ger/JederKilometer/issues).


## 👥 Mitwirkende

- [dragonempire96](https://github.com/dragonempire96)

## 📄 Lizenz

Dieses Projekt steht unter der [MIT-Lizenz](https://github.com/Good-Dragon-Ger/JederKilometer/blob/main/LICENSE).
