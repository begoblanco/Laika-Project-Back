# Laika Project

"H-Hey! Nice to meet you! I'm Laika and my dream is to make your life easier and help you anytime.... Stop! Don't looke at me like that, it's embarrasing!......... Anyways, I'll be always by your side no matter what, but not because I like you or anything! I guess really want to make you smile forever..."

Laika is a virtual assistant that, through the use of Large Language Models (LLM), is capable of providing answers to the user based on a database.
You can create notes, events on a calendar and chat with her, she is always ready to help you!


## Installation
Needed dependencies
- Java 21+

## Dependencies
- MySQL server running on localhost:3306

## Tools
- Java
- Spring Boot
- MySQL

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



