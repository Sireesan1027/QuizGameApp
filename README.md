# Game Score Management API

## Features:
- Store game scores for users.
- Retrieve the leaderboard showing the highest score per user.
- Each user can play multiple times, but only their highest score is shown.

## Tech Stack:
- Java 17+
- Spring Boot 2.7+
- MongoDB (local or cloud)
- Swagger for API documentation
- Log4j2 for logging

## Setup:

### 1. Clone the Repository:
```bash
git clone https://github.com/Sireesan1027/QuizGameApp.git
cd quiz-game-app
```

### 2. Configure MongoDB:
In `src/main/resources/application.yml`, set your MongoDB URI:
```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/gamingapp  # Adjust as needed
```

### 3. Build and Run:
```bash
mvn clean install
mvn spring-boot:run
```

## API Endpoints:

### 1. **POST /api/scores**
- **Description**: Store a new score for a user.
- **Request Body**:
```json
{
  "email": "user@example.com",
  "username": "User1",
  "score": 5000
}
```
- **Response**:
```json
{
  "gameId": "abcd1234",
  "email": "user@example.com",
  "username": "User1",
  "score": 5000,
  "timestamp": "2024-11-30T12:34:56.789Z"
}
```

### 2. **GET /api/scores/leaderboard**
- **Description**: Retrieve the leaderboard with the highest score for each user.
- **Response**:
```json
[
  {
    "email": "user@example.com",
    "username": "User1",
    "score": 5000
  },
  {
    "email": "anotheruser@example.com",
    "username": "AnotherUser",
    "score": 3000
  }
]
```

## Swagger UI:
Access the API documentation and interact with the API at:
```
http://localhost:8080/swagger-ui.html
```

## Logging:
Logs will be printed to the console. For production use, consider configuring Log4j2 for file logging.

## License:
This project is licensed under the MIT License.
