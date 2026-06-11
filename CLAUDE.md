# Scholaris

School Management System — monorepo with a Spring Boot backend and Angular frontend.

## Layout
- `backend/` — REST API (Spring Boot, Java 21, Maven)
- `frontend/` — SPA (Angular 21, TypeScript)

## Database
- PostgreSQL 16 in Docker: container `scholaris_db`, `localhost:5432`
- DB / user / password: `scholaris_db` / `scholaris` / `scholaris`
- DB shell: `docker exec -e PGPASSWORD=scholaris scholaris_db psql -U scholaris -d scholaris_db`

## Auth
- Stateless JWT (jjwt 0.12.6) + Spring Security
- Roles: `ADMIN`, `TEACHER`, `STUDENT` (Spring authorities are `ROLE_<NAME>`)
- Seeded demo users (created by `DataSeeder` when the users table is empty):
  `admin/admin123`, `teacher/teacher123`

## Ports
- Backend: `http://localhost:8080`
- Frontend: `http://localhost:4200` (proxies API calls to 8080)
