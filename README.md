# Enterprise Commerce Microservices Platform

## Project Overview

An Enterprise E-Commerce Microservices Platform built with Java and Spring Boot. This project serves as a comprehensive demonstration of real-world enterprise architecture, microservices design patterns, and modern backend technologies.

## Architecture Diagram

```text
                         React / React Native
                                  |
                                  | HTTPS / REST
                                  v
                         +------------------+
                         |   API Gateway    |
                         +--------+---------+
                                  |
          +-----------------------+-----------------------+
          |                       |                       |
          v                       v                       v
   Auth Service            Product Service          User Service
          |                       |                       |
          v                       v                       v
       Auth DB                 Product DB              User DB

                                  |
                                  v
                           Cart Service
                                  |
                               Cart DB
                                  |
                                  v
                           Order Service
                           /           \
                          /             \
                       REST             Kafka
                        |                 |
                        v                 v
                Payment Service       Event Bus
                        |                 |
                        v          +------+-------+----------+
                 Payment Gateway  |      |       |          |
                                  v      v       v          v
                             Inventory  Order  Analytics Notification
                              Service   Service Service    Service
                                  |                         |
                                  v                         v
                             Inventory DB                RabbitMQ
                                                            |
                                                   +--------+--------+
                                                   |                 |
                                                   v                 v
                                             Email Worker      SMS Worker
```

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.x** (Spring Web, Spring Data JPA, Spring Security)
- **Spring Cloud 2023.x** (Gateway, Eureka, Config, OpenFeign)
- **Messaging:** Apache Kafka, RabbitMQ
- **Databases:** MySQL, Redis
- **Security:** JWT Authentication
- **DevOps:** Docker, Docker Compose
- **Testing:** JUnit 5, Mockito, Testcontainers

## Services Overview

1. **api-gateway**: Central entry point, routing, and security.
2. **config-server**: Centralized configuration management.
3. **discovery-server**: Eureka service registry.
4. **auth-service**: JWT authentication and user registration.
5. **user-service**: Customer profiles and addresses.
6. **product-service**: Product catalog management.
7. **cart-service**: Shopping cart management.
8. **order-service**: Core business workflow.
9. **payment-service**: Payment processing.
10. **inventory-service**: Stock management.
11. **notification-service**: Email and SMS dispatch.
12. **analytics-service**: Business metrics tracking.

## Database Architecture

We follow the "Database-per-Service" pattern to ensure loose coupling. Each service owns its schema:
- `auth_db`, `user_db`, `product_db`, `cart_db`, `order_db`, `payment_db`, `inventory_db`, `analytics_db`

## Communication Patterns

- **REST (Synchronous):** Used for immediate request/response needs (e.g., Order calling Payment).
- **Kafka (Event-Driven):** Used for business events indicating state changes (e.g., `OrderCreated`, `PaymentCompleted`).
- **RabbitMQ (Asynchronous Tasks):** Used for background job processing (e.g., sending emails).

## How to Run

To run the full infrastructure and services, you can use Docker Compose:

```bash
# Start infrastructure and all services
docker compose up -d

# Stop everything
docker compose down
```

For development, you can start the infrastructure only and run services from your IDE:

```bash
# To compile the complete platform
mvn clean install
```

## Documentation

- [ARCHITECTURE.md](ARCHITECTURE.md) - Deep dive into design decisions.
- Further API and component documentation will be added.
