# Plan de délégation — Issues ouvertes SVP Platform

**Date** : 2026-03-18  
**Rôle** : Chef de projet — Orchestrateur  
**Objectif** : Assigner chaque issue ouverte à l’agent compétent et définir l’ordre d’exécution.

---

## 1. Vue d’ensemble

| Sprint | Issues ouvertes | Priorité | Statut |
|--------|-----------------|----------|--------|
| Sprint 0 | #9, #10, #11, #12, #13, #14 | **P0** | À valider / compléter |
| Sprint 1 | #15, #16, #17, #18 | P1 | En attente Sprint 0 |
| Sprint 2 | #19, #20, #21 | P2 | En attente Sprint 1 |
| Sprint 3 | #22, #23, #24, #25 | P3 | En attente Sprint 2 |
| Sprint 4 | #26, #27, #28, #29 | P4 | En attente Sprint 3 |
| Legacy (2020) | #1, #2, #3, #4, #5, #6, #7 | À trier | À évaluer (fermer ou migrer) |

---

## 2. Sprint 0 — Délégation immédiate

Le Sprint 0 doit être **entièrement validé** avant de passer au Sprint 1.

### #9 — S0-1 Infra: Docker Compose (Postgres+PostGIS, Redis, ORS en profile)

| Champ | Valeur |
|-------|--------|
| **Agent** | @infra.mdc |
| **Contexte** | `infra/docker-compose.yml` et `infra/README.md` existent déjà. |
| **Tâche** | Vérifier que `docker compose -f infra/docker-compose.yml up -d` et `--profile ors` fonctionnent. Mettre à jour `infra/README.md` si besoin. |
| **Critère de validation** | `docker compose up -d` OK, `--profile ors` OK, doc à jour. |
| **Branche** | `feature/infra-docker-s0` |
| **Action post-livraison** | Fermer #9 si validé. |

---

### #10 — S0-2 Backend: Socle Quarkus 21 + extensions

| Champ | Valeur |
|-------|--------|
| **Agent** | @dev-java-api.mdc |
| **Contexte** | Backend Quarkus existe avec RESTEasy Reactive, Panache, WebSockets Next, Scheduler, SmallRye JWT, Postgres, Redis. |
| **Tâche** | Ajouter `quarkus-smallrye-health` pour l’endpoint `/q/health`. Vérifier build OK, healthcheck accessible, connexion Postgres/Redis via variables d’environnement. |
| **Critère de validation** | Build OK, `GET /q/health` retourne 200, config Postgres/Redis via env. |
| **Branche** | `feature/backend-socle-s0` |
| **Action post-livraison** | Fermer #10 si validé. |

---

### #11 — S0-3 Backend: Conventions packages com.svp.* + structure

| Champ | Valeur |
|-------|--------|
| **Agent** | @dev-java-api.mdc |
| **Contexte** | Le backend a déjà `api`, `websocket`, `domain`, `billing`, `notification`. Cohabitation avec code legacy `src/main/java/com/svp`. |
| **Tâche** | Vérifier que le code Quarkus dans `backend/` respecte la structure : `api`, `websocket`, `domain`, `infra/persistence`. Documenter ou refactorer si nécessaire. |
| **Critère de validation** | Code organisé par domaines, conventions de nommage respectées. |
| **Branche** | `feature/backend-structure-s0` |
| **Action post-livraison** | Fermer #11 si validé. |

---

### #12 — S0-5 Mobile: Monorepo Flutter + SVP Client & SVP Pro

| Champ | Valeur |
|-------|--------|
| **Agent** | @dev-mobile-client.mdc + @dev-mobile-artisan.mdc |
| **Contexte** | `mobile/apps/svp_client` et `mobile/apps/svp_pro` existent avec geolocator, background_locator_2, flutter_map, firebase_messaging. |
| **Tâche** | Vérifier `flutter pub get` au niveau `mobile/`, lancement des 2 apps, écran placeholder présent. |
| **Critère de validation** | `flutter pub get` OK, `svp_client` et `svp_pro` démarrent, deps imposées présentes. |
| **Branche** | `feature/mobile-socle-s0` |
| **Action post-livraison** | Fermer #12 si validé. |

---

### #13 — S0-4 Web: Angular 17+ strict + démarrage

| Champ | Valeur |
|-------|--------|
| **Agent** | (Pas d’agent web dédié dans les rules — à traiter manuellement ou via @ux-ui pour wireframes) |
| **Contexte** | `web/` existe avec Angular 17, TypeScript strict. |
| **Tâche** | Vérifier `npm install` puis `npm run start` OK, page placeholder (login ou landing pro). |
| **Critère de validation** | Build OK, TypeScript strict sans `any`, page placeholder. |
| **Branche** | `feature/web-socle-s0` |
| **Action post-livraison** | Fermer #13 si validé. |

---

### #14 — S0-6 CI: pipeline minimal (lint/test/build)

| Champ | Valeur |
|-------|--------|
| **Agent** | @infra.mdc |
| **Contexte** | `.github/workflows/ci.yml` existe mais est vide (commentaire uniquement). |
| **Tâche** | Implémenter le pipeline : build backend Quarkus, lint/test web Angular, analyse Flutter. Exécution sur PR. |
| **Critère de validation** | GitHub Actions : build backend + lint/test web + analyse Flutter, exécution sur PR. |
| **Branche** | `feature/ci-pipeline-s0` |
| **Action post-livraison** | Fermer #14 si validé. |

---

## 3. Sprint 1 — Délégation (après Sprint 0 validé)

