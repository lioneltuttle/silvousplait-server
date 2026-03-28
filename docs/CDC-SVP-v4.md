# Cahier des Charges Technique — SVP Platform

*La mise en relation immédiate avec les artisans*

| Champ | Valeur |
|---|---|
| Référence | SVP-CDC-MVP-v1 |
| Version | 1.1 — Enrichie |
| Statut | En cours de validation |
| Périmètre | MVP — Produit Minimum Viable |
| Confidentialité | Document confidentiel — usage interne |

# 1. Contexte

La société S'IL VOUS PLAÎT envisage de développer une application
mobile de mise en relation entre professionnels artisans et
particuliers, dans le cadre d'une intervention immédiate pour des
services du quotidien.

Le modèle s'inspire du fonctionnement des plateformes de mise en
relation à la demande (type Uber), appliqué au secteur de l'artisanat
et des services à domicile. L'objectif est de permettre à un client de
trouver un artisan disponible et proche en moins d'une minute, et à
l'artisan de recevoir des missions en temps réel dans sa zone
d'activité.

Ce document présente le contenu et les modalités de mise en œuvre de la
première version de l'application (MVP --- Minimum Viable Product). Les
fonctionnalités décrites constituent l'ensemble des composantes de
cette version initiale.

## 1.1 Use case principal --- Le parcours de mise en relation

+-----------------------------------------------------------------------+
| **Résumé du parcours**                                                |
|                                                                       |
| Un client identifie un besoin de prestation (ex : plombier,           |
| électricien). Il lance une recherche depuis l'application mobile. Les |
| artisans disponibles dans un rayon de 30 minutes en voiture reçoivent |
| une notification et peuvent se proposer. Le client reçoit une liste   |
| d'artisans répondants avec leur tarif horaire et leur note. Il        |
| choisit un artisan, l'appelle ou lui indique l'adresse. Dès cette     |
| mise en relation, l'artisan reçoit une notification push et un email  |
| lui indiquant qu'une ligne vient d'être ajoutée à sa facturation du   |
| mois, qu'il ait effectué la prestation ou non. Le prélèvement est     |
| effectué en début du mois suivant par SEPA.                           |
+=======================================================================+
+-----------------------------------------------------------------------+

  -------------------------------------------------------------------------
  **1**   **Saisie de la        Le client ouvre l'application, saisit le
          demande**             type de prestation recherchée (ex : «
                                Plombier », « Fuite chauffe-eau ») et
                                confirme sa localisation.
  ------- --------------------- -------------------------------------------
  **2**   **Diffusion aux       Le système identifie tous les artisans dont
          artisans**            le type d'activité correspond et dont la
                                localisation GPS est à moins de 30 minutes
                                de trajet en voiture du client. Une
                                notification push est envoyée
                                instantanément à chacun d'eux.

  **3**   **Réponse des         Chaque artisan notifié peut consulter la
          artisans**            demande (prénom du client, adresse, type de
                                prestation) et accepter ou ignorer. La
                                réponse est comptabilisée dans les
                                résultats envoyés au client.

  **4**   **Résultats côté      Au bout de 60 secondes, ou dès que 5
          client**              artisans ont répondu positivement, le
                                client reçoit une liste ordonnée affichant
                                : nom, photo, note (étoiles), tarif horaire
                                et distance estimée.

  **5**   **Sélection et        Le client sélectionne un artisan. Une
          contact**             modale lui propose deux actions : appeler
                                directement (ouverture de l'app téléphone
                                avec le numéro pré-rempli), ou transmettre
                                l'adresse d'intervention à l'artisan.

  **6**   **Facturation de la   Notification immédiate à l'artisan (push +
          mise en relation**    email) : mise en relation enregistrée et
                                ajoutée à sa facturation du mois.
                                Prélèvement SEPA en début du mois suivant.
  -------------------------------------------------------------------------

# 2. Technologies et environnement

## 2.1 Architecture générale

L'architecture repose sur un backend Quarkus (Java) fonctionnant en mode
"always-on" sur VPS, exposant une API REST pour les opérations
classiques et un serveur WebSocket natif Vert.x pour toutes les
communications temps réel. Ce modèle réactif non-bloquant permet de
gérer de très nombreuses connexions WebSocket simultanées (artisans
connectés en permanence) avec un usage mémoire minimal --- avantage
décisif par rapport à un serveur thread-per-request classique.

Quarkus est compilé en binaire natif via GraalVM, ce qui réduit
l'empreinte mémoire à \~50-100 MB au repos (contre \~300-500 MB pour
Spring Boot classique). Cette compacité permet de faire cohabiter sur un
même VPS OVH à coût modéré : le backend Quarkus, PostgreSQL + PostGIS,
Redis et l'instance OpenRouteService.

