# LpTasks API

REST API for task management with JWT authentication, built with **Kotlin + Spring Boot**.

---

## 🛠️ Technologies

- **Kotlin** + **Spring Boot 3.3**
- **PostgreSQL** — data persistence
- **Redis** — cache
- **Spring Security** + **JWT (Auth0)** — authentication and authorization
- **Docker Compose** — local infrastructure
- **Maven** — dependency management

---

## 📋 Prerequisites

- Java 21+
- Maven
- Docker and Docker Compose

---

## 🚀 How to run

**1. Bring up the infrastructure (database and cache):**

```bash
docker-compose up -d
```

**2. Run the application:**

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

---

## 🔐 Authentication

The API uses **JWT Bearer Token**. To access protected endpoints, include the header:

```
Authorization: Bearer <your_token>
```

### Authentication endpoints

| Method | Endpoint | Access | Description |
|--------|----------|--------|-----------|
| `POST` | `/app/auth/register` | Public | Register a new user |
| `POST` | `/app/auth/login` | Public | Log in and obtain a token |

#### Register user
```json
POST /app/auth/register
{
  "email": "user@email.com",
  "password": "password123",
  "isAdmin": false
}
```

#### Login
```json
POST /app/auth/login
{
  "email": "user@email.com",
  "password": "password123"
}
```
**Response:**
```json
{
  "body": { "token": "eyJhbGci..." },
  "message": "Successfully loged in",
  "statusCode": 200
}
```

---

## ✅ Task Endpoints

All endpoints below require authentication.

| Method | Endpoint | Description |
|--------|----------|-----------|
| `GET` | `/app/tasks` | List all tasks |
| `GET` | `/app/tasks/id?id={id}` | Find task by ID |
| `GET` | `/app/tasks/title?taskTitle={title}` | Find tasks by title |
| `GET` | `/app/tasks/category?category={category}` | Filter by category |
| `GET` | `/app/tasks/sortByPriority?sortOrder={asc\|desc}` | Sort by priority |
| `POST` | `/app/tasks` | Create one or more tasks |
| `PUT` | `/app/tasks/{id}` | Update a task |
| `DELETE` | `/app/tasks/{id}` | Delete a task |

### Request body (create/update)

```json
[
  {
    "title": "Study Kotlin",
    "description": "Review coroutines and flows",
    "category": "STUDY",
    "priority": "HIGH"
  }
]
```

### Available categories

| Value |
|-------|
| `WORK` |
| `STUDY` |
| `HOBBY` |
| `OTHER` |

### Available priorities

| Value |
|-------|
| `LOW` |
| `MEDIUM` |
| `HIGH` |

---

## 📦 Project structure

```
src/main/kotlin/com/lucas/lptasks/
├── controller/       # Entry layer (REST)
├── service/          # Business logic
├── repository/       # Database access
├── model/            # JPA entities
├── dto/              # Data transfer objects
├── security/         # JWT filters and Spring Security configuration
├── exception/        # Custom exceptions and global handler
├── enum/             # Category and priority enums
└── utils/            # Utilities (validation, ApiResponse)
```

---

## 🗄️ Environment variables / Configuration

Settings live in `src/main/resources/application.yml`. The default values are:

| Property | Default |
|-------------|--------|
| `server.port` | `8080` |
| `datasource.url` | `jdbc:postgresql://127.0.0.1:5432/LpTasks` |
| `datasource.username` | `lukas` |
| `datasource.password` | `mistery123` |
| `cache.type` | `redis` |
| `token.secret` | `encrypted123` |

> ⚠️ In production, replace `token.secret` with a secure value and externalize credentials via environment variables.

---

## 🐳 Docker Compose

The `docker-compose.yml` file brings up two services:

- **PostgreSQL 13** on port `5432` — automatically initializes with the `initialize.sql` script
- **Redis 7.4** on port `6379`

```bash
# Bring up
docker-compose up -d

# Tear down
docker-compose down
```

---

## 🗺️ Roadmap

### 🔄 In progress

- [ ] **Unit and integration tests** — coverage of services, controllers, and security filters with JUnit 5 and MockK

---

### 🚀 Upcoming implementations

#### Features
- [ ] **Pagination** on listing endpoints (`GET /app/tasks`) to support large volumes of data
- [ ] **Task status** — add a `status` field with values like `TODO`, `IN_PROGRESS`, `DONE`
- [ ] **Due date** (`dueDate`) per task with filtering by deadline
- [ ] **Combined search endpoint** — filter tasks by multiple criteria at once (category + priority + status)
- [ ] **Soft delete** — replace physical deletion with logical deletion via a `deletedAt` field
- [ ] **Task assignment** — link tasks to specific users
- [ ] **Task listing per user** — each user sees only their own tasks

#### Security and Auth
- [ ] **Refresh token** — issue and rotate refresh tokens to avoid frequent re-login
- [ ] **Token revocation** — invalidate active tokens via a blacklist in Redis
- [ ] **Rate limiting** — limit requests per IP/user to prevent abuse

#### Cache
- [ ] **Apply `@Cacheable`** on frequently read endpoints (`getAllTasks`, `getTasksByCategory`)
- [ ] **Automatic cache invalidation** when creating, updating, or deleting tasks with `@CacheEvict`

#### Infrastructure
- [ ] **Migrations with Flyway** — replace `ddl-auto: update` with schema versioning
- [ ] **Dockerize the application** — add the API to `docker-compose.yml` to run everything with a single command
- [ ] **Environment profiles** — separate configurations for `dev`, `test`, and `prod`
- [ ] **Structured logging** — add logs with request ID correlation for traceability

#### Documentation
- [ ] **Swagger / OpenAPI** — interactive endpoint documentation via SpringDoc

---

## 📐 Response pattern

All endpoints return the same envelope:

```json
{
  "body": { },
  "message": "Descriptive message",
  "statusCode": 200
}
```
