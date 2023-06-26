# Recruitment Exercise - Backend Developer Strategic Imperatives

## Introduction
    For this project I created an API using Java with Springboot simulating a simple client management system.
    The API has the following endpoints:
    - GET /clients
    - GET /clients/{id}
    - POST /clients
    - PUT /clients/{id}
    - DELETE /clients/{id}
    This project also has unit tests for the service layer and integration tests for the controller layer.
    I used H2 as the database for this project, so you don't need to install any database to run it.
    I used Spring Data JPA to access the database.
    I used Swagger to document the API.
    I used Lombok to reduce boilerplate code.
    I used JUnit 5 and Mockito to create the tests.

## Requirements
    - Java 17
    - Maven
    
## How to run
    - Clone the repository
    - Run the command `mvn spring-boot:run` in the root folder of the project
    - The API will be available at http://localhost:8080

## How to use
    I used Swagger to document the API, so you can access the documentation at http://localhost:8080/swagger-ui.html
    You can also use Postman to test the API, the collection is available at the root folder of the project.
    You can also import OpenApi specification file at http://localhost:8080/v3/api-docs

## How to run tests
    - Run the command `mvn test` in the root folder of the project