## 2.2 Applicatif

  -----------------------------------------------------------------------
  **Couche**            **Technologies retenues**
  --------------------- -------------------------------------------------
  **Application         Flutter (Dart) --- iOS & Android, codebase
  mobile**              unique, deux apps distinctes (SVP Client / SVP
                        Pro). Plugins géolocalisation : geolocator +
                        background_locator_2 (localisation arrière-plan
                        natif iOS/Android)

  **Front-end web**     Angular (TypeScript) --- espace web artisan
                        (profil, compteur facturation, litiges) +
                        back-office administration. Architecture
                        opinionated adaptée à des interfaces riches en
                        formulaires et tableaux de données.

  **Back-end**          Quarkus (Java) --- compilé en natif GraalVM.
                        Extensions : RESTEasy Reactive (API REST),
                        Quarkus WebSockets (Vert.x, connexions
                        persistantes artisans), Hibernate ORM Panache
                        (accès BDD), Quarkus Scheduler (cron
                        facturation). Démarrage natif \~30ms, mémoire
                        \~50-100 MB.

  **Base de données**   PostgreSQL 16 + extension PostGIS --- requêtes
                        spatiales natives (ST_Within, ST_DWithin) pour le
                        filtrage géographique des artisans dans la zone
                        isochrone. Redis pour le cache des positions GPS
                        (TTL 60s) et la gestion des sessions WebSocket.

  **Temps réel**        WebSocket natif Quarkus / Vert.x --- connexions
                        persistantes entre SVP Pro (artisans) et le
                        serveur. Dispatch des demandes client en push,
                        réception des positions GPS toutes les 30s.
                        Non-bloquant : un seul thread gère des centaines
                        de connexions simultanées.

  **Notifications       Firebase Cloud Messaging (FCM) pour Android et
  push**                iOS

  **Géolocalisation &   OpenRouteService (ORS) auto-hébergé sur VPS ---
  routing**             données OpenStreetMap, zéro coût à la requête.
                        Stratégie isochrone : une requête ORS calcule le
                        polygone "zone atteignable en 30 min" depuis le
                        client, puis PostGIS filtre les artisans dans ce
                        polygone (ST_Within). Rendu cartographique côté
                        Flutter via flutter_map + tuiles OpenStreetMap
                        (Leaflet).

  **Facturation**       Stripe Billing ou équivalent --- génération de
                        factures mensuelles automatisées

  **Stockage médias**   AWS S3 ou OVH Object Storage (photos de profil,
                        documents)

  **Envoi d'emails     SendGrid, Mailjet ou Postmark
  transactionnels**     
  -----------------------------------------------------------------------

## 2.3 Détail de l'architecture technique

### 2.3.1 Quarkus --- choix et extensions

Quarkus est un framework Java conçu nativement pour la compilation
GraalVM (binaire natif) et le modèle réactif (Vert.x). Il est privilégié
ici pour trois raisons : faible empreinte mémoire permettant de
regrouper tous les services sur un seul VPS à coût réduit ; moteur
Vert.x intégré nativement adapté aux connexions WebSocket persistantes
des artisans ; déploiement rapide et redémarrage \< 50ms sans
interruption de service. Les extensions utilisées sont : RESTEasy
Reactive pour l'API REST, Quarkus WebSockets Next pour les canaux temps
réel, Hibernate ORM Panache pour l'accès PostgreSQL, Quarkus Scheduler
pour les crons de facturation et relance, et SmallRye JWT pour
l'authentification.

### 2.3.2 Stratégie isochrone ORS + PostGIS

Plutôt que d'appeler une API de calcul de temps de trajet pour chaque
artisan disponible (coût exponentiel), le système utilise la stratégie
suivante : une unique requête à l'instance ORS locale calcule le
polygone isochrone "zone atteignable en 30 minutes en voiture depuis la
position du client". Ce polygone GeoJSON est ensuite utilisé dans une
requête PostGIS (ST_Within) pour extraire en base tous les artisans dont
la position courante se trouve à l'intérieur. Le résultat est trié par
note puis distance. Cette approche remplace N appels API par une requête
locale + une requête SQL, avec un coût infrastructure nul au-delà du
VPS.

### 2.3.3 Flutter --- architecture des deux applications

SVP Client et SVP Pro partagent un monorepo Flutter avec une couche
métier commune (modèles, appels API, gestion WebSocket) et des couches
UI/navigation distinctes. SVP Pro intègre les packages geolocator et
background_locator_2 pour la localisation en arrière-plan. La
cartographie est rendue via flutter_map avec des tuiles OpenStreetMap
(Leaflet), sans dépendance à Google Maps SDK. Les notifications push FCM
sont gérées via firebase_messaging, compatible Flutter iOS et Android.

## 2.4 Webdesign et éléments graphiques

L'application web est conçue « mobile-first ». L'expérience
utilisateur (UX) répond à toutes les règles ergonomiques de base et aux
tendances d'UX au moment de la réalisation. L'expérience utilisateur
offre un maximum d'accessibilité afin de garantir un usage possible par
le plus grand nombre de personnes.

## 2.4 Compatibilité web

L'application web et le back-office seront garantis compatibles avec
les dernières versions des navigateurs suivants : Google Chrome, Mozilla
Firefox, Microsoft Edge, Safari. Le support d'Internet Explorer n'est
plus requis (fin de vie officielle Microsoft).

## 2.5 Distribution mobile

Le projet comporte deux applications mobiles distinctes, distribuées
chacune sur l'App Store (Apple) et le Google Play Store. Elles cibleront
Android 13 et iOS 17 minimum.

**Application mobile client (SVP Client) :** destinée aux particuliers.
Elle utilise la localisation GPS uniquement lors d'une recherche active
(permission "en cours d'utilisation").

**Application mobile artisan (SVP Pro) :** destinée aux artisans
professionnels. Elle requiert la permission de localisation en
arrière-plan permanent ("always on") pour permettre le calcul de
proximité en temps réel, même lorsque l'application est fermée. Cette
contrainte technique justifie l'existence d'une application séparée,
conformément aux exigences des guidelines Apple App Store et Google Play
Store.

*Note technique : les deux applications partagent la même base de code
React Native (monorepo) et le même back-end API. Seuls les écrans, les
permissions et les flux de navigation diffèrent. Cela minimise le coût
de développement et de maintenance.*

# 3. Partage de localisation en temps réel

Le partage de localisation est un composant central du système. Il
conditionne le calcul de la zone de dispatch, l'estimation du temps de
trajet, et le classement des artisans dans les résultats.

## 3.1 Localisation côté artisan

### 3.1.1 Activation du mode disponible

L'artisan doit activer un statut « Disponible » depuis son application
mobile pour commencer à recevoir des demandes. Ce statut déclenche le
partage actif de sa position GPS en arrière-plan.

-   Fréquence de mise à jour de la position : toutes les 30 secondes
    (paramétrable depuis le back-office)

-   La position est envoyée au serveur via la connexion WebSocket Vert.x
    active (canal dédié "gps-update"), mise en cache Redis avec TTL 60s,
    et persistance en base PostgreSQL/PostGIS pour les requêtes
    spatiales.

-   En cas de perte de connexion, la dernière position connue est
    conservée avec un horodatage --- si la position date de plus de 5
    minutes, l'artisan est marqué « hors ligne »

