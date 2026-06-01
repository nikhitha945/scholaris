import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="page">
      <header class="bar">
        <span class="logo">Scholaris</span>
        <button class="logout" (click)="logout()">Sign out</button>
      </header>
      <section class="hero">
        <p class="eyebrow">Signed in</p>
        <h1>Welcome, {{ user()?.fullName || user()?.username }}</h1>
        <p class="role">Role: {{ user()?.role }}</p>
        <p class="note">
          You've successfully authenticated. This is where the school
          management dashboard would live.
        </p>
      </section>
    </div>
  `,
  styles: [
    `
      .page {
        min-height: 100vh;
        background: var(--paper);
      }
      .bar {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 1.1rem 2rem;
        border-bottom: 1px solid var(--line);
        background: var(--paper-pure);
      }
      .logo {
        font-family: var(--font-display);
        font-weight: 600;
        font-size: 1.25rem;
        color: var(--ink);
      }
      .logout {
        border: 1.5px solid var(--line);
        background: transparent;
        color: var(--ink-soft);
        padding: 0.5rem 1rem;
        border-radius: 9px;
        font-size: 0.9rem;
      }
      .logout:hover {
        border-color: var(--moss);
        color: var(--ink);
      }
      .hero {
        max-width: 40rem;
        margin: 5rem auto 0;
        padding: 0 2rem;
      }
      .eyebrow {
        text-transform: uppercase;
        letter-spacing: 0.08em;
        font-size: 0.8rem;
        color: var(--accent-deep);
        margin: 0 0 0.5rem;
      }
      h1 {
        font-family: var(--font-display);
        font-weight: 600;
        font-size: 2.4rem;
        margin: 0 0 0.5rem;
        color: var(--ink);
      }
      .role {
        color: var(--moss);
        font-weight: 500;
        margin: 0 0 1.5rem;
      }
      .note {
        color: var(--muted);
        line-height: 1.6;
      }
    `,
  ],
})
export class DashboardComponent {
  private auth = inject(AuthService);
  private router = inject(Router);

  user = this.auth.currentUser;

  logout(): void {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}
