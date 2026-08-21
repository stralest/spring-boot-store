# Store API

A RESTful backend API for an online store built with Spring Boot.

The project provides functionality for managing products, categories, users, shopping carts, orders, authentication, and payments.

## Technologies

- Java 17
- Spring Boot 3.4.1
- Spring Security
- Spring Data JPA
- Hibernate
- MySQL
- Flyway
- MapStruct
- JWT
- Stripe
- Maven

## Features

- User registration and authentication
- JWT-based authentication
- Role-based authorization
- Product and category management
- Shopping cart management
- Order management
- User profiles and addresses
- Database migrations with Flyway
- DTO mapping with MapStruct
- Stripe payment integration
- Global exception handling
- Request logging

## Project Structure

The project is organized by features rather than by technical layers.

```text
src/main/java/com/codewithmosh/store/

├── auth/
├── admin/
├── user/
├── product/
├── category/
├── cart/
├── order/
├── payment/
├── config/
├── common/
└── filter/
```

Each feature contains the classes related to that functionality, such as controllers, services, repositories, mappers, entities, and DTOs.

# Database

The application uses MySQL as its database.

Flyway is used to manage database migrations. When the application starts, Flyway automatically applies any pending migrations.

The default local database configuration is:

```
Database: store_api
Host: localhost
Port: 3306
Username: root
```

Make sure MySQL is running before starting the application.

# Configuration

The application uses environment variables for sensitive configuration such as JWT and Stripe keys.

Create a .env file in the project root:

```
JWT_SECRET=your-jwt-secret
STRIPE_SECRET_KEY=your-stripe-secret-key
STRIPE_WEBHOOK_SECRET_KEY=your-stripe-webhook-secret
```

Do not commit the .env file to GitHub.

Running the Application

Clone the repository:

```
git clone <repository-url>
```

Navigate to the project directory:

```
cd store
```

Make sure MySQL is running and the required environment variables are configured.

Run the application with Maven:

```
Windows
.\mvnw spring-boot:run
```

Or build the project first:

```
.\mvnw clean package
```

The API runs on:

```
http://localhost:8080
API Documentation
```

When the application is running, Swagger UI is available at:

```
http://localhost:8080/swagger-ui/index.html
```

# Database Migrations

Database migrations are located in:

```
src/main/resources/db/migration
```

Flyway applies the migrations automatically when the application starts.

# Payments

The project includes Stripe integration for handling payments.

Stripe credentials must be provided through environment variables before using payment-related endpoints.

License

This project was created for learning and portfolio purposes.
