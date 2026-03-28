import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [ReactiveFormsModule],
  template: `
    <main class="shell">
      <section class="card">
        <h1>Onboarding financier artisan</h1>
        <p>Renseignez votre IBAN et acceptez le mandat SEPA pour activer votre compte.</p>

        <form [formGroup]="form" (ngSubmit)="onSubmit()" novalidate>
          <label class="field">
            <span>IBAN</span>
            <input
              type="text"
              formControlName="iban"
              placeholder="FR76 3000 6000 0112 3456 7890 189"
            />
          </label>
          <div class="error" *ngIf="form.get('iban')?.invalid && form.get('iban')?.touched">
            IBAN requis.
          </div>

          <label class="field checkbox">
            <input type="checkbox" formControlName="sepaConsent" />
            <span>
              J'accepte le mandat SEPA pour les prélèvements mensuels de mise en relation.
            </span>
          </label>
          <div class="error" *ngIf="form.get('sepaConsent')?.invalid && form.get('sepaConsent')?.touched">
            Vous devez accepter le mandat SEPA pour continuer.
          </div>

          <button type="submit" [disabled]="form.invalid">
            Valider mon onboarding financier (mock)
          </button>
        </form>

        <p class="hint">
          Cet écran sera relié à l'API backend d'onboarding financier (S3-3) pour bloquer le statut
          <strong>Disponible</strong> tant que les informations ne sont pas complètes.
        </p>
      </section>

      <section class="card">
        <h2>Back-office — factures &amp; litiges (aperçu)</h2>
        <p>Consultation des factures mensuelles et des litiges ouverts (maquette S3-4).</p>
        <ul class="bo-list">
          <li>Facture 2026-03 — 45,00 € HT — <span class="tag">Payée</span></li>
          <li>Litige #12 — contestation montant — <span class="tag tag-warn">En cours</span></li>
        </ul>
        <p class="hint">Branchement API facturation / litiges prévu au sprint suivant.</p>
      </section>
    </main>
  `,
  styles: [
    `
      .shell {
        min-height: 100vh;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: flex-start;
        padding: 2rem;
        gap: 1.5rem;
        background: #f3f4f6;
        font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI',
          sans-serif;
      }

      .card {
        max-width: 480px;
        width: 100%;
        background: white;
        border-radius: 12px;
        padding: 1.5rem 2rem;
        box-shadow: 0 10px 30px rgba(15, 23, 42, 0.08);
      }

      h1 {
        margin: 0 0 0.75rem;
        font-size: 1.5rem;
      }

      p {
        margin: 0 0 1rem;
      }

      form {
        display: flex;
        flex-direction: column;
        gap: 0.75rem;
        margin-top: 1rem;
      }

      .field {
        display: flex;
        flex-direction: column;
        gap: 0.25rem;
      }

      .field span {
        font-size: 0.875rem;
        font-weight: 500;
      }

      input[type='text'] {
        border-radius: 8px;
        border: 1px solid #d1d5db;
        padding: 0.5rem 0.75rem;
        font-size: 0.95rem;
      }

      .checkbox {
        flex-direction: row;
        align-items: flex-start;
        gap: 0.5rem;
      }

      .checkbox span {
        font-weight: 400;
      }

      button[type='submit'] {
        margin-top: 0.5rem;
        border-radius: 999px;
        border: none;
        padding: 0.6rem 1.2rem;
        background: #2563eb;
        color: white;
        font-weight: 600;
        cursor: pointer;
      }

      button[disabled] {
        opacity: 0.5;
        cursor: not-allowed;
      }

      .error {
        color: #b91c1c;
        font-size: 0.8rem;
      }

      .hint {
        margin-top: 1.25rem;
        font-size: 0.85rem;
        color: #4b5563;
      }

      .bo-list {
        margin: 0.5rem 0 0;
        padding-left: 1.25rem;
      }

      .tag {
        font-size: 0.75rem;
        padding: 0.1rem 0.45rem;
        border-radius: 999px;
        background: #dcfce7;
        color: #166534;
      }

      .tag-warn {
        background: #fef3c7;
        color: #92400e;
      }
    `,
  ],
})
export class AppComponent {
  form: FormGroup;

  constructor(private readonly formBuilder: FormBuilder) {
    this.form = this.formBuilder.group({
      iban: ['', [Validators.required]],
      sepaConsent: [false, [Validators.requiredTrue]],
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    // Sprint 3 : envoi mock, le branchement backend viendra ensuite.
    alert('Onboarding financier enregistré (mock).');
  }
}

