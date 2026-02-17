# Personal Management To-Do List

Detta är en Spring Boot-webbapplikation utvecklad som ett samarbetsprojekt. Applikationen fungerar som en central hubb för personlig organisation och består av flera oberoende CRUD-moduler.

## Live Demo
Applikationen finns publicerad på:
[https://intelligent-kerrie-jimmy-grafstrom-f39488fb.koyeb.app/](https://intelligent-kerrie-jimmy-grafstrom-f39488fb.koyeb.app/)

## Team & Ansvarsområden
* **Jimmy** - *Träningsloggen (Workout Session)*: Hanterar träningspass, varaktighet och intensitet.
* **Sedina** - *Matlistan (Grocery List)*: Hanterar inköpsartiklar, kvantitet och status.
* **Yunus** - *Studieplanen (Study Plan)*: Spårar akademiska uppgifter och deadlines.

## Funktioner
Applikationen erbjuder ett webbgränssnitt byggt med **Thymeleaf** där användare kan:
- **Skapa (Create):** Lägga till nya poster i respektive lista.
- **Läsa (Read):** Visa detaljerad information och listvyer.
- **Uppdatera (Update):** Redigera befintliga poster (t.ex. ändra status på ett träningspass eller en vara).
- **Radera (Delete):** Ta bort poster som inte längre behövs.

## Teknisk Stack
* **Java 21** & **Spring Boot 4.0.2**
* **Spring Data JPA** (PostgreSQL i produktion, H2 för lokal utveckling/test)
* **Thymeleaf** (Frontend-mallar)
* **Maven** (Byggverktyg)
* **Docker** & **GitHub Actions** (CI/CD för automatisk publicering till Docker Hub)

## Miljövariabler
För att köra applikationen i produktion (t.ex. på Koyeb) krävs följande miljövariabler:
* `DB_URL`: Anslutningssträng till PostgreSQL.
* `DB_USERNAME`: Databasanvändarnamn.
* `DB_PASSWORD`: Databaslösenord.

## Kom igång lokalt
1. Klona repot.
2. Bygg och kör projektet med Maven:
   ```bash
   ./mvnw spring-boot:run
   ```
3. Öppna webbläsaren på: `http://localhost:8080`

## Docker & Deployment
Projektet är konfigurerat för att automatiskt byggas och pushas till Docker Hub via GitHub Actions vid varje push till `main`. Koyeb är sedan konfigurerat för att automatiskt rulla ut den senaste imagen.

För att bygga imagen manuellt:
```bash
./mvnw package
docker build -t to-do-list .
```

## Testning
Projektet använder JUnit för tester. Testerna körs automatiskt i GitHub Actions innan Docker-imagen byggs.
```bash
./mvnw test
```
