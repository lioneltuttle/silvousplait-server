# SVP Platform — Instructions agent

## Projet
Plateforme mise en relation immédiate clients/artisans (modèle Uber).
Deux apps Flutter distinctes : SVP Client (particuliers) et SVP Pro (artisans).

## Stack OBLIGATOIRE — ne jamais dévier
- Backend : Quarkus (Java 21), natif GraalVM
  Extensions : RESTEasy Reactive, WebSockets Next, Hibernate ORM Panache,
  Quarkus Scheduler, SmallRye JWT
- Mobile : Flutter (Dart), monorepo
  Packages : geolocator, background_locator_2, flutter_map, firebase_messaging
- Web : Angular 17+ TypeScript strict
- BDD : PostgreSQL 16 + PostGIS, Redis 7
- Cartographie : OpenRouteService auto-hébergé (jamais Google Maps)
- Paiement : GoCardless (SEPA)

## Conventions
- Java : camelCase, packages com.svp.*
- Dart : snake_case fichiers, camelCase variables
- TypeScript : strict, pas de any
- Commits : Conventional Commits (feat:, fix:, chore:)
- Branches : main protégée, features sur feature/nom-court
- Toute entité JPA doit avoir sa migration Flyway correspondante
- Tout nouveau service doit avoir son test unitaire

## Architecture clé
- WebSocket Vert.x : canal "gps-update" (artisan→serveur),
  canal "demand-dispatch" (serveur→artisan)
- Matching : 1 requête ORS isochrone → ST_Within PostGIS → tri note/distance
- Facturation : notification push immédiate à chaque mise en relation
  + cron 1er du mois

## Interdictions strictes
- Ne jamais utiliser Google Maps ou toute API cartographique payante
- Ne jamais utiliser Spring Boot
- Ne jamais committer sur main directement
- Ne jamais créer d'entité JPA sans migration Flyway