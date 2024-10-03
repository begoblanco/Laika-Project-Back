# Laika Project

## Installation
Needed dependencies
- Java 21+

## Dependencies:
- MySQL server running on localhost:3306

## Environment variables

| Variable               | Value                  |   |   |   |   |   |   |   |   |
|------------------------|------------------------|---|---|---|---|---|---|---|---|
| DATABASE_DB_NAME       | secret                 |   |   |   |   |   |   |   |   |
| DATABASE_USERNAME      | secret                 |   |   |   |   |   |   |   |   |
| DATABASE_PASSWORD      | secret                 |   |   |   |   |   |   |   |   |
| SPRING_PROFILES_ACTIVE | mysql                  |   |   |   |   |   |   |   |   |
| API_ENDPOINT           | /api/v1                |   |   |   |   |   |   |   |   |
| CHAT_API_ENDPOINT      | http://localhost:5000  |   |   |   |   |   |   |   |   |
| SECRET_KEY             | secret                 |   |   |   |   |   |   |   |   |


## Usage
```bash
mvnw.cmd spring-boot:run
```

## Port
`8080`

## Paths

### auth

| Path                       | Method | Description                                               |   |   |   |   |   |   |   |   |
|----------------------------|--------|-----------------------------------------------------------|---|---|---|---|---|---|---|---|
| API_ENDPOINT/auth/login    | POST   | Login user with username and password. Returns JWT token. |   |   |   |   |   |   |   |   |
| API_ENDPOINT/auth/register | POST   | Register a user with username and password.               |   |   |   |   |   |   |   |   |



