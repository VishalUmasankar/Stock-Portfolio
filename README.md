# Stock Portfolio Management System

This is a Spring Boot-based Stock Portfolio Management System that allows users to manage their investments, track their holdings, and get real-time updates. The project integrates with Swagger for seamless API documentation and testing.

---

## Project Objectives
- Manage user registration and authentication .
- Track stock holdings and portfolio performance.
- Enable buying and selling of stocks through the API.
- Set price alerts for specific stock symbols.
- Document and test APIs using Swagger UI.

---

## Features

- User Registration & Login
- Portfolio Management (Buy/Sell stocks)
- Alert System for stock price changes
- Database integration using Spring Data JPA
- Unit testing with JUnit
- Scheduled background tasks
- API documentation with Swagger UI

---

## Project Flow

1. **User** interacts via Swagger UI .
2. **Controller Layer** handles HTTP requests.
3. **Service Layer** processes business logic.
4. **Repository Layer** interacts with the database.
5. **Entity Layer** maps to DB tables.
6. **DTOs** carry structured data.
7. **Scheduler** performs timed tasks.
8. **Utilities** support reusable operations.
9. **Exception Handling** ensures clear errors.
10. **Swagger Config** provides UI for API testing.

---

## Tech Stack

| Layer       | Technology                     |
|-------------|--------------------------------|
| Backend     | Spring Boot                    |
| ORM         | Spring Data JPA (Hibernate)    |
| DB          | MySQL (configurable)      |
| Docs        | Swagger (Springfox)            |
| Build Tool  | Maven                          |
| Testing     | JUnit                          |

---

## Project Structure

```
Stock-Portfolio/
├── src/
│   ├── main/java/com/stockportfolio/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── exception/
│   │   ├── config/
│   │   ├── util/
│   │   └── scheduler/
│   └── resources/
├── test/
│   └── java/com/stockportfolio/
├── pom.xml
└── README.md
```

---

## API Documentation

Once the application is running, access the Swagger UI at:

```
http://localhost:8080/swagger-ui/index.html
```

1. **Mail Controller**
```
POST /mail/send — Send an email.
```
2. **User Controller**
```
POST /api/register — Register a new user.
POST /api/login — User login.
POST /api/holdings/sell — Sell holdings.
POST /api/holdings/buy — Buy holdings.
GET /api/holdings/portfolio/{userId} — Get portfolio details of a user.
GET /api/activity/user/{userId} — Get user activity.
PUT /api/holdings/update — Update alert values.
DELETE /api/user/{id} — Delete the user.
```

3. **Alert Controller**
```
POST /alert/update-price/{id} — Update alert price by ID.
POST /alert/toggle/{id} — Enable or disable an alert by ID.
```
## How to Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/stock-portfolio.git
   cd stock-portfolio
   ```

2. **Build the project**
   ```bash
   ./mvnw clean install
   ```

3. **Run the app**
   ```bash
   ./mvnw spring-boot:run
   ```

---

## Running Tests

Use your choice IDE, such as Eclipse or IntelliJ, to run the JUnit test cases included in the src/test/java directory.

---


## License

This project is for educational purposes only. Kindly contact the authors before any Commercial use. 
---

## Authors

- [Vishal Umasankar U](https://github.com/VishalUmasankar)
- [A Harish](https://github.com/AHarish1234)
- [Karthikchidambaram](https://github.com/Iornspider12)
- [Sudarsan S](https://github.com/sud0410)
- [SibiSugan K](https://github.com/Sibi3002)
- [P Jayarama Surya](https://github.com/Surya-Pulya)
- [Utkarsh Upathyay](https://github.com/UTKARSHutkarsh)
