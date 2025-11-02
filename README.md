# Boutique Marocaine - Système de Gestion Commerciale

## Le lien vers la vidéo : 

https://drive.google.com/drive/folders/18EhEyPxxJrTsg_Z-O36oe9U5jnxr9YxV?usp=sharing

  
## 1. Description du projet

**Contexte fonctionnel :**
Ce projet s'inscrit dans le domaine de la gestion commerciale d'une boutique marocaine spécialisée dans les produits artisanaux. Il permet de suivre et d'administrer l'ensemble des opérations commerciales, en intégrant la gestion des clients, des produits et des commandes.

**Objectif de l'application :**
Fournir un outil digital complet et intuitif pour gérer une boutique, suivre les ventes, gérer les stocks et offrir une vue d'ensemble des performances commerciales.

**Ce que l'application permet concrètement :**
Permet de gérer une boutique complète avec suivi des clients, gestion des produits, traitement des commandes et visualisation des statistiques

## 2. Architecture technique

### 2.1 Stack technologique

**Backend :**
- Spring Boot 3.5.6
- Spring Data JPA / Hibernate pour la gestion des entités et des requêtes SQL
- Spring Validation pour la validation des données
- Spring Web pour les contrôleurs MVC

**Frontend :**
- Thymeleaf pour le rendu côté serveur des pages HTML
- HTML / CSS / Bootstrap 5 pour le design et la mise en page réactive
- Bootstrap Icons pour les icônes
- Design personnalisé avec inspiration marocaine 

**Base de données :**
- MySQL (8.0)
- Configuration via application.properties

**Build / Gestion du projet :**
- Maven pour la gestion des dépendances et le packaging de l'application
- Spring Boot DevTools pour le développement en temps réel

### 2.2 Structure du code

**entity/** : Client - Produit - Commande - CommandePk > classes JPA avec relations complexes
**repository/** : ClientRepository - ProduitRepository - CommandeRepository > interfaces d'accès aux données
**service/** : StatisticsService > logiques métier et calculs statistiques
**controller/** : AdminController - ClientController - CommandeController - HomeController - ProduitController > contrôleurs web MVC
**templates/** : vues Thymeleaf (html) avec design moderne
**static/** : CSS, JS, images


## 3. Fonctionnalités principales

### CRUD sur les entités principales
- **Client** : création, modification, suppression, consultation des informations (nom, email, historique des commandes)
- **Produit** : gestion complète avec nom, catégorie, prix, stock, statut automatique (en stock/rupture)
- **Commande** : gestion avec clé composite, statut, total, relations client-produit

### Gestion avancée des stocks
- Suivi automatique du statut des produits (en stock/rupture)
- Mise à jour automatique du statut lors des modifications de stock
- Filtrage par disponibilité des produits

### Tableau de bord / statistiques
- Nombre total de clients, produits et commandes
- Chiffre d'affaires total
- Produits en stock vs en rupture
- Commandes en attente
- Top 5 des produits les plus vendus
- Commandes récentes avec tri par date

### Interface utilisateur moderne
- Design inspiré de l'artisanat marocain
- Animations et transitions fluides
- Navigation intuitive avec icônes Bootstrap Icons

## 4. Modèle de données

### 4.1 Entités

**Client :**
- id : identifiant unique auto-généré
- nom : nom complet du client
- email : email unique du client
- commandes : liste des commandes associées

**Produit :**
- id : identifiant unique auto-généré
- nom : nom du produit
- categorie : catégorie du produit
- prix : prix en MAD
- stock : quantité disponible
- statut : booléen calculé automatiquement (true si stock > 0)

**Commande :**
- id : clé composite (clientId + dateCreation + produitId)
- statut : état de la commande (EN_ATTENTE, etc.)
- total : montant total de la commande
- client : référence au client (ManyToOne)
- produit : référence au produit (ManyToOne)

**CommandePk :**
- clientId : identifiant du client
- dateCreation : date de création de la commande
- produitId : identifiant du produit

### 4.2 Relations

- Client / Commande : @OneToMany => Un client peut avoir plusieurs commandes
- Produit / Commande : @OneToMany => Un produit peut être dans plusieurs commandes
- Commande / Client : @ManyToOne => Chaque commande appartient à un seul client
- Commande / Produit : @ManyToOne => Chaque commande concerne un seul produit

### 4.3 Configuration base de données

URL de connexion : `jdbc:mysql://localhost:3306/boutique?serverTimezone=UTC`
Identifiants : root / (mot de passe vide)
Stratégie de génération : `spring.jpa.hibernate.ddl-auto=update`

## 5. Lancer le projet

### 5.1 Prérequis
- Java version 21
- Maven 3.9.11
- MySQL 8.0

### 5.2 Installation
1. Cloner le dépôt
2. Configurer la base de données MySQL
3. Créer la base de données "boutique"
4. Lancer l'application (mvn spring-boot:run ou exécuter la classe main)

### 5.3 Accès
- URL d'accès principal : http://localhost:8080/
- Tableau de bord administrateur : http://localhost:8080/admin/dashboard
- Gestion des clients : http://localhost:8080/clients
- Gestion des produits : http://localhost:8080/produits
- Gestion des commandes : http://localhost:8080/commandes

## 6. Points forts techniques

### Architecture robuste
- Séparation claire des responsabilités (Controller/Service/Repository)
- Utilisation des meilleures pratiques Spring Boot

### Interface utilisateur
- Design moderne et responsive
- Thème personnalisé inspiré de la culture marocaine

### Gestion des données
- Relations JPA bien définies
- Clé composite pour les commandes
- Gestion automatique des statuts de produits

