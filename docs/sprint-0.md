## Sprint 0 — Socle technique (MVP)

### Objectif

Mettre en place un socle exécutable en local (Docker + projets backend/web/mobile), un backlog initial (user stories) et une base CI minimale.

### Livrables Sprint 0

- `infra/docker-compose.yml` démarre **PostgreSQL 16 + PostGIS** et **Redis 7** (ORS optionnel).
- Backend Quarkus (Java 21) démarre, se connecte à Postgres/Redis, expose un healthcheck.
- Web Angular (TS strict) démarre (écran placeholder).
- Monorepo Flutter : les 2 apps démarrent (écran placeholder).
- Backlog : user stories prêtes à être créées en issues GitHub.

### Backlog (issues à créer sur GitHub)

#### S0-1 — Infra: Docker Compose (Postgres+PostGIS, Redis, ORS en profile)

- **Description**: Environnement local reproductible.
- **Acceptance criteria**
  - `docker compose -f infra/docker-compose.yml up -d` démarre Postgres + Redis
  - ORS est démarrable via `--profile ors`
  - Documentation `infra/README.md` à jour

#### S0-2 — Backend: Socle Quarkus Java 21 + extensions imposées

- **Description**: Démarrer Quarkus avec RESTEasy Reactive + Panache + Scheduler + WebSockets Next + SmallRye JWT.
- **Acceptance criteria**
  - Build OK
  - Endpoint de healthcheck accessible
  - Connexion Postgres et Redis configurée via variables d’environnement

#### S0-3 — Backend: Convention packages `com.svp.*` + structure modules

- **Acceptance criteria**
  - Code organisé par domaines (api, websocket, domain, infra/persistence)
  - Conventions de nommage respectées

#### S0-4 — Web: Angular 17+ strict + démarrage

- **Acceptance criteria**
  - `npm install` puis `npm run start` OK
  - TypeScript strict sans `any`
  - Page placeholder (login ou landing pro)

#### S0-5 — Mobile: Monorepo Flutter + apps SVP Client & SVP Pro démarrables

- **Acceptance criteria**
  - `flutter pub get` au niveau `mobile/` OK
  - Lancement de `svp_client` et `svp_pro` OK (écran placeholder)
  - Dépendances imposées présentes (geolocator, background_locator_2, flutter_map, firebase_messaging)

#### S0-6 — CI: pipeline minimal (lint/test/build)

- **Acceptance criteria**
  - GitHub Actions: build backend + lint/test web + analyse Flutter
  - Exécution sur PR

### Blocages connus (à traiter)

- Docker Desktop doit être démarré pour valider `docker compose up`.
- GitHub CLI (`gh`) nécessaire pour créer automatiquement milestones/issues (sinon création manuelle via UI).