| Issue | Titre | Agent | Branche |
|-------|-------|-------|---------|
| #15 | S1-2 Backend : création de demande et dispatch initial | @dev-java-api.mdc | `feature/backend-demande-s1` |
| #16 | S1-1 Mobile client : recherche + envoi de demande | @dev-mobile-client.mdc | `feature/mobile-client-recherche-s1` |
| #17 | S1-3 Mobile pro : statut disponible + réception demande | @dev-mobile-artisan.mdc | `feature/mobile-pro-statut-s1` |
| #18 | S1-4 Backend : matching + classement artisans | @dev-java-api.mdc | `feature/backend-matching-s1` |

**Ordre recommandé** : #15 (backend) → #16, #17 (mobile en parallèle) → #18 (backend matching).

---

## 4. Sprint 2 — Délégation

| Issue | Titre | Agent | Branche |
|-------|-------|-------|---------|
| #19 | S2-1 Backend : WebSocket demand-dispatch + gps-update | @dev-java-websocket.mdc | `feature/websocket-s2` |
| #20 | S2-2 Mobile pro : client WebSocket + réception demande temps réel | @dev-mobile-artisan.mdc | `feature/mobile-pro-websocket-s2` |
| #21 | S2-4 Backend : notifications push FCM | @dev-java-api.mdc | `feature/backend-fcm-s2` |

**Ordre** : #19 (WebSocket backend) → #20 (mobile pro) et #21 (FCM) en parallèle.

---

## 5. Sprint 3 — Délégation

| Issue | Titre | Agent | Branche |
|-------|-------|-------|---------|
| #22 | S3-1 Backend : modèle de facturation mensuelle | @dev-java-api.mdc | `feature/backend-facturation-s3` |
| #23 | S3-3 Web pro : onboarding financier (IBAN + mandat SEPA) | (Web) | `feature/web-onboarding-s3` |
| #24 | S3-2 Mobile pro : écran 'Mon compteur du mois' | @dev-mobile-artisan.mdc | `feature/mobile-pro-compteur-s3` |
| #25 | S3-4 Back-office : consultation factures & litiges | (Web) | `feature/web-backoffice-s3` |

---

## 6. Sprint 4 — Délégation

| Issue | Titre | Agent | Branche |
|-------|-------|-------|---------|
| #26 | S4-1 Backend : sécurité JWT + rôles | @dev-java-api.mdc | `feature/backend-jwt-s4` |
| #27 | S4-2 Backend : tests automatisés matching / WebSocket / facturation | @testeur-back.mdc | `feature/tests-backend-s4` |
| #28 | S4-4 CI : pipeline complet (backend/web/mobile) | @infra.mdc | `feature/ci-complet-s4` |
| #29 | S4-3 Web / mobile : durcir les flux d’auth | @dev-mobile-client.mdc + @dev-mobile-artisan.mdc + (Web) | `feature/auth-durcie-s4` |

---

## 7. Issues legacy (2020)

| Issue | Titre | Action recommandée |
|-------|-------|--------------------|
| #1 | Enable Push Notification testing | Évaluer : fermer si couvert par S2/S4, ou migrer vers une issue SVP. |
| #2 | Login Page for PRO | Idem. |
| #3 | Information Page for PRO | Idem. |
| #4 | Add test for backend | Idem — peut être couvert par #27. |
| #5 | Add Tests for Front End | Idem. |
| #6 | PRO - bills Page | Idem — peut être couvert par S3. |
| #7 | PRO - List of missing requests page | Idem. |

**Délégation** : @business-analyst.mdc pour trier et décider (fermer / migrer / garder).

---

## 8. Instructions de lancement

Pour lancer le Sprint 0 :

1. **Déléguer en parallèle** (si possible) :
   - @infra.mdc → #9, #14
   - @dev-java-api.mdc → #10, #11
   - (Web) → #13
   - @dev-mobile-client.mdc ou @dev-mobile-artisan.mdc → #12

2. **Format de délégation** (exemple pour #9) :

```
@infra.mdc
Contexte : infra/docker-compose.yml et infra/README.md existent.
Issue GitHub : #9
Ta tâche : Vérifier que docker compose -f infra/docker-compose.yml up -d et --profile ors fonctionnent. Mettre à jour infra/README.md si besoin.
Critère de validation : docker compose up -d OK, --profile ors OK, doc à jour.
Quand tu as fini : commit sur feature/infra-docker-s0 et dis-moi "DONE #9"
```

3. **Après chaque livrable** : vérifier critères → fermer l’issue GitHub → passer à la suivante.

---

## 9. Prochaine action prioritaire

**État CP — 2026-03-24** : Sprint 0 toujours la priorité P0. Aucune issue S0 ne doit être considérée comme « Done » tant que les critères du tableau §2 ne sont pas vérifiés sur une branche `feature/*` et validés en PR (cf. @qa-review.mdc).

**Immédiat** :

1. **Débloquer l’environnement** (humain) : Docker Desktop pour #9 ; `gh` ou UI GitHub pour suivi milestones/issues (cf. `docs/blocages.md`).
2. **En parallèle (Sprint 0)** — relancer ou poursuivre les livrables :
   - @infra.mdc → #9, #14
   - @dev-java-api.mdc → #10, #11
   - Web / dev générique → #13 (pas d’agent web dédié dans les rules)
   - @dev-mobile-client.mdc ou @dev-mobile-artisan.mdc → #12
3. **Legacy** : @business-analyst.mdc → tri #1–#7 (fermer / migrer / garder), sans bloquer le flux S0.

**Garde-fou** : Ne pas démarrer Sprint 1 (#15–#18) tant que les 6 issues S0 ne sont pas validées.

**Blocage connu** : Docker Desktop pour #9 ; `JAVA_HOME` / terminal pour backend local (cf. `docs/blocages.md`).
