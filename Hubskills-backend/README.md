# Hubskills - Backend

## Description
This is the backend API for the Hubskills platform, built with:
- Java 17
- Spring Boot 3.2.0
- PostgreSQL

## Prerequisites
- Java 17 Development Kit (JDK)
- PostgreSQL running on port 5432 (or configured port)
- Maven (optional, wrapper is included)

## Setup

### Database Configuration
1. Ensure PostgreSQL is running.
2. Create a database named `hubskills`.
3. Set the following environment variables if your local credentials differ from defaults (`postgres`/`postgres`):
    - `DB_USERNAME`: Your PostgreSQL username.
    - `DB_PASSWORD`: Your PostgreSQL password.

   Example (Unix/Mac):
   ```bash
   export DB_USERNAME=myuser
   export DB_PASSWORD=mypassword
   ```

   Example (Windows PowerShell):
   ```powershell
   $env:DB_USERNAME="myuser"
   $env:DB_PASSWORD="mypassword"
   ```

### Running the Application
From the project root:
```bash
./mvnw spring-boot:run
```
Or if you have Maven installed:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

## Security Note
This project was configured to use environment variables for sensitive data. Do not commit `application.properties` with hardcoded real production credentials.
