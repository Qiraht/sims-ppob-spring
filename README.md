# SIMS PPOB API

Backend REST API for SIMS PPOB (Payment Point Online Bank) application.

## Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot 4.0.6
- **Database:** PostgreSQL (with Liquibase migrations)
- **Security:** Spring Security + JWT Authentication
- **Documentation:** SpringDoc OpenAPI (Swagger UI)
- **Containerization:** Docker multi-stage build

## Prerequisites

- Java 21
- Maven 3.9+
- PostgreSQL
- Docker & Docker Compose (optional)

## Setup

1. Clone the repository
   ```bash
   git clone https://github.com/your-username/ppob-sims-spring.git
   cd ppob-sims-spring
   ```

2. Copy `.env.example` to `.env` and fill in the values
   ```bash
   cp .env.example .env
   ```

3. Make sure PostgreSQL is running and the database configured in `.env` exists

## Running the App

### With Maven

```bash
mvn spring-boot:run
```

### With Docker Compose

```bash
docker compose up --build
```

The app will start on `http://localhost:8080` (or the `APP_PORT` defined in `.env`).

## API Endpoints

| # | Method | Endpoint             | Auth | Description         |
|---|--------|----------------------|------|---------------------|
| 1 | POST   | `/registration`      | No   | Register new user   |
| 2 | POST   | `/login`             | No   | Login & get JWT     |
| 3 | GET    | `/profile`           | Yes  | Get user profile    |
| 4 | PUT    | `/profile/update`    | Yes  | Update profile name |
| 5 | PUT    | `/profile/image`     | Yes  | Upload profile image (multipart/form-data, max 5MB, JPG/JPEG only) |
| 6 | GET    | `/banner`            | No   | Get all banners     |
| 7 | GET    | `/service`           | Yes  | Get all services    |
| 8 | GET    | `/balance`           | Yes  | Get wallet balance  |
| 9 | POST   | `/topup`             | Yes  | Top up balance      |
| 10 | POST   | `/transaction`       | Yes  | Create payment      |
| 11 | GET    | `/transaction/history` | Yes  | Transaction history (paginated) |

## Swagger UI

Access the interactive API documentation at:

```
http://localhost:8080/swagger-ui.html
```

## Project Structure

```
ppob-sims-spring/
├── .env.example                          # Environment variables template
├── docker-compose.yaml                   # Docker Compose config
├── Dockerfile                            # Multi-stage build
├── pom.xml                               # Maven dependencies
└── src/main/java/com/qiraht/ppob_sims_spring/
    ├── PpobSimsSpringApplication.java    # Entry point
    ├── config/                           # Security, Swagger, WebMvc configs
    ├── controller/                       # REST controllers
    ├── dto/                              # Request/Response DTOs
    ├── entity/                           # JPA entities
    ├── enums/                            # Status enums
    ├── exception/                        # Custom exceptions & handler
    ├── repository/                       # Spring Data repositories
    ├── service/                          # Business logic
    └── util/                             # JWT & invoice utilities
```
