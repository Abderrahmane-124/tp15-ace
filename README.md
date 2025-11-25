# 🏦 Service Bancaire GraphQL avec Spring Boot - Gestion des Transactions

## 📋 Vue d'ensemble

Application Spring Boot implémentant un service bancaire avec GraphQL permettant la gestion de comptes et de transactions financières (dépôts et retraits).

## 🎯 Fonctionnalités implémentées

### Gestion des Comptes
- ✅ Créer un nouveau compte bancaire (COURANT ou EPARGNE)
- ✅ Consulter tous les comptes
- ✅ Consulter un compte par ID
- ✅ Calculer les statistiques des soldes (total, somme, moyenne)

### Gestion des Transactions
- ✅ Ajouter une transaction (DEPOT ou RETRAIT) à un compte
- ✅ Consulter toutes les transactions d'un compte spécifique
- ✅ Consulter toutes les transactions
- ✅ Calculer les statistiques des transactions (nombre, somme des dépôts, somme des retraits)

## 🏗️ Architecture

### Structure du projet
```
src/main/java/com/example/banque_service/
├── BanqueServiceApplication.java          # Point d'entrée + données de test
├── Controllers/
│   └── CompteControllerGraphQL.java       # Contrôleur GraphQL (queries + mutations)
├── Entities/
│   ├── Compte.java                        # Entité Compte
│   ├── TypeCompte.java                    # Enum: COURANT, EPARGNE
│   ├── Transaction.java                   # Entité Transaction
│   └── TypeTransaction.java               # Enum: DEPOT, RETRAIT
├── DTO/
│   └── TransactionRequest.java            # DTO pour les requêtes de transaction
├── Repositories/
│   ├── CompteRepository.java              # Repository JPA pour Compte
│   └── TransactionRepository.java         # Repository JPA pour Transaction
└── Exceptions/
    └── GraphQLExceptionHandler.java       # Gestion des erreurs

src/main/resources/
├── application.properties                  # Configuration
└── graphql/
    └── schema.graphqls                    # Schéma GraphQL
```

### Modèle de données

#### Entité Compte
```java
- id: Long (auto-généré)
- solde: double
- dateCreation: Date
- type: TypeCompte (COURANT, EPARGNE)
- transactions: List<Transaction> (relation OneToMany)
```

#### Entité Transaction
```java
- id: Long (auto-généré)
- montant: double
- date: Date
- type: TypeTransaction (DEPOT, RETRAIT)
- compte: Compte (relation ManyToOne)
```

## 🚀 Démarrage

### Prérequis
- Java 17+
- Maven

### Lancer l'application
```bash
./mvnw spring-boot:run
```

L'application démarre sur **http://localhost:8082**

### Accès aux interfaces

- **GraphiQL**: http://localhost:8082/graphiql
- **Console H2**: http://localhost:8082/h2-console
  - JDBC URL: `jdbc:h2:mem:banque`
  - Username: `sa`
  - Password: (vide)

## 📊 Schéma GraphQL

### Types

```graphql
type Compte {
  id: ID
  solde: Float
  dateCreation: String
  type: TypeCompte
}

type Transaction {
  id: ID
  montant: Float
  date: String
  type: TypeTransaction
  compte: Compte
}

enum TypeCompte {
  COURANT
  EPARGNE
}

enum TypeTransaction {
  DEPOT
  RETRAIT
}
```

### Queries

```graphql
type Query {
  # Gestion des comptes
  allComptes: [Compte]
  compteById(id: ID): Compte
  totalSolde: SoldeStats
  
  # Gestion des transactions
  compteTransactions(id: ID): [Transaction]
  allTransactions: [Transaction]
  transactionStats: TransactionStats
}
```

### Mutations

```graphql
type Mutation {
  saveCompte(compte: CompteRequest): Compte
  addTransaction(transaction: TransactionRequest): Transaction
}
```

## 🧪 Exemples d'utilisation

### Créer un compte
```graphql
mutation {
  saveCompte(compte: {
    solde: 5000.0
    dateCreation: "2024-11-25"
    type: COURANT
  }) {
    id
    solde
    type
  }
}
```

### Ajouter un dépôt
```graphql
mutation {
  addTransaction(transaction: {
    compteId: 1
    montant: 500.0
    date: "2024-11-25"
    type: DEPOT
  }) {
    id
    montant
    type
    compte {
      id
      solde
    }
  }
}
```

### Consulter les transactions d'un compte
```graphql
query {
  compteTransactions(id: 1) {
    id
    montant
    date
    type
  }
}
```

### Statistiques des transactions
```graphql
query {
  transactionStats {
    count
    sumDepots
    sumRetraits
  }
}
```

Pour plus d'exemples, consultez le fichier **GRAPHQL_TESTS.md**.

## 🔧 Technologies utilisées

- **Spring Boot 4.0.0**
- **Spring Data JPA** - Persistance des données
- **Spring GraphQL** - API GraphQL
- **H2 Database** - Base de données en mémoire
- **Lombok** - Réduction du code boilerplate
- **Jakarta Persistence API** - Annotations JPA

## 📦 Dépendances principales

```xml
<dependencies>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-graphql</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
  </dependency>
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
  </dependency>
</dependencies>
```

## 🗄️ Configuration

### application.properties
```properties
# Application
spring.application.name=banque-service
server.port=8082

# H2 Database
spring.datasource.url=jdbc:h2:mem:banque
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.h2.console.settings.web-allow-others=true

# GraphQL
spring.graphql.graphiql.enabled=true
spring.graphql.graphiql.path=/graphiql
```

## 🔍 Données de test

Au démarrage, l'application initialise automatiquement:
- **3 comptes** (2 COURANT, 1 EPARGNE)
- **4 transactions** (3 DEPOT, 1 RETRAIT)

## ⚠️ Gestion des erreurs

L'application gère les erreurs suivantes:
- Compte introuvable (ID inexistant)
- Transaction sur un compte inexistant
- Validation des données d'entrée

Exemple de message d'erreur:
```json
{
  "errors": [{
    "message": "Compte 999 not found",
    "path": ["compteById"]
  }]
}
```

## 📈 Extensions possibles

- [ ] Authentification et autorisation
- [ ] Pagination des résultats
- [ ] Filtrage avancé des transactions
- [ ] Calcul automatique du solde basé sur les transactions
- [ ] Historique des modifications
- [ ] Export des données (PDF, CSV)
- [ ] Notifications lors des transactions
- [ ] Limites de retrait par compte

## 👨‍💻 Auteur

Projet réalisé dans le cadre du cours "Architecture Composants D'entreprise" - 5IIR

