# Week 1 Practice — Job Posting API

> **Goal:** Validate everything from Week 1 in one focused practice session.
> **Time:** 1 day
> **Extendable:** Yes — same codebase continues into Week 2, 3, and 4

---

## Setup

Go to [start.spring.io](https://start.spring.io) and generate the project with this config:

| Field | Value |
|---|---|
| Project | Maven |
| Language | Java |
| Spring Boot | 3.5.x (latest stable) |
| Group | `com.raynald` |
| Artifact | `hireloop` |
| Packaging | Jar |
| Java | 17 |

**Dependencies to add:**
- Spring Web
- Spring Data JPA
- PostgreSQL Driver
- Lombok
- Validation

Extract the zip, open in IntelliJ, let Maven download everything.

---

## application.properties

```properties
server.port=8080

spring.datasource.url=jdbc:postgresql://localhost:5432/hireloop_db
spring.datasource.username=postgres
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

Create the database manually first:

```sql
CREATE DATABASE hireloop_db;
```

---

## Folder Structure

```
com.raynald.hireloop/
├── controller/
│   └── JobController.java
├── service/
│   └── JobService.java
├── repository/
│   └── JobRepository.java
├── entity/
│   └── Job.java
├── dto/
│   ├── CreateJobRequest.java
│   ├── UpdateJobRequest.java
│   └── JobResponse.java
├── mapper/
│   └── JobMapper.java
└── exception/
    └── ResourceNotFoundException.java
```

---

## The Case

Build a Job Posting API with a single `Job` entity.

### Entity Fields

| Field | Type | Notes |
|---|---|---|
| `id` | Long | Auto-generated |
| `title` | String | Not null |
| `department` | String | Not null |
| `location` | String | Not null |
| `status` | String | Defaults to `"OPEN"` on creation |
| `createdAt` | LocalDateTime | Auto-set by Hibernate |
| `updatedAt` | LocalDateTime | Auto-updated by Hibernate |

---

### Endpoints to Build

| Method | Endpoint | What it does |
|---|---|---|
| GET | `/api/v1/jobs` | Get all jobs |
| GET | `/api/v1/jobs/{id}` | Get one job by id |
| GET | `/api/v1/jobs/department/{department}` | Get all jobs by department |
| POST | `/api/v1/jobs` | Create a new job |
| PUT | `/api/v1/jobs/{id}` | Update an existing job |
| DELETE | `/api/v1/jobs/{id}` | Delete a job |

---

### Rules

- Clean layered structure — Controller → Service → Repository. Nothing skips a layer
- Controller never touches the entity directly — always go through DTOs
- `status` defaults to `"OPEN"` on creation. Client does not set this field manually
- If a job is not found by id, throw a `ResourceNotFoundException` (build this as a custom exception)
- Use `LAZY` fetch type on any relationships
- Use `@RequiredArgsConstructor` + `private final` for dependency injection — no `@Autowired` on fields

---

### DTOs

**CreateJobRequest** — what the client sends on POST:
```
title, department, location
```

**UpdateJobRequest** — what the client sends on PUT:
```
title, department, location, status
```

**JobResponse** — what the API returns:
```
id, title, department, location, status, createdAt
```

---

### Expected Request / Response Examples

**POST /api/v1/jobs**
```json
// Request body
{
  "title": "Backend Engineer",
  "department": "Engineering",
  "location": "Remote"
}

// Response — 201 Created
{
  "id": 1,
  "title": "Backend Engineer",
  "department": "Engineering",
  "location": "Remote",
  "status": "OPEN",
  "createdAt": "2026-06-17T10:00:00"
}
```

**GET /api/v1/jobs/1**
```json
// Response — 200 OK
{
  "id": 1,
  "title": "Backend Engineer",
  "department": "Engineering",
  "location": "Remote",
  "status": "OPEN",
  "createdAt": "2026-06-17T10:00:00"
}

// If not found — 404 Not Found
{
  "message": "Job not found with id: 1"
}
```

**PUT /api/v1/jobs/1**
```json
// Request body
{
  "title": "Senior Backend Engineer",
  "department": "Engineering",
  "location": "Hybrid",
  "status": "CLOSED"
}

// Response — 200 OK
{
  "id": 1,
  "title": "Senior Backend Engineer",
  "department": "Engineering",
  "location": "Hybrid",
  "status": "CLOSED",
  "createdAt": "2026-06-17T10:00:00"
}
```

**DELETE /api/v1/jobs/1**
```
// Response — 204 No Content (empty body)
```

---

## Done When

- [ ] All 6 endpoints work and return correct HTTP status codes
- [ ] All endpoints tested manually via Postman or any HTTP client
- [ ] Data actually persists in PostgreSQL — verify via pgAdmin or psql
- [ ] Job not found returns a proper error response, not a Spring default error page
- [ ] No `new SomeService()` anywhere — Spring handles all wiring

---

## What's Coming in Week 2 (Same Codebase)

You will extend this exact project with:

- Request validation — `@NotNull`, `@Size`, `@Email` on your DTOs
- Global exception handler — `@ControllerAdvice` for consistent error responses
- JWT authentication — secure your endpoints
- Role-based access control — `ADMIN` vs `USER` guards
- Unit + integration tests — JUnit + Mockito on your `JobService`

Build it clean now. Messy foundation = painful Week 2.