-   L'artisan peut désactiver le mode disponible à tout moment ; le
    partage de localisation s'arrête immédiatement

### 3.1.2 Gestion de la confidentialité

La position GPS précise de l'artisan n'est jamais exposée directement
au client. Seule la distance estimée en temps de trajet (ex. : « à 12
min ») et la zone géographique approximative (ex. : arrondissement,
commune) sont affichées côté client.

+-----------------------------------------------------------------------+
| **Règle de confidentialité --- position artisan**                     |
|                                                                       |
| La position GPS précise (latitude/longitude) est stockée côté serveur |
| uniquement. Le client reçoit uniquement un temps de trajet estimé et  |
| une zone approximative. La position exacte n'est jamais transmise à  |
| l'application client.                                                |
+=======================================================================+
+-----------------------------------------------------------------------+

## 3.2 Localisation côté client

Le client peut se localiser de deux façons :

-   Localisation automatique : utilisation du GPS du téléphone via
    l'API de géolocalisation native (permission requise)

-   Localisation manuelle : saisie d'une adresse dans un champ avec
    autocomplétion (Google Places Autocomplete ou équivalent)

La position du client est utilisée uniquement le temps de la recherche.
Elle n'est pas stockée en base de données au-delà de la durée de vie de
la session de recherche (30 minutes maximum).

## 3.3 Calcul du rayon de dispatch --- 30 minutes en voiture

Le critère de sélection des artisans notifiés n'est pas un rayon
kilométrique fixe, mais un temps de trajet estimé en voiture, paramétré
par défaut à 30 minutes.

  -----------------------------------------------------------------------
  **Paramètre**            **Détail technique**
  ------------------------ ----------------------------------------------
  **API utilisée**         OpenRouteService (ORS) auto-hébergé --- calcul
                           isochrone 30 min depuis la position client

  **Mode de calcul**       Temps de trajet en voiture, conditions de
                           trafic en temps réel si disponible

  **Seuil par défaut**     30 minutes (paramétrable dans le back-office)

  **Stratégie de           Si l'API est indisponible, calcul par rayon
  fallback**               kilométrique estimé (vitesse moyenne 40 km/h)

  **Fréquence de           À chaque nouvelle demande client ; pas de
  recalcul**               recalcul en continu

  **Périmètre              Si moins de 5 artisans répondent après 60s, le
  d'élargissement**       périmètre est élargi à 45 min puis 60 min
                           (paramétrable)
  -----------------------------------------------------------------------

# 4. Flux de notification push

Les notifications push constituent le canal de communication temps réel
entre la plateforme et les utilisateurs mobiles. Deux types de flux sont
à distinguer : les notifications vers les artisans (dispatch de
demandes) et les notifications vers les clients (résultats et
confirmations).

## 4.1 Infrastructure de notification

Les notifications push sont gérées via Firebase Cloud Messaging (FCM),
qui couvre à la fois iOS (via APNs) et Android. Chaque application
mobile enregistre un token FCM unique à l'installation, stocké en base
de données et associé au compte utilisateur.

+-----------------------------------------------------------------------+
| **Gestion des tokens FCM**                                            |
|                                                                       |
| À chaque connexion, l'application vérifie que le token FCM est à     |
| jour et l'envoie au serveur si nécessaire. Les tokens expirés sont   |
| automatiquement supprimés de la base. Un artisan peut avoir plusieurs |
| tokens actifs (plusieurs appareils).                                  |
+=======================================================================+
+-----------------------------------------------------------------------+

## 4.2 Notification de demande vers les artisans

### 4.2.1 Déclenchement

Dès qu'un client valide sa recherche, le serveur :

-   Requête la base pour identifier tous les artisans dont le statut est
    « Disponible »

-   Filtre par type d'activité correspondant à la demande

-   Filtre par temps de trajet inférieur ou égal au seuil configuré (30
    min par défaut)

-   Envoie une notification push FCM à tous les artisans retenus en une
    seule requête batch

### 4.2.2 Contenu de la notification

  -----------------------------------------------------------------------
  **Champ**              **Valeur affichée**
  ---------------------- ------------------------------------------------
  **Titre**              « Nouvelle demande près de vous »

  **Corps**              Type de prestation + commune du client (ex : «
                         Plombier --- Paris 11e »)

  **Données embarquées   ID de la demande, coordonnées approximatives,
  (silent data)**        type de prestation

  **Action au tap**      Ouverture de l'app sur la modale de détail de
                         la demande

  **TTL (Time To Live)** 60 secondes --- la notification expire
                         automatiquement si non reçue
  -----------------------------------------------------------------------

### 4.2.3 Comportement si l'app est en arrière-plan ou fermée

