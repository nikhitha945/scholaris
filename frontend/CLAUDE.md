# Scholaris Frontend

Angular SPA for the Scholaris school management system.

## Stack
- Angular 21.2.13, TypeScript 5.9, standalone components (no NgModules)
- RxJS 7.8, Zone.js 0.15

## Run / build
- Dev server: `npm start` → http://localhost:4200
- Production build: `npm run build`
- Watch mode: `npm run watch`
- Tests: `npm test`

## Layout
- `pages/` — route-level components (`login/`, `dashboard/`)
- `services/` — `auth.service.ts` (login, token management, user state)
- `guards/` — `auth.guard.ts` (route protection)
- `interceptors/` — `auth.interceptor.ts` (attaches JWT, handles 401 → redirect to /login)
- `environments/` — `environment.ts` (`apiBaseUrl: http://localhost:8080/api`)

## Patterns
- All components are **standalone** with explicit `imports` arrays.
- Dependency injection via `inject()`, not constructor injection.
- State management uses **Angular signals** (`signal()`, `computed()`).
- Forms use **ReactiveFormsModule** with `FormBuilder`.
- Routes use **lazy loading** via `loadComponent()` in `app.routes.ts`.
- Functional guards and interceptors (not class-based).

## API integration
- Base URL set in `environments/environment.ts`.
- `auth.interceptor.ts` attaches `Authorization: Bearer {token}` to all requests.
- On 401 response: clears storage, redirects to `/login`.
- Token stored in `localStorage` as `scholaris_token`; user data as `scholaris_user`.

## Design
- CSS custom properties for theming (ink/paper/moss/accent/danger palette).
- Fonts: Fraunces (display), Outfit (body) via Google Fonts.
- Responsive: mobile breakpoint at 860px.
