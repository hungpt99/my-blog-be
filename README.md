# Java Spring Boot Blog Platform (Spring Boot 3.1)

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

## Overview
This is a blog platform built with Java Spring Boot 3.1, providing a robust and scalable backend for managing posts, users, and comments.

## Included Technologies
- **Backend**: Spring Boot 3.1, Spring Security, Spring Data JPA, Hibernate, JWT Authentication
- **Database**: PostgreSQL, Redis (for caching)
- **API Documentation**: Swagger
- **Migrations**: Liquibase
- **Testing**: JUnit 5, Mockito
- **Dependency Management**: Maven
- **Containerization**: Docker, Docker Compose
- **Utilities**: Lombok, Spring Data Redis

## Features
- User authentication and authorization with JWT
- Blog post management (CRUD operations)
- Commenting system with nested replies
- User roles and permissions
- Redis caching for improved performance
- API documentation with Swagger
- Database versioning with Liquibase

## Requirements
- **Java 17**
- **Maven 3.9.3**
- **Docker 20.10.8**
- **Docker Compose 2.19.1**
- **PostgreSQL 13.11**
- **Redis 7.0.12**

## Run with Docker Compose
```bash
docker-compose up --build -d
```

## Install Dependencies
```bash
mvn clean install
```

## Run Project
```bash
mvn spring-boot:run
```

## Build Project
```bash
mvn clean package
```

## Skip Integration Tests
```bash
mvn clean install -DskipITs=true
```

## Source Code Structure
```
|-- src
|   |-- main
|   |   |-- java/com/example/blog
|   |   |   |-- config (Security, CORS, Redis, etc.)
|   |   |   |-- controller (REST API endpoints)
|   |   |   |-- service (Business logic)
|   |   |   |-- repository (Data access layer)
|   |   |   |-- model (Entities and DTOs)
|   |   |-- resources
|   |   |   |-- db/changelog (Liquibase migrations)
|   |   |   |-- application.yml (Configuration)
|-- docker-compose.yml (Docker setup for PostgreSQL, Redis, and application)
|-- pom.xml (Maven dependencies)
```

## API Endpoints
### Authentication
- `POST /auth/login` - User login
- `POST /auth/register` - User registration

### Blog Posts
- `GET /posts` - Get all posts
- `POST /posts` - Create a new post
- `PUT /posts/{id}` - Update a post
- `DELETE /posts/{id}` - Delete a post

### Comments
- `POST /posts/{postId}/comments` - Add a comment
- `GET /posts/{postId}/comments` - Get all comments for a post

## Contributing
Feel free to submit issues or pull requests to improve this project!

## License
This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).

