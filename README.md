# Personal Management To-Do List

This is a Spring Boot web application developed as a collaborative project. The application serves as a centralized hub for personal organization, consisting of several independent CRUD modules.

## Live Demo
The application is published at:
[https://intelligent-kerrie-jimmy-grafstrom-f39488fb.koyeb.app/](https://intelligent-kerrie-jimmy-grafstrom-f39488fb.koyeb.app/)

## Team & Responsibilities
* **Jimmy** - *Exercise Log (Workout Session)*: Handles workout sessions, duration, and intensity.
* **Sedina** - *Grocery List*: Manages shopping items, quantities, and status.
* **Yunus** - *Study Plan*: Tracks academic tasks and deadlines.

## Features
The application provides a web interface built with **Thymeleaf** where users can:
- **Create:** Add new entries to each respective list.
- **Read:** View detailed information and list views.
- **Update:** Edit existing entries (e.g., changing the status of a workout or an item).
- **Delete:** Remove entries that are no longer needed.

## Technical Stack
* **Java 21** & **Spring Boot 4.0.2**
* **Spring Data JPA** (PostgreSQL in production, H2 for local development/testing)
* **Thymeleaf** (Frontend templates)
* **Maven** (Build tool)
* **Docker** & **GitHub Actions** (CI/CD for automatic publishing to Docker Hub)

## Environment Variables
To run the application in production (e.g., on Koyeb), the following environment variables are required:
* `DB_URL`: PostgreSQL connection string.
* `DB_USERNAME`: Database username.
* `DB_PASSWORD`: Database password.

When cloning this repository make sure to open the settings for environment variables in your IDE, and use the variables above to connect to your own database environment.  
H2-database is used for testing. The '.properties' file for testing is already preconfigured.

## Getting Started Locally
1. Clone the repository.
2. Build and run the project using Maven:
   ```bash
   ./mvnw spring-boot:run
   ```
3. Open your browser at: `http://localhost:8080`

## Docker & Deployment
The project is configured to automatically build and push a Docker image to Docker Hub via GitHub Actions on every push to `main`. Koyeb is then configured to automatically deploy the latest image.

To build the image manually:
```bash
./mvnw package
docker build -t to-do-list .
```

## Testing
The project uses JUnit and Mockito for testing. Tests are automatically executed in GitHub Actions before the Docker image is built.
```bash
./mvnw test
```
