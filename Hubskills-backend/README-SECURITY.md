# Security Notes

This project uses environment variables for sensitive configuration.

## Local Development

To run the application locally, you must provide the following environment variables:

- `DB_USERNAME`: Your database username (e.g., `hubskills`)
- `DB_PASSWORD`: Your database password

### Running with Maven

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.datasource.username=your_username --spring.datasource.password=your_password"
```

### Running with Java JAR

Build the project:
```bash
mvn clean install
```

Run the JAR:
```bash
java -jar target/Hubskills-backend-0.0.1-SNAPSHOT.jar --spring.datasource.username=your_username --spring.datasource.password=your_password
```
