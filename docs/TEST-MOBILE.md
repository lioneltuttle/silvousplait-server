# Tester les écrans mobiles SVP Platform

## Prérequis

1. **Flutter** installé (`flutter doctor`)
2. **Émulateur Android** ou **appareil physique** connecté
3. **Backend Quarkus** lancé sur `http://localhost:8080`
4. **Docker** (Postgres + Redis) démarré pour le backend

---

## 1. Démarrer l'environnement

### Terminal 1 — Infra (Docker)
```powershell
# Démarrer Docker Desktop, puis :
docker compose -f infra/docker-compose.yml up -d
```

### Terminal 2 — Backend Quarkus
```powershell
# Vérifier JAVA_HOME (JDK 21+ requis)
$env:JAVA_HOME = "C:\Program Files\Java\jdk-25.0.2"

cd backend
./mvnw quarkus:dev
```
→ Backend disponible sur `http://localhost:8080`

### Terminal 3 — Web Angular (optionnel)
```powershell
cd web
npm install
npm start
```
→ Front web sur `http://localhost:4200`

---

## 2. Lancer les apps mobiles

### SVP Client (particuliers)

```powershell
cd mobile
flutter pub get
cd apps/svp_client
flutter run
```

**Écran principal** : recherche de prestation
- Champ **Type de prestation** (ex. plomberie, électricité)
- Champ **Adresse** (manuel)
- Bouton **Utiliser ma position** (mock pour l’instant)
- Bouton **Lancer la recherche** → envoie `POST /api/demands` au backend

**Scénario de test** :
1. Saisir un type (ex. "Plomberie")
2. Cliquer sur **Lancer la recherche**
3. Vérifier le SnackBar "Demande envoyée (id=…)"
4. La liste d’artisans se remplit progressivement (simulation 5/15/25 s)

**URL backend** : `http://10.0.2.2:8080` (émulateur Android) — `10.0.2.2` = localhost de la machine hôte.

---

### SVP Pro (artisans)

```powershell
cd mobile/apps/svp_pro
flutter run
```

**Écran principal** : statut Disponible / Indisponible
- Switch **Disponible** → ouvre une connexion WebSocket vers `ws://10.0.2.2:8080/ws/demand-dispatch`
- Quand une demande est créée côté client, elle est poussée en temps réel vers les artisans connectés
- **Dernière demande reçue** affichée en bas de l’écran

**Bouton "Mon compteur du mois"** : écran facturation (données mockées)

**Scénario de test** :
1. Lancer SVP Pro, passer le switch en **Disponible**
2. Lancer SVP Client sur un autre émulateur/device
3. Dans SVP Client : saisir une prestation et lancer la recherche
4. Vérifier dans SVP Pro que la **dernière demande reçue** se met à jour

---

## 3. Tester sur appareil physique

Sur un téléphone en USB, remplacer `10.0.2.2` par l’**IP de ton PC** sur le réseau local :

- **svp_client** : `lib/main.dart` → `baseUrl: 'http://192.168.x.x:8080'`
- **svp_pro** : `lib/main.dart` → `Uri.parse('ws://192.168.x.x:8080/ws/demand-dispatch')`

---

## 4. Résumé des écrans

| App        | Écran                    | Fonctionnalité                          |
|-----------|---------------------------|-----------------------------------------|
| SVP Client | Recherche                | Saisie prestation + adresse, envoi demande |
| SVP Client | Liste artisans           | Affichage progressif après recherche   |
| SVP Pro   | Disponibilité             | Switch Disponible + WebSocket           |
| SVP Pro   | Dernière demande         | Réception temps réel des demandes      |
| SVP Pro   | Mon compteur du mois     | Liste mises en relation + total mock   |
