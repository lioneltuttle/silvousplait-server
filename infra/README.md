## Infra (développement local)

### Prérequis

- Docker Desktop avec `docker compose`

### Démarrer les services de base (recommandé)

PostgreSQL 16 + PostGIS et Redis 7 :

```bash
docker compose -f infra/docker-compose.yml up -d
```

### Démarrer aussi OpenRouteService (optionnel)

ORS est gourmand (CPU/RAM, données OSM). Il est donc dans un *profile* Compose dédié.

```bash
docker compose -f infra/docker-compose.yml --profile ors up -d
```

### Ports

- Postgres: `localhost:5432` (db: `svp`, user: `svp`, password: `svp`)
- Redis: `localhost:6379`
- ORS (profile `ors`): `localhost:8082`

