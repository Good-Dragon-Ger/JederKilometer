# JederKilometer 🚴‍♂️

_Every kilometer counts – track your activities and support your team!_

## 🧠 Project Description

**JederKilometer** is a modern Spring Boot application that automatically fetches, stores, and analyzes activities from a Strava club.  
The goal is to make athletic performance visible and to motivate teams and individuals – for example, through donations per kilometer.

### Key Features:

- Integration with the **Strava API**
- Automatic fetching of club activities
- Support for multiple sport types
- Calculation of kilometers per activity/sport
- Visualization using charts
- User and team management
- Optional conversion of kilometers into monetary values

## 🧰 Technologies Used

- Java 21
- Spring Boot 3.2.4
- Spring Security with OAuth2 (currently GitHub login, Strava planned)
- Spring Data JDBC
- Thymeleaf
- PostgreSQL & H2 (for local development)
- Flyway for database migration
- Docker & Docker Compose

## 🧱 Architecture

The project uses a customized **Onion Architecture**:

- **Domain Layer** – Core business logic & models  
  - `domain.model` – e.g., `Aufzeichnung` (Activity), `Sportler` (Athlete), `Sportart` (Sport Type)
  - `domain.service` – e.g., `KMBerechnung` (KM Calculation)

- **Application Layer** – Coordination & use cases  
  - `application.service` – Services like `StravaService`
  - `application.service.repository` – Repository interfaces

- **Adapter Layer** – External communication  
  - `adapter.web` – REST controllers, security config  
  - `adapter.database` – Database repository implementations

## ⚙️ Setup & Configuration

### Prerequisites

- Java 21 or higher
- PostgreSQL (for production) or H2 (local)
- Strava API credentials
- Docker (optional)

### Configuration (`application.properties`)

Configuration can be provided via `application.properties` or entirely through **environment variables** (recommended for deployment).

```properties
# Database
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

## 🚀 Running the Application

### With Gradle

```bash
./gradlew build
./gradlew bootRun
```

### With Docker

```bash
docker build -t jederkilometer .
docker-compose up
```

The application will be available at: `http://localhost:8080`

## 🔐 Usage

1. Open `http://localhost:8080`
2. the main application with statistics is publicly accessible.
3. **A login (currently via GitHub) is only required for the admin area** accessible via `/login`.
4. **Strava login is planned and will replace GitHub in the future**.
5. admin functions are accessible via `/admin` and you will be redirected after successful login`

## 🧪 Testing & Native Build

```bash
# Run tests
./gradlew test
```

## 📈 Contributions

If you find bugs or want to suggest new features, please open an [issue](https://github.com/Good-Dragon-Ger/JederKilometer/issues).

## 👥 Contributors

- [dragonempire96](https://github.com/dragonempire96)

## 📄 License

This project is licensed under the [MIT License](https://github.com/Good-Dragon-Ger/JederKilometer/blob/main/LICENSE).