FCM gère nativement la réception des notifications même lorsque
l'application est fermée. Une notification système s'affiche dans la
barre de notifications. Au tap, l'application s'ouvre et l'artisan
est dirigé vers la modale de la demande correspondante (deep link via
l'ID de la demande embarqué dans le payload).

## 4.3 Notification de résultats vers le client

  -----------------------------------------------------------------------
  **Événement**            **Notification envoyée au client**
  ------------------------ ----------------------------------------------
  **Aucun artisan          « Aucun artisan disponible pour le moment.
  disponible après délai   Réessayez plus tard. »
  maximum**                

  **Premier artisan        Notification silencieuse pour rafraîchir l'UI
  répondant reçu**         (pas de notification visible)

  **Seuil de 5 artisans    « Votre liste d'artisans est prête !
  atteint OU fin du délai  Consultez les résultats. »
  de 60s**                 

  **Artisan sélectionné    Confirmation silencieuse --- mise à jour de
  par le client**          l'état de la demande
  -----------------------------------------------------------------------

## 4.4 Gestion des erreurs de notification

-   Si un token FCM est invalide (appareil changé, app désinstallée),
    l'erreur FCM est interceptée et le token est supprimé
    automatiquement de la base

-   Les échecs de notification ne bloquent pas le processus de dispatch
    --- la demande est quand même enregistrée

-   Un log des notifications envoyées/reçues/échouées est conservé 30
    jours pour débogage

-   En cas d'indisponibilité de FCM, un mécanisme de fallback par SMS
    (via Twilio ou équivalent) peut être activé depuis le back-office
    pour les artisans ayant renseigné leur numéro

# 5. Système de matching artisan --- client

Le moteur de matching est le cœur fonctionnel de la plateforme. Il
détermine quels artisans sont contactés pour une demande donnée, dans
quel ordre les résultats sont présentés au client, et selon quels
critères.

## 5.1 Critères de sélection des artisans notifiés

  -----------------------------------------------------------------------
  **Critère**            **Description**
  ---------------------- ------------------------------------------------
  **Statut disponible**  L'artisan a activé son mode « Disponible » dans
                         l'application --- sa localisation est active et
                         récente (\< 5 min)

  **Compétence           Le type d'activité ou les services proposés par
  correspondante**       l'artisan correspondent à la recherche du
                         client (matching par type d'activité et/ou
                         service)

  **Zone                 La localisation GPS actuelle de l'artisan est à
  d'intervention**      moins de 30 minutes en voiture du client (ou
                         dans la zone d'intervention définie sur son
                         profil)

  **Compte actif et      Le profil de l'artisan est complet et validé
  vérifié**              par l'administration (SIREN vérifié,
                         informations renseignées)

  **Non blacklisté**     L'artisan n'est pas suspendu ou blacklisté par
                         l'administration
  -----------------------------------------------------------------------

## 5.2 Classement des résultats côté client

Les artisans qui ont répondu favorablement sont présentés au client
selon un score composite. L'ordre d'affichage dans la liste résulte du
calcul suivant :

+-----------------------------------------------------------------------+
| **Score de classement (indicatif)**                                   |
|                                                                       |
| Score = (Note moyenne × 0.4) + (Proximité normalisée × 0.4) + (Taux   |
| de réponse × 0.2) Les pondérations sont ajustables depuis le          |
| back-office. En cas d'égalité, l'artisan ayant répondu en premier   |
| est prioritaire.                                                      |
+=======================================================================+
+-----------------------------------------------------------------------+

  -----------------------------------------------------------------------
  **Critère de classement**    **Poids par défaut**
  ---------------------------- ------------------------------------------
  **Note moyenne (1 à 5        40 %
  étoiles)**                   

  **Proximité (temps de trajet 40 %
  --- plus proche = mieux      
  classé)**                    

  **Taux de réponse historique 20 %
  (% de demandes acceptées)**  
  -----------------------------------------------------------------------

## 5.3 Gestion du délai de recherche et élargissement progressif

  -------------------------------------------------------------------------
  **1**   **t = 0s**            Lancement de la recherche. Dispatch aux
                                artisans dans un rayon de 30 min.
  ------- --------------------- -------------------------------------------
  **2**   **t = 60s**           Si au moins 5 artisans ont répondu : les
                                résultats sont envoyés au client. Sinon, on
                                continue.

  **3**   **t = 90s**           Élargissement automatique du rayon à 45
                                minutes. Nouveau dispatch aux artisans
                                supplémentaires.

  **4**   **t = 150s**          Si toujours \< 5 artisans, élargissement à
                                60 minutes. Dernier dispatch.

  **5**   **t = 210s**          Envoi des résultats au client, quelle que
                                soit la quantité d'artisans répondants
                                (même 0). Si aucun résultat, message
                                d'invitation à réessayer.
  -------------------------------------------------------------------------

Tous les paramètres de délai et de rayon sont configurables depuis le
back-office. La logique d'élargissement peut être désactivée.

## 5.4 Cas de concurrence entre artisans

Plusieurs clients peuvent initier des recherches simultanées dans une
même zone. Le système gère cette concurrence de la façon suivante :

-   Un artisan peut recevoir plusieurs notifications de demandes
    simultanées

-   Un artisan ne peut « accepter » qu'une seule demande à la fois ---
    une fois qu'il accepte une demande, les autres notifications
    actives sont annulées sur son appareil

