# JobDock 🚀

A **Job Portal REST API** built with Spring Boot. Supports two roles — Job Seekers and Recruiters — with JWT-based authentication, role-based access control, and full job application lifecycle management.

---

## Features

- **Authentication** — Register and login with JWT tokens
- **Role-based access** — SEEKER and RECRUITER roles with method-level security
- **Job management** — Recruiters can post and manage job listings
- **Applications** — Seekers can apply to jobs, recruiters can shortlist or reject
- **Validation** — Request validation with meaningful error messages
- **Global exception handling** — Consistent error responses across all endpoints

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.x |
| Database | PostgreSQL |
| ORM | Spring Data JPA / Hibernate |
| Security | Spring Security + JWT (jjwt 0.12.3) |
| Utilities | Lombok, Maven |

---

## Getting Started

### Prerequisites

- Java 21+
- PostgreSQL installed and running
- Maven

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/jobdock.git
   cd jobdock
   ```

2. **Create the database**
   ```sql
   CREATE DATABASE jobdock_db;
   ```

3. **Configure `application.properties`**
   ```properties
   spring.application.name=JobDock

   spring.datasource.url=jdbc:postgresql://localhost:5432/jobdock_db
   spring.datasource.username=postgres
   spring.datasource.password=your_password

   jwt.secret=your_base64_encoded_secret
   jwt.expiration=86400000

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true
   spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
   ```

   > Generate a Base64 secret at [base64encode.org](https://www.base64encode.org)

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   The server starts at `http://localhost:8080`

---

## API Reference

### Auth

| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/api/users/register` | Public | Register as SEEKER or RECRUITER |
| POST | `/api/users/login` | Public | Login and receive JWT token |

**Register request:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "secret123",
  "role": "SEEKER"
}
```

**Login response:**
```json
{
  "jwtToken": "eyJhbGci...",
  "user": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "role": "SEEKER"
  }
}
```

---

### Jobs

| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/api/jobs/` | RECRUITER | Post a new job |
| GET | `/api/jobs/` | Authenticated | Get all job listings |
| GET | `/api/jobs/{jobId}/applications` | RECRUITER (owner) | Get all applications for a job |

**Post job request:**
```json
{
  "title": "Senior Java Developer",
  "requiredExp": "3-5 years",
  "description": "Join our backend team.",
  "isActive": true,
  "jobType": "FULL_TIME",
  "salary": "$80,000 - $100,000",
  "company": "Acme Corp",
  "location": "Bengaluru, India"
}
```

---

### Applications

| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/api/application/` | SEEKER | Apply to a job |
| GET | `/api/application/my` | SEEKER | View my applications |
| PUT | `/api/application/{id}/status` | RECRUITER | Update application status |

**Apply request:**
```json
{
  "jobId": 1,
  "resumeUrl": "https://example.com/resume.pdf"
}
```

**Update status request:**
```json
"SHORTLISTED"
```

---

## Authentication

All protected endpoints require a Bearer token in the Authorization header:

```
Authorization: Bearer <your_jwt_token>
```

---

## Roles & Permissions

| Action | SEEKER | RECRUITER |
|---|---|---|
| Register / Login | ✅ | ✅ |
| View job listings | ✅ | ✅ |
| Post a job | ❌ | ✅ |
| Apply to a job | ✅ | ❌ |
| View own applications | ✅ | ❌ |
| View job applications | ❌ | ✅ (own jobs only) |
| Update application status | ❌ | ✅ (own jobs only) |

---

## Project Structure

```
src/main/java/com/example/JobDock
├── config/
├── Controller/
├── dto/
├── Exceptions/
├── Model/
├── Repository/
└── Service/
   
```

---

## Application Status Flow

```
APPLIED → REVIEWING → SHORTLISTED → HIRED
                    ↘ REJECTED
```

---

## Upcoming

- [ ] Unit tests with JUnit + Mockito
- [ ] Docker + docker-compose setup
- [ ] Response DTOs for Application endpoints
- [ ] Microservices migration
