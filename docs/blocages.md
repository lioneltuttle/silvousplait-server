## Blocages

### 2026-03-11 — Docker daemon non démarré

- **Symptôme**: `docker compose ... up -d` → `docker daemon is not running` (pipe `//./pipe/docker_engine` introuvable).
- **Impact**: impossible de valider le `docker-compose` Sprint 0.
- **Action**: démarrer Docker Desktop, puis relancer :

```bash
docker compose -f infra/docker-compose.yml up -d
docker compose -f infra/docker-compose.yml ps
```

### 2026-03-11 — GitHub CLI non installable (annulation install)

- **Symptôme**: `winget install GitHub.cli` se termine par `1602 (install cancelled)`.
- **Impact**: impossible de créer milestones et issues automatiquement via CLI.
- **Action**: installer `gh` (UAC à accepter) ou créer les issues via l’UI GitHub.

