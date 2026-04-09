# HubSkills Backend

API REST de gestion des competences pour une organisation. Permet de gerer les utilisateurs, un catalogue de competences, et le suivi des niveaux de maitrise.

## Stack technique

- **Java 17** + **Spring Boot 3.2.0**
- **Spring Data JPA** (Hibernate)
- **PostgreSQL** (production) / **H2** (tests)
- **Maven** (wrapper inclus)

## Prerequis

- Docker + Docker Compose

## Lancement rapide (Docker)

```bash
docker-compose up --build
```

L'API demarre sur `http://localhost:8080`. PostgreSQL est inclus, aucune installation manuelle necessaire.

## Lancement sans Docker

### Prerequis

- JDK 17+
- PostgreSQL

### 1. Base de donnees

Creer une base PostgreSQL :

```sql
CREATE DATABASE hubskills;
CREATE USER hubskills WITH PASSWORD 'votre_mot_de_passe';
GRANT ALL PRIVILEGES ON DATABASE hubskills TO hubskills;
```

### 2. Variables d'environnement

| Variable      | Description                             | Exemple                                      |
|---------------|-----------------------------------------|----------------------------------------------|
| `DB_URL`      | URL JDBC (optionnel, defaut ci-dessous) | `jdbc:postgresql://localhost:5432/hubskills` |
| `DB_USERNAME` | Utilisateur PostgreSQL                  | `hubskills`                                  |
| `DB_PASSWORD` | Mot de passe PostgreSQL                 | `votre_mot_de_passe`                         |

```bash
export DB_USERNAME=hubskills
export DB_PASSWORD=votre_mot_de_passe
./mvnw spring-boot:run
```

L'API demarre sur `http://localhost:8080`.

## API Endpoints

### Utilisateurs (`/api/users`)

| Methode  | URL                      | Description                |
|----------|--------------------------|----------------------------|
| `GET`    | `/api/users`             | Liste tous les utilisateurs |
| `GET`    | `/api/users/{id}`        | Utilisateur par ID          |
| `GET`    | `/api/users/email/{email}` | Utilisateur par email     |
| `POST`   | `/api/users`             | Creer un utilisateur        |
| `PUT`    | `/api/users/{id}`        | Modifier un utilisateur     |
| `DELETE` | `/api/users/{id}`        | Supprimer un utilisateur    |

**Roles disponibles** : `EMPLOYEE`, `MANAGER`, `ADMIN`

Exemple de body :
```json
{
  "email": "john@example.com",
  "password": "secret",
  "firstName": "John",
  "lastName": "Doe",
  "role": "EMPLOYEE"
}
```

### Competences (`/api/skills`)

| Methode  | URL                | Description             |
|----------|--------------------|-------------------------|
| `GET`    | `/api/skills`      | Liste les competences   |
| `GET`    | `/api/skills/{id}` | Competence par ID       |
| `POST`   | `/api/skills`      | Creer une competence    |
| `PUT`    | `/api/skills/{id}` | Modifier une competence |
| `DELETE` | `/api/skills/{id}` | Supprimer une competence|

### Competences utilisateur (`/api/user-skills`)

| Methode  | URL                                          | Description                          |
|----------|----------------------------------------------|--------------------------------------|
| `GET`    | `/api/user-skills/user/{userId}`             | Competences d'un utilisateur         |
| `GET`    | `/api/user-skills/skill/{skillId}`           | Utilisateurs ayant une competence    |
| `GET`    | `/api/user-skills/user/{userId}/skill/{skillId}` | Relation specifique             |
| `POST`   | `/api/user-skills?userId=&skillId=&currentLevel=&targetLevel=` | Assigner une competence |
| `PATCH`  | `/api/user-skills/user/{userId}/skill/{skillId}?level=` | Modifier le niveau          |
| `DELETE` | `/api/user-skills/user/{userId}/skill/{skillId}` | Retirer une competence          |

**Niveaux** : `1` (Debutant), `2` (Intermediaire), `3` (Avance), `4` (Expert)

## Architecture

```
src/main/java/com/hubskills/
├── HubskillsBackendApplication.java   # Point d'entree
├── controller/                         # Endpoints REST
│   ├── HomeController.java
│   ├── UserController.java
│   ├── SkillController.java
│   ├── UserSkillController.java
│   └── GlobalExceptionHandler.java     # Gestion des erreurs de validation
├── model/                              # Entites JPA
│   ├── User.java
│   ├── Skill.java
│   └── UserSkill.java
├── repository/                         # Acces donnees (Spring Data JPA)
│   ├── UserRepository.java
│   ├── SkillRepository.java
│   └── UserSkillRepository.java
└── service/                            # Logique metier
    ├── UserService.java
    ├── SkillService.java
    └── UserSkillService.java
```

## Tests

Les tests unitaires utilisent JUnit 5 + Mockito (services) et MockMvc (controllers) avec H2 en memoire.

```bash
./mvnw test
```

## Licence

Ce projet est open source.