-   Si un artisan a déjà accepté une demande, il passe temporairement en
    statut « Occupé » pour les nouvelles recherches (paramétrable :
    durée d'indisponibilité automatique)

-   Le statut repasse à « Disponible » automatiquement après une durée
    configurable, ou manuellement à l'initiative de l'artisan

# 6. Facturation des artisans --- Modèle hybride

Le modèle de facturation repose sur deux principes complémentaires
visant à éliminer les contestations en fin de mois tout en minimisant la
friction à l'inscription. En V1 MVP : facturation mensuelle avec
notification temps réel à chaque mise en relation et prélèvement SEPA
automatique. En V2 : ajout optionnel du compte prépayé pour les artisans
souhaitant maîtriser leur budget.

## 6.0 Principes du modèle hybride

  --------------------------------------------------------------------------
  **Version**   **Mécanisme**                     **Objectif**
  ------------- --------------------------------- --------------------------
  **V1 MVP**    Facturation mensuelle postpayée + Frein à l'inscription
                notification push + email en      minimal, zéro surprise en
                temps réel à chaque mise en       fin de mois
                relation + prélèvement SEPA       
                automatique en fin de mois        

  **V2**        Ajout optionnel du compte prépayé Zéro impayé possible,
                : l'artisan recharge son solde et maîtrise du budget pour
                chaque mise en relation débite    les artisans qui le
                automatiquement le compte. Le     souhaitent
                statut "Disponible" est bloqué si 
                le solde est insuffisant.         
  --------------------------------------------------------------------------

## 6.1 Définition d'une mise en relation facturable

+-----------------------------------------------------------------------+
| **Règle de facturation**                                              |
|                                                                       |
| Une mise en relation est considérée comme facturable dès lors que     |
| l'artisan a été présenté dans la liste de résultats affichée au      |
| client ET que le client a cliqué sur le profil de cet artisan         |
| (ouverture de la modale avec les deux options : appel ou transmission |
| d'adresse). Le fait que la prestation ait lieu ou non n'influe pas  |
| sur la facturation.                                                   |
+=======================================================================+
+-----------------------------------------------------------------------+

  -----------------------------------------------------------------------
  **Événement**                         **Facturable ?**
  ------------------------------------- ---------------------------------
  **Artisan notifié mais n'ayant pas   Non
  répondu**                             

  **Artisan ayant répondu mais non      Non
  retenu dans les résultats**           

  **Artisan affiché dans la liste       Non
  résultat mais non cliqué par le       
  client**                              

  **Client a ouvert la modale de        Oui
  l'artisan (appel ou adresse)**       

  **Artisan sélectionné mais prestation Oui (mise en relation réalisée)
  non réalisée**                        

  **Artisan sélectionné et prestation   Oui
  réalisée**                            
  -----------------------------------------------------------------------

## 6.2 Notification temps réel à chaque mise en relation (V1)

Dès qu'un événement de mise en relation facturable est enregistré en
base (client ayant appelé ou transmis l'adresse à l'artisan), le système
déclenche immédiatement et simultanément :

-   **Une notification push sur SVP Pro :** "Nouvelle mise en relation
    ajoutée à votre facture --- \[type de prestation\] --- \[X,XX € HT\]
    --- Votre compteur ce mois : N mise(s) en relation."

-   **Un email de confirmation :** contenant la date/heure, le type de
    prestation, le montant unitaire HT et le cumul du mois en cours. Cet
    email est horodaté et conservé comme preuve en cas de litige
    ultérieur.

-   **Une mise à jour en temps réel de l'écran "Mon compteur du mois"
    dans SVP Pro :** liste détaillée de toutes les lignes du mois en
    cours avec date, type de prestation, montant unitaire et total
    cumulé provisoire TTC.

*La notification immédiate est le principal mécanisme anti-contestation.
L'artisan valide mentalement chaque ligne au moment où elle se produit,
et non un mois plus tard lors de la réception d'une facture
récapitulative.*

### 6.2.1 Plafond mensuel de mises en relation

Chaque artisan est soumis à un plafond mensuel de mises en relation,
configurable depuis le back-office (valeur par défaut : 20 mises en
relation par mois). Lorsque ce plafond est atteint :

-   L'artisan reçoit une notification l'informant qu'il a atteint son
    plafond du mois et que les prochaines mises en relation seront
    toujours facturées.

-   Il doit confirmer explicitement vouloir continuer à recevoir des
    demandes au-delà du plafond via un bouton de confirmation dans
    l'application ("Continuer à recevoir des demandes ce mois"). Cette
    confirmation est enregistrée et constitue une preuve de consentement
    supplémentaire.

-   Identifiant unique de la mise en relation

-   Date et heure de la mise en relation

-   Identifiant de l'artisan

-   Type de prestation demandée

-   Action du client (appel ou transmission d'adresse)

-   Statut de facturation (en attente / facturé / litigieux)

Ces données sont accessibles par l'artisan depuis son espace web « Mes
interventions », et par l'administration depuis le back-office.

## 6.3 Onboarding financier --- Mandat SEPA et IBAN

La collecte des informations de paiement fait partie intégrante du
parcours d'inscription de l'artisan sur le site web professionnel. Le
principe est de recueillir l'IBAN et le mandat SEPA avant que l'artisan
ne commence à recevoir des demandes, afin d'éviter tout blocage au
moment du premier prélèvement.

### 6.3.1 Collecte de l'IBAN à l'inscription

L'IBAN est collecté lors de l'étape d'onboarding sur le site web
professionnel, après la validation du SIREN et avant l'activation du
compte. L'artisan signe électroniquement un mandat de prélèvement SEPA
(via Stripe ou GoCardless). Sans IBAN validé et mandat signé, le profil
est marqué "incomplet" et l'artisan ne peut pas activer son statut
"Disponible" dans SVP Pro.

### 6.3.2 Cycle de facturation mensuelle

La période de facturation court du 1er au dernier jour du mois. Le 1er
du mois suivant, un cron job génère automatiquement une facture PDF par
artisan ayant au moins une mise en relation sur la période. La facture
est envoyée par email et disponible dans l'espace web professionnel. Le
prélèvement SEPA est initialisé à J+5 après émission (délai
réglementaire SEPA). L'artisan dispose de J+0 à J+5 pour contester une
ligne avant l'exécution du prélèvement.

### 6.3.2 Contenu d'une facture

  -----------------------------------------------------------------------
  **Élément**           **Détail**
  --------------------- -------------------------------------------------
  **En-tête**           Raison sociale de S'IL VOUS PLAÎT, numéro de
                        TVA, coordonnées

  **Destinataire**      Raison sociale de l'artisan, SIREN, adresse

  **Numéro de facture** Format : SVP-AAAA-MM-{ID artisan}

  **Détail des lignes** Une ligne par mise en relation : date, type de
                        prestation, montant unitaire HT

  **Total HT / TVA /    Calculé automatiquement
  TTC**                 

  **Conditions de       Paiement à 30 jours, prélèvement automatique ou
  règlement**           virement
  -----------------------------------------------------------------------

### 6.3.3 Modes de règlement

Deux modes de règlement sont proposés :

-   Prélèvement automatique SEPA : l'artisan renseigne son IBAN lors de
    l'onboarding. Le prélèvement est effectué à J+5 après émission de
    la facture (via Stripe ou GoCardless).

-   Virement bancaire manuel : l'artisan règle par virement dans le
    délai imparti. Un rappel automatique est envoyé à J+15 si le
    paiement n'est pas reçu.

## 6.4 Gestion des litiges et suspension

  -----------------------------------------------------------------------
  **Étape**       **Action automatique**
  --------------- -------------------------------------------------------
  **Mise en       Notification push + email immédiats à l'artisan. Mise à
  relation**      jour de l'écran "Mon compteur du mois" dans SVP Pro.

  **1er du mois** Facture PDF générée et envoyée par email. Disponible
                  dans l'espace web. Début de la fenêtre de contestation
                  (5 jours).

  **J+5 (du       Prélèvement SEPA automatique exécuté (si aucune
  mois)**         contestation en attente). Notification push de
                  confirmation à l'artisan.

  **J+15 (du      Rappel email + notification push si le prélèvement a
  mois)**         échoué (rejet SEPA, IBAN invalide, provision
                  insuffisante).

  **J+30 (du      Suspension automatique du compte artisan (ne reçoit
  mois)**         plus de demandes)

  **J+45 (du      Suspension automatique du compte (ne reçoit plus de
  mois)**         demandes). Notification à l'administrateur pour suivi
                  manuel du recouvrement.
  -----------------------------------------------------------------------

L'artisan dispose d'une fenêtre de contestation de 5 jours après
réception de la facture (entre le 1er et le 6 du mois). Il peut
contester une ligne depuis son espace web ou depuis SVP Pro (bouton
"Signaler cette ligne"). L'administrateur traite le litige depuis le
back-office et peut annuler ou ajuster la ligne avant l'exécution du
prélèvement. Passé J+5 sans contestation, le prélèvement est réputé
accepté tacitement --- les notifications temps réel envoyées tout au
long du mois constituent la preuve que l'artisan était informé de chaque
ligne.

# 7. Spécifications fonctionnelles

## 7.1 Applications mobiles --- Vue d'ensemble

La plateforme comprend deux applications mobiles distinctes, développées
en React Native (monorepo partagé) et publiées séparément sur l'App
Store et le Play Store. Cette séparation est imposée par des contraintes
techniques et de validation des stores :

-   **SVP Client** --- application destinée aux particuliers.
    Localisation GPS ponctuelle (lors d'une recherche active
    uniquement). Connexion par email/mot de passe ou via
    Google/Facebook.

-   **SVP Pro** --- application destinée aux artisans professionnels.
    Localisation GPS permanente en arrière-plan (permission "always on")
    indispensable pour le système de matching en temps réel. Connexion
    email/mot de passe uniquement (compte créé via le site web
    professionnel).

  -----------------------------------------------------------------------
  **Type de connexion**  **Méthodes disponibles**
  ---------------------- ------------------------------------------------
  **Particulier**        Email + mot de passe / Connexion Google /
                         Connexion Facebook

  **Professionnel**      Email + mot de passe uniquement (inscription via
                         l'espace web)
  -----------------------------------------------------------------------

## 7.2 Application mobile client --- SVP Client

### 7.2.1 Page d'accueil

La page d'accueil est constituée d'une carte géographique interactive.
Le client est invité à se localiser (localisation automatique GPS ou
saisie manuelle d'adresse). Un champ texte avec autocomplétion lui
permet de saisir un type de prestation ou un service (ex : « Plombier »,
« Fuite chauffe-eau »).

### 7.2.2 Recherche de professionnels

Dès que le client lance la recherche, la carte affiche un périmètre
dynamique autour de sa position et un indicateur de recherche en cours.
Une animation simule la présence de professionnels dans la zone. Les
notifications push sont envoyées aux artisans éligibles selon les
critères du système de matching (section 5).

### 7.2.3 Résultats de recherche

Les artisans répondants sont affichés dans un tableau de résultats
présentant pour chaque artisan : nom, photo de profil, note (étoiles
1-5), tarif horaire indicatif, distance et temps de trajet estimé. La
liste se met à jour en temps réel à mesure que des artisans répondent.

### 7.2.4 Sélection d'un artisan

En sélectionnant un artisan, une modale propose deux options :

-   Appeler directement : ouverture de l'application téléphone avec le
    numéro pré-rempli

-   Transmettre l'adresse : envoi de la localisation du client à
    l'artisan via la plateforme

### 7.2.5 Notation

Après un délai paramétrable (par défaut 3 heures après la mise en
relation), le client reçoit une notification l'invitant à noter
l'artisan de 1 à 5 étoiles et à laisser un commentaire facultatif.

## 7.3 Application mobile artisan --- SVP Pro

SVP Pro est l'application mobile dédiée aux artisans professionnels
inscrits sur la plateforme. Son rôle principal est de permettre à
l'artisan de rester localisable en permanence et de recevoir les
demandes clients en temps réel, même lorsque l'application est en
arrière-plan ou que l'écran est éteint. L'inscription s'effectue
exclusivement via le site web professionnel. L'application mobile ne
propose pas de formulaire d'inscription.

### 7.3.0 Connexion et permissions au premier lancement

Au premier lancement, l'artisan se connecte avec ses identifiants créés
sur le site web (email + mot de passe). Il lui est ensuite demandé
d'accorder deux permissions système obligatoires au bon fonctionnement
de l'application :

-   **Localisation en arrière-plan ("Toujours autoriser") :** permission
    GPS permanente, indispensable pour que le serveur puisse calculer la
    proximité de l'artisan même quand l'app est fermée. Un écran
    pédagogique explique pourquoi cette permission est nécessaire avant
    de déclencher le dialogue système.

-   **Notifications push :** indispensables pour recevoir les alertes de
    demandes clients, y compris en arrière-plan.

*Si l'artisan refuse la permission de localisation en arrière-plan, un
message l'informe qu'il ne pourra pas recevoir de demandes et qu'il doit
activer cette permission depuis les réglages système. L'accès à
l'application reste possible mais le mode "Disponible" est bloqué.*

### 7.3.0b Localisation en arrière-plan --- fonctionnement technique

Lorsque l'artisan est en statut "Disponible", un service natif de
géolocalisation s'exécute en arrière-plan (Background Location sur
Android, Significant Location Changes + Background Fetch sur iOS). La
position GPS est envoyée au serveur toutes les 30 secondes via une
requête HTTPS silencieuse. Ce service s'arrête automatiquement dès que
l'artisan passe en statut "Indisponible" ou se déconnecte. La batterie
est préservée au maximum : la fréquence de mise à jour est réduite
automatiquement en cas de faible niveau de batterie (seuil paramétrable,
par défaut 20%).

### 7.3.1 Page d'accueil

La page d'accueil affiche une carte géographique indiquant la zone
d'activité de l'artisan. Un bouton « Disponible / Indisponible »
permet à l'artisan d'activer ou désactiver la réception des demandes
et le partage de sa localisation.

### 7.3.2 Réception d'une demande

Quand une demande correspondant à son profil est émise dans sa zone,
l'artisan reçoit une notification push. En appuyant dessus, la carte se
centre sur la localisation de la demande. Un pictogramme représente la
position du client. En appuyant sur ce pictogramme, une modale affiche :

-   Prénom + initiale du nom du client

-   Adresse exacte du client

-   Type de prestation demandée

-   Boutons « Accepter » ou « Fermer »

### 7.3.3 Mes interventions

L'artisan visualise l'historique de toutes les mises en relation le
concernant. Un bouton « Signaler » lui permet de contester une notation
ou une mise en relation auprès de l'administration.

## 7.4 Application web --- Espace professionnels

### 7.4.1 Inscription et onboarding

L'inscription se fait via email + mot de passe uniquement. À la
première connexion, l'artisan remplit son profil complet :

-   Raison sociale, forme juridique, SIREN

-   Nom et prénom du responsable, numéro de téléphone, email de contact

-   Description de l'activité

-   Type d'activité (liste déroulante prédéfinie) et services proposés
    (avec fourchette tarifaire optionnelle par service)

-   Adresse physique

-   Zone(s) d'intervention : périmètre circulaire saisi sur une carte
    géographique

-   IBAN + mandat SEPA signé électroniquement (obligatoire à
    l'inscription, avant activation du compte)

### 7.4.2 Mon compte --- gestion du profil

L'artisan peut modifier à tout moment toutes les informations
renseignées lors de l'onboarding : localisation, zones d'intervention,
types d'activité, services et tarifs.

### 7.4.3 Mes factures

Un espace dédié permet à l'artisan de consulter et télécharger toutes
ses factures mensuelles, de suivre le statut de chaque facture (payée /
en attente / litigieuse) et de contester une ligne via le formulaire de
signalement.

## 7.5 Application web --- Back-office administration

Le back-office est accessible uniquement au personnel de S'IL VOUS
PLAÎT. Il permet de :

  -----------------------------------------------------------------------
  **Module**             **Fonctionnalités**
  ---------------------- ------------------------------------------------
  **Gestion des          CRUD complet --- validation des profils,
  artisans**             suspension, consultation des statistiques par
                         artisan

  **Gestion des          CRUD complet --- historique des recherches et
  particuliers**         mises en relation

  **Référentiels**       CRUD des types d'activité et des services
                         associés --- gestion de la liste
                         d'autocomplétion

  **Paramétrage de la    Délai de recherche (s), rayon initial (min),
  recherche**            nombre de relances, timeout avant notation

  **Gestion des          CRUD notations --- traitement des signalements
  notations**            artisans

  **Facturation**        Tableau de bord des factures mensuelles ---
                         gestion des litiges et impayés --- export
                         comptable

  **Notifications**      Monitoring des envois push --- logs d'erreurs
                         --- configuration du fallback SMS

  **Tableau de bord**    Statistiques globales : nombre de demandes, taux
                         de matching, CA mensuel, artisans actifs
  -----------------------------------------------------------------------

## 7.6 Site vitrine --- One page

Une page promotionnelle présente l'application et ses fonctionnalités
selon la structure suivante :

-   Image + slogan et signature de l'application

-   Liens de téléchargement vers l'App Store et le Google Play Store

-   Présentation du fonctionnement (3 étapes illustrées)

-   Section d'incitation à l'inscription pour les professionnels

-   Contact & mentions légales

# 8. Sécurité et données personnelles

## 8.1 Authentification et autorisation

-   Authentification par JWT (JSON Web Token) avec durée de validité de
    24h et refresh token de 30 jours

-   Mots de passe stockés avec bcrypt (coût minimum 12)

-   Connexion sociale via OAuth 2.0 (Google, Facebook) --- aucun mot de
    passe tiers stocké

-   Rate limiting sur les endpoints d'authentification (5 tentatives /
    15 min par IP)

-   Rôles distincts : Particulier / Professionnel / Administrateur ---
    cloisonnement strict des accès API

## 8.2 Protection des données --- RGPD

-   Les données personnelles des utilisateurs sont stockées sur des
    serveurs localisés en Europe (Union Européenne)

-   La position GPS des artisans n'est jamais transmise aux clients ---
    seul le temps de trajet estimé est exposé

-   La position GPS des clients n'est conservée en base que le temps de
    la session de recherche active (max 30 min)

-   Un mécanisme de suppression de compte (droit à l'effacement) est
    implémenté pour les particuliers et les professionnels

-   Une politique de confidentialité et des CGU sont intégrées à
    l'application et acceptées lors de l'inscription

# 9. Méthodologie

## 9.1 Livraisons continues

Des livraisons régulières sont effectuées sur l'environnement de test.
Le client peut utiliser l'application dès la première livraison, dans
la limite des fonctionnalités disponibles à ce stade.

## 9.2 Modifications du périmètre

La modification de fonctionnalités annoncées mais non encore développées
est possible et soumise à une nouvelle étude de faisabilité et à un
chiffrage. Si la charge estimée est équivalente à celle du devis
initial, la modification ne génère pas de prestation supplémentaire. Si
la charge augmente, une prestation supplémentaire est nécessaire. Si la
charge diminue, la différence est rendue sous forme de jours/homme
offerts.

# 10. Livrables

## 10.1 Environnement de test

-   Web : URL protégée par authentification pour les tests utilisateurs

-   iOS : TestFlight

-   Android : Programme Beta Google Play

## 10.2 Code source et propriété intellectuelle

La livraison du code source, des fichiers SQL, des configurations et
documentations aura lieu après validation de tous les tests et signature
du procès-verbal de livraison définitive. La propriété intellectuelle et
la jouissance d'exploitation du code source sont transmises au client à
cette occasion.

## 10.3 Garantie et maintenance

Le bon fonctionnement de l'application est garanti 3 mois après
livraison en production. La maintenance corrective (bugs et anomalies)
est assurée gratuitement pendant cette période. Au-delà, un contrat de
maintenance est mis en place.

# 11. Charge de travail estimée

  -----------------------------------------------------------------------
  **Module / Fonctionnalité**                           **Charge (j/h)**
  ----------------------------------------------------- -----------------
  **Direction artistique + UX/UI guidelines + maquettes 
  des écrans principaux**                               

  **GLOBAL --- Mise en place technologique (socle       
  Quarkus natif GraalVM + Angular + Flutter,            
  environnements dev/staging/prod, CI/CD GitHub         
  Actions, VPS OVH)**                                   

  **GLOBAL --- Services tiers (emails transactionnels,  
  FCM, Distance Matrix API)**                           

  **GLOBAL --- Infrastructure temps réel : WebSocket    
  Vert.x (dispatch demandes + réception positions GPS), 
  cache Redis positions, logique isochrone ORS +        
  PostGIS ST_Within**                                   

  **GLOBAL --- Moteur de géolocalisation et calcul de   
  rayon (30 min en voiture)**                           

  **BACK-END --- CRUD particuliers / professionnels /   
  notations / référentiels**                            

  **BACK-END --- API sécurisée (JWT, rôles, rate        
  limiting)**                                           

  **BACK-END --- Moteur de matching et dispatch des     
  notifications push**                                  

  **BACK-END --- Module de facturation hybride :        
  notification temps réel (push + email) à chaque mise  
  en relation, compteur mensuel, plafond configurable,  
  génération facture PDF, mandat SEPA, prélèvement      
  GoCardless/Stripe, gestion contestations et           
  suspension**                                          

  **WEB --- Back-office complet (gestion artisans,      
  facturation, paramétrage, litiges)**                  

  **WEB --- Site vitrine one-page (5 sections,          
  responsive)**                                         

  **WEB --- Espace professionnel (inscription,          
  onboarding, profil, factures)**                       

  **MOBILE CLIENT (SVP Client) --- Connexion /          
  inscription particulier (email + RS)**                

  **MOBILE CLIENT --- SVP Client Flutter : carte        
  flutter_map/OSM, recherche, animation liste           
  résultats, WebSocket, push FCM**                      

  **MOBILE CLIENT --- Espace particulier : résultats,   
  modale de sélection**                                 

  **MOBILE CLIENT --- Espace particulier : notation,    
  mon compte**                                          

  **MOBILE ARTISAN (SVP Pro) --- Connexion, permissions 
  GPS background, statut disponible/indisponible, carte 
  temps réel**                                          

  **MOBILE ARTISAN (SVP Pro) --- Réception demande      
  push, modale détail, acceptation, deep link**         

  **MOBILE ARTISAN (SVP Pro) --- Mes interventions,     
  signalement notation, navigation client**             

  **Gestion de projet + accompagnement aux tests**      
  -----------------------------------------------------------------------

# 12. Forfaits

  -----------------------------------------------------------------------
  **Type**               **Prestation**
  ---------------------- ------------------------------------------------
  **CONCEPTION ---       Webdesign UX/UI • Application back-end / API
  Développement          (Quarkus Java) • Application front-end web
  complet**              (Angular) • Application mobile client SVP Client
                         iOS + Android (Flutter) • Application mobile
                         artisan SVP Pro iOS + Android (Flutter)

  **HÉBERGEMENT WEB**    Location serveur dédié ou VPS (OVH /
                         Infomaniak). Option cloud (GCP, AWS, Azure)
                         soumise à prestation supplémentaire à la
                         consommation.

  **EXPLOITATION WEB**   Sécurisation serveur, monitoring, mises à jour,
                         optimisation performances, sauvegardes
                         journalières, extraction de données.

  **MAINTENANCE WEB**    Maintenance préventive et corrective de
                         l'application web et du back-office.

  **MAINTENANCE MOBILE** Maintenance préventive et corrective des
                         applications iOS et Android (mises à jour stores
                         incluses).
  -----------------------------------------------------------------------

# 13. Glossaire

  -----------------------------------------------------------------------
  **Terme**             **Définition**
  --------------------- -------------------------------------------------
  **Particulier         Utilisateur final de la plateforme ayant besoin
  (client)**            d'un service à domicile ou d'artisan.

  **Professionnel       Utilisateur proposant ses services aux
  (artisan)**           particuliers via la plateforme.

  **Administrateur**    Personnel de S'IL VOUS PLAÎT gérant l'ensemble
                        de l'application via le back-office.

  **Mise en relation**  Action par laquelle un client ouvre la fiche
                        d'un artisan (appel ou transmission d'adresse).
                        Événement facturable.

  **Dispatch**          Envoi simultané de notifications push à
                        l'ensemble des artisans éligibles pour une
                        demande donnée.

  **Type d'activité**  Métier ou activité (ex : Plombier) --- liste
                        référentielle administrable.

  **Service**           Sous-catégorie d'un type d'activité (ex : Fuite
                        chauffe-eau) --- ce qu'une activité peut
                        proposer.

  **Token FCM**         Identifiant unique attribué par Firebase Cloud
                        Messaging à chaque installation d'application
                        mobile pour l'envoi de notifications push.

  **TTL**               Time To Live --- durée de validité d'une
                        notification push. Passé ce délai, la
                        notification est abandonnée.

  **CRUD**              Create, Read, Update, Delete --- opérations
                        standard d'administration de données.

  **MVP**               Minimum Viable Product --- première version
                        fonctionnelle de l'application couvrant les
                        fonctionnalités essentielles.

  **SEPA**              Single Euro Payments Area --- espace de paiement
                        en euros permettant les prélèvements bancaires
                        automatiques entre pays européens.
  -----------------------------------------------------------------------
