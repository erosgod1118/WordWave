## Wordwave Application

### Technologies

- Java 1.8
- Spring Boot
- Spring Cloud
- Swagger
- Lombok
- Liquibase
- Maven 3.5.X
- MySQL 5.7.X

### Requirements

- JDK 1.8
- Any IDE of your choice (Lombok plugin required)

### Build application

- Navigate to project directory
- mvn clean install

### Run application

Eureka Server

- Navigate to eureka-server directory
- mvn spring-boot:run

Config Server

- Navigate to config-server directory
- mvn spring-boot:run

Services

- Navigate to \*\*-service directory directory
- mvn spring-boot:run

API-Gateaway

- Navigate to api-gateway directory
- mvn spring-boot:run

### Run db scripts

- Navigate to wordwave-db directory
- mvn install

### Swagger. REST API Documentation

- All Swagger Resources: http://localhost:7080/{service-name}/swagger-resources
- Swagger UI endpoint: http://localhost:7080/{service-name}/swagger-ui.html
- Swagger docs endpoint: http://localhost:7080/{service-name}/v2/api-docs

TODO: test comment that will neen to be removed
