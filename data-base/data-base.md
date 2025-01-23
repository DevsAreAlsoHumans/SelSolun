# **Table users**

## Raison d’être :

Elle sert à gérer les utilisateurs de l’application.  
Chaque utilisateur doit avoir un compte sécurisé pour accéder à ses données personnelles (portefeuille, historique de transactions).

## Colonnes :

- id : Identifiant unique pour chaque utilisateur, utilisé comme clé primaire et pour établir des relations avec d'autres tables.
- username, email : Ces champs sont uniques pour éviter les doublons dans les comptes utilisateurs.
- password_hash : Stockage sécurisé du mot de passe grâce à un hash.
- created_at : Historique de création des comptes.

# **Table cryptocurrencies**

## Raison d’être :

Cette table stocke les données des cryptomonnaies disponibles dans l'application.  
Elle permet d’afficher leurs prix en temps réel et de les lier aux portefeuilles et transactions.

## Colonnes clés :

- id : Identifiant unique pour chaque cryptomonnaie.
- name, symbol : Nom (ex. Bitcoin) et symbole (ex. BTC) pour identification claire. 
- current_price : Permet de récupérer le prix en temps réel pour les transactions ou les graphiques. 
- updated_at : Garde une trace du moment où les prix ont été mis à jour.

# **Table portfolios**

## Raison d’être :

Elle gère le portefeuille de chaque utilisateur, c’est-à-dire les cryptomonnaies qu’ils possèdent et leurs quantités respectives.  
Cette table permet de regrouper les informations nécessaires pour afficher un portefeuille utilisateur complet.

## Colonnes clés :

- id : Identifiant unique du portefeuille.
- user_id : Associe un portefeuille à un utilisateur via une relation avec la table users.
- crypto_id : Identifie les cryptomonnaies détenues via une relation avec la table cryptocurrencies.
- quantity : Quantité de chaque cryptomonnaie possédée par l’utilisateur.
- last_updated : Date de la dernière modification pour suivre l’état à jour du portefeuille.

# **Table transactions**

## Raison d’être :

Elle stocke toutes les transactions (achats/ventes) effectuées par les utilisateurs.  
Cette table est essentielle pour afficher un historique détaillé, calculer les performances du portefeuille, et gérer les simulations d’achat/vente.

## Colonnes clés :

- id : Identifiant unique pour chaque transaction.
- user_id : Identifie l’utilisateur ayant effectué la transaction.
- crypto_id : Identifie la cryptomonnaie concernée.
- quantity : Quantité achetée ou vendue.
- price_at_transaction : Prix unitaire au moment de la transaction, pour calculer les gains/pertes.
- transaction_type : Définit si l’opération est un achat ou une vente ("buy" ou "sell").
- timestamp : Date et heure de la transaction, utile pour les graphiques historiques.

# **Relations entre les tables**

## 1. users ↔ portfolios :

- *Relation : **One-to-Many**.*
  - Un utilisateur (users) peut posséder plusieurs cryptomonnaies différentes dans son portefeuille (portfolios). Chaque ligne dans la table portfolios représente une association entre un utilisateur et une cryptomonnaie spécifique, avec la quantité détenue.
  - Justification : Bien qu'il semble y avoir une relation Many-to-Many entre utilisateurs et cryptomonnaies, elle est implémentée via une table intermédiaire (portfolios) qui inclut les colonnes user_id et crypto_id.

## 2. users ↔ transactions :

- *Relation : **One-to-Many**.*
  - Un utilisateur peut effectuer plusieurs transactions (transactions), chacune concernant une cryptomonnaie spécifique. Chaque ligne dans la table transactions inclut un user_id, identifiant ainsi qui a initié la transaction.
  - Justification : La relation Many-to-Many entre utilisateurs et cryptomonnaies dans le contexte des transactions est également gérée via une table séparée (transactions) qui relie ces entités avec des détails supplémentaires comme la quantité et le prix.

## 3. cryptocurrencies ↔ (portfolios et transactions) :

- *Relation : **Many-to-Many**.*
  - Une cryptomonnaie (cryptocurrencies) peut être détenue par plusieurs utilisateurs via la table portfolios, ou être impliquée dans plusieurs transactions via la table transactions.
  - Justification : La relation Many-to-Many est représentée par les tables intermédiaires :
    - portfolios pour relier les utilisateurs et les cryptomonnaies possédées. 
    - transactions pour relier les utilisateurs aux cryptomonnaies dans le cadre des opérations d'achat ou de vente.