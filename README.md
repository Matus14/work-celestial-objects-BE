# Space Catalog - Backend API

This is the backend part of the **Space Catalog** application — an educational and informative project that provides data about celestial objects such as planets, moons, comets, and asteroids.  
The backend is responsible for storing, managing, and exposing information about celestial objects through a REST API built with Spring Boot.

---

## Features

- REST API built with **Spring Boot**
- Uses **PostgreSQL** database with Flyway migrations
- CRUD operations for celestial objects
- Automatically managed timestamps (`created_at`, `updated_at`)
- Database trigger for auto-updating `updated_at` on every record change
- Validation and exception handling with clear HTTP responses
- DTO layer separating request and response models
- Service layer with validation logic and error handling
- Clean architecture and modular code structure
- CORS enabled for integration with a frontend (React/Vite)

---

## Technologies Used

- Java 17+
- Spring Boot (Web, Data JPA, Validation)
- PostgreSQL + Flyway
- Lombok
- Git & GitHub
- JUnit 5 & Mockito (unit testing)

---

## Learning Objectives

- REST API development with Spring Boot
- JPA & Hibernate integration with PostgreSQL
- Versioned database management using Flyway migrations
- Layered architecture (Controller / Service / Repository / Entity / DTO)
- Data validation and error handling using `@Valid`, `ResponseStatusException`
- Lombok annotations (`@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`)
- Global exception handling with `@ControllerAdvice`
- Implementing DB trigger for auto-updated timestamps
- Writing JUnit 5 & Mockito tests (happy/error paths, mocks, captors)
- GitHub project workflow: commit, push, and version control best practices

---

## Project Structure
src
└── main
└── java/com/matus/spacecatalog_backend
├── controller
│ └── CelestialObjectController.java
├── dto
│ ├── CelestialObjectRequestDTO.java
│ └── CelestialObjectResponseDTO.java
├── entity
│ └── CelestialObject.java
├── repository
│ └── CelestialObjectRepository.java
├── service
│ └── CelestialObjectService.java
└── SpacecatalogBackendApplication.java
└── resources
├── db/dev/migration
│ ├── V1__init.sql
│ ├── V2__add_updated_at.sql
│ ├── V3__seed_data.sql
│ └── V4__trigger_updated_at.sql
├── application.properties
└── test/java/com/matus/spacecatalog_backend
└── CelestialObjectServiceTest.java



## API Endpoints

| Method | Endpoint | Description |
|--------|-----------|-------------|
| **POST** | `/api/celestialobjects` | Create a new celestial object |
| **GET** | `/api/celestialobjects` | Retrieve all celestial objects |
| **GET** | `/api/celestialobjects/{id}` | Retrieve a celestial object by ID |
| **PUT** | `/api/celestialobjects/{id}` | Update an existing celestial object |
| **DELETE** | `/api/celestialobjects/{id}` | Delete a celestial object by ID |

---

## Database Migrations (Flyway)

| Version | File | Description |
|----------|------|-------------|
| **V1** | `V1__init.sql` | Creates main `celestial_object` table |
| **V2** | `V2__add_updated_at.sql` | Adds `updated_at` column |
| **V3** | `V3__seed_data.sql` | Inserts initial dataset (planets, comet) |
| **V4** | `V4__trigger_updated_at.sql` | Creates trigger for auto-updating `updated_at` |


## AUTHOR

Created by Matúš Bučko as part of a full-stack educational portfolio project “Space Catalog”.
The backend module was built using Spring Boot, PostgreSQL, Flyway, and REST API architecture, focusing on clean design, modularity, and real-world backend development practices.
