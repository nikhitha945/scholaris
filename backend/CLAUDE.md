# Scholaris Backend

Spring Boot REST API for the Scholaris school management system.

## Stack
- Spring Boot 4.0.6, Java 21, Maven
- PostgreSQL 16 (see root CLAUDE.md for connection details)
- `spring.jpa.hibernate.ddl-auto=update`

## Run / test
- Start the app: `mvn spring-boot:run` → http://localhost:8080
- Compile only: `mvn -q compile`

## Layout
- `controller/` — REST controllers (`AuthController`, `AdminController`, `GlobalExceptionHandler`)
- `service/` — business logic (`AuthService`, `AdminQueryService`, `CustomUserDetailsService`)
- `security/` — `JwtUtil`, `JwtAuthenticationFilter`
- `config/` — `SecurityConfig`, `WebConfig`, `JpaConfig`, `DataSeeder`
- `entity/` — JPA entities; `dto/` — request/response records
- `resources/static/admin.html` — admin portal UI

## Key features
- **Auth**: `POST /api/auth/login`, `GET /api/auth/me`. Public path: `/api/auth/**`.
- **Admin portal**: `GET /admin` — admin-only server-rendered SQL console.
  `POST /api/admin/query` runs raw SQL (full read/write), gated to `ROLE_ADMIN`.
  Guardrails: query timeout + max-row cap (`app.admin.query.*` in `application.yml`),
  every statement audit-logged as `[admin-sql] user=… sql=…`.
- **Audit timestamps**: all entities extend `entity/Auditable` (`@CreatedDate` / `@LastModifiedDate`
  → `created_at` / `modified_at`, both NOT NULL). Enabled by `config/JpaConfig` (`@EnableJpaAuditing`).

## Conventions
- DTOs are Java `record`s.
- All errors flow through `GlobalExceptionHandler` → `ApiError` (timestamp/status/error/message).
- New entities must `extend Auditable` to inherit `created_at`/`modified_at` automatically.

## Gotchas
- Adding a NOT NULL audit column to a table that already has rows fails under `ddl-auto=update`
  (Postgres rejects `ADD COLUMN ... NOT NULL` on non-empty tables). Migrate manually:
  add the column nullable → backfill → `ALTER COLUMN ... SET NOT NULL`.
- The admin SQL portal bypasses JPA, so raw SQL writes there do NOT trigger auditing.

## Before deploying (security)
- Rotate the `admin` password and set a real `JWT_SECRET` (both ship as well-known defaults in `application.yml`).
- The admin portal allows destructive SQL (`DROP`, unfiltered `DELETE`, etc.) by design — no undo.
