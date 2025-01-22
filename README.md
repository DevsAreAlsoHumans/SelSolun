# Cahier des Charges

## Contexte

La cryptomonnaie est un secteur en pleine expansion, avec une adoption croissante auprès des particuliers et des entreprises. Ce challenge technique vise à créer une application web innovante permettant de suivre et de gérer des actifs cryptographiques. Le projet s'inscrit dans le cadre d'un exercice pratique sur une durée de deux jours, avec des livrables attendus comprenant un déploiement fonctionnel et une présentation convaincante.

## Objectifs

- Fournir une application intuitive pour suivre les cours des cryptomonnaies en temps réel.
- Offrir un portefeuille virtuel permettant d'effectuer des opérations d'achat et de vente.
- Assurer la sécurité des transactions virtuelles.
- Proposer une interface utilisateur attrayante et simple d'utilisation.
- Rendre le projet évolutif pour de futures fonctionnalités (ex. trading avancé, analyses de marché).

## Fonctionnalités

### Fonctionnalités de Base

#### 1. Visualisation des cours

- Connexion avec l'API CoinGecko pour récupérer les prix des cryptomonnaies en temps réel.
- Affichage des informations principales : nom de la cryptomonnaie, symbole, prix actuel, variation sur 24 heures.
- Filtrage des cryptomonnaies par popularité ou volume échangé.

#### 2. Portefeuille virtuel

- Gestion des soldes en différentes cryptos.
- Simulations d’achat et de vente.
- Historique des transactions et suivi des performances du portefeuille.

#### 3. Graphiques interactifs

- Affichage des évolutions de prix via des graphiques dynamiques (courbes sur 1 jour, 1 semaine, 1 mois, etc.).

#### 4. Sécurité

- Authentification via login/mot de passe.
- Données utilisateur stockées de manière sécurisée avec chiffrement (ex. bcrypt pour les mots de passe).

### Fonctionnalités Optionnelles

- Notifications des variations de prix.

## Architecture Technique

### Technologies Utilisées

#### 1. Frontend

- Framework : Angular.
- Librairie graphique : Chart.js pour les graphiques.
- Design : Bootstrap.

#### 2. Backend

- Technologie : Java Spring Boot.
- Sécurité : JWT pour l'authentification.

#### 3. Base de Données

- Type : MySQL (relationnelle).

## Équipe

### Chef de Projet : Charles

- Responsable de la planification, de la coordination, et du suivi des tâches.
- Supervise la qualité des livrables et veille au respect des délais.

### Développeurs Full-Stack Junior :

- Implémentation des fonctionnalités sous la supervision du chef de projet.
- Participation aux tests et corrections.

## Risques et Contraintes

### Contraintes Techniques

- Limite de temps de deux jours pour livrer une version fonctionnelle.
- Utilisation d'une API tierce (CoinGecko) qui impose des limites de requêtes.

### Contraintes Humaines

- Une petite équipe avec deux développeurs junior à encadrer.

### Contraintes Sécuritaires

- Garantir la confidentialité des données utilisateur même dans un environnement simulé.

## Livrables

### Code Source

- Hébergé sur GitHub avec documentation complète (installation, guide utilisateur, fonctionnalités).

### Application Déployée

- Hébergement sur le VPS NEXA.

### Présentation Canva

- Slides clairs et attrayants pour exposer le projet (fonctionnalités, défis techniques, résultats).

## Critères de Validation

- Fonctionnalités de base 100% opérationnelles.
- Déploiement fonctionnel.
- Documentation claire.
- Interface utilisateur intuitive.
