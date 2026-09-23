# Enterprise Commerce Microservices Platform

## 1. Project Overview

Build a production-style **Enterprise E-Commerce Microservices Platform** using Java and Spring Boot.

This project is specifically designed to demonstrate and practice:

* Java
* Spring Boot
* Spring Security
* JWT authentication
* Spring Cloud Gateway
* Eureka Service Discovery
* Spring Cloud Config
* REST API communication
* OpenFeign
* Apache Kafka
* RabbitMQ
* MySQL
* Redis
* Docker
* Docker Compose
* OpenAPI / Swagger
* JUnit
* Mockito
* Testcontainers
* Distributed transactions
* Event-driven architecture
* Asynchronous processing
* Error handling
* Resilience
* Logging
* Monitoring
* Observability

The application must represent a realistic enterprise system rather than a collection of unrelated demos.

---

# 2. CRITICAL AI INSTRUCTIONS

The AI implementing this project MUST follow these rules.

## Rule 1 — Do not create a monolith

Do NOT put all business logic into one Spring Boot application.

The project must contain independently deployable Spring Boot services.

Each business service must have:

* Its own source code
* Its own `pom.xml`
* Its own Spring Boot application
* Its own configuration
* Its own database/schema ownership
* Its own REST endpoints where applicable
* Its own Docker configuration where applicable

---

## Rule 2 — One repository is allowed

The entire project can be a **monorepo**.

Example:

```text
Enterprise-Commerce-Microservices/
    auth-service/
    user-service/
    product-service/
    order-service/
    payment-service/
```

One Git repository does NOT make the system a monolith.

The services must still be independently buildable and deployable.

---

# 3. Architecture

Use this high-level architecture:

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

---

# 4. Services

Create the following services.

```text
1. api-gateway
2. config-server
3. discovery-server
4. auth-service
5. user-service
6. product-service
7. cart-service
8. order-service
9. payment-service
10. inventory-service
11. notification-service
12. analytics-service
```

---

# 5. Infrastructure

Create infrastructure configuration for:

```text
MySQL
Redis
Kafka
Zookeeper or Kafka's supported controller mode
RabbitMQ
```

Prefer Docker Compose for local development.

The complete infrastructure should be startable with:

```bash
docker compose up -d
```

and stoppable with:

```bash
docker compose down
```

---

# 6. Technology Stack

Use:

```text
Java 17
Spring Boot
Spring Web
Spring Data JPA
Spring Security
JWT
Spring Cloud Gateway
Spring Cloud Netflix Eureka
Spring Cloud Config
Spring Cloud OpenFeign
Apache Kafka
RabbitMQ
MySQL
Redis
Docker
Docker Compose
Maven
Swagger / OpenAPI
JUnit 5
Mockito
Testcontainers
Lombok
Bean Validation
```

Use compatible versions throughout the entire project.

Do not mix incompatible Spring Boot and Spring Cloud versions.

---

# 7. Root Project Structure

Create:

```text
Enterprise-Commerce-Microservices/
│
├── PROJECT_GUIDE.md
├── README.md
├── ARCHITECTURE.md
├── docker-compose.yml
├── pom.xml
│
├── infrastructure/
│   ├── mysql/
│   ├── kafka/
│   ├── rabbitmq/
│   └── redis/
│
├── config-repo/
│
├── api-gateway/
├── config-server/
├── discovery-server/
│
├── auth-service/
├── user-service/
├── product-service/
├── cart-service/
├── order-service/
├── payment-service/
├── inventory-service/
├── notification-service/
└── analytics-service/
```

---

# 8. Root Maven Project

The root Maven project should act as an aggregator.

It should NOT contain the business logic of the application.

Each service must remain independently buildable.

Example:

```text
mvn clean install
```

should build the complete project.

Each individual service must also be buildable independently.

Example:

```bash
cd order-service
mvn clean package
```

---

# 9. API Gateway

Technology:

```text
Spring Cloud Gateway
```

Responsibilities:

* Route requests
* JWT validation
* Authentication filtering
* Authorization filtering
* CORS
* Request logging
* Rate limiting where appropriate
* Central entry point for frontend

Example routes:

```text
/api/auth/**       -> auth-service
/api/users/**      -> user-service
/api/products/**   -> product-service
/api/cart/**       -> cart-service
/api/orders/**     -> order-service
/api/payments/**   -> payment-service
/api/inventory/**  -> inventory-service
```

Frontend should normally communicate with the API Gateway rather than directly accessing internal services.

---

# 10. Discovery Server

Use:

```text
Eureka Server
```

Services should register themselves.

Expected registered services:

```text
API-GATEWAY
AUTH-SERVICE
USER-SERVICE
PRODUCT-SERVICE
CART-SERVICE
ORDER-SERVICE
PAYMENT-SERVICE
INVENTORY-SERVICE
NOTIFICATION-SERVICE
ANALYTICS-SERVICE
```

Do not hardcode service hostnames where service discovery can be used.

---

# 11. Config Server

Use Spring Cloud Config.

Centralize configuration for:

```text
Database
Kafka
RabbitMQ
Redis
Eureka
JWT
Service ports
Logging
External API configuration
```

Keep secrets out of Git.

Use environment variables for sensitive credentials.

Example:

```text
MYSQL_USERNAME
MYSQL_PASSWORD
JWT_SECRET
RABBITMQ_USERNAME
RABBITMQ_PASSWORD
```

---

# 12. Auth Service

Responsibilities:

```text
User registration
User login
JWT access token
Refresh token
Password hashing
Role management
Permission management
```

Roles:

```text
CUSTOMER
ADMIN
SELLER
```

Endpoints:

```http
POST /api/auth/register
POST /api/auth/login
POST /api/auth/refresh
POST /api/auth/logout
```

Use:

```text
Spring Security
BCrypt
JWT
```

Never store plain-text passwords.

---

# 13. User Service

Responsibilities:

```text
Customer profile
Personal information
Addresses
Phone
Email
Preferences
```

Example endpoints:

```http
GET /api/users/{id}
PUT /api/users/{id}
DELETE /api/users/{id}

POST /api/users/{id}/addresses
GET /api/users/{id}/addresses
PUT /api/users/{id}/addresses/{addressId}
DELETE /api/users/{id}/addresses/{addressId}
```

Database:

```text
user_db
```

---

# 14. Product Service

Responsibilities:

```text
Product
Category
Brand
Price
Product image metadata
Product status
Product search/filter
```

Endpoints:

```http
GET /api/products
GET /api/products/{id}

POST /api/products
PUT /api/products/{id}
DELETE /api/products/{id}

GET /api/products/category/{categoryId}
GET /api/products/search
```

Support:

```text
Pagination
Sorting
Filtering
Validation
```

Database:

```text
product_db
```

---

# 15. Cart Service

Responsibilities:

```text
Create cart
Get cart
Add item
Update quantity
Remove item
Clear cart
Calculate subtotal
```

Endpoints:

```http
GET /api/cart
POST /api/cart/items
PUT /api/cart/items/{itemId}
DELETE /api/cart/items/{itemId}
DELETE /api/cart
```

Database:

```text
cart_db
```

Cart must belong to a user.

---

# 16. Order Service

This is the primary business workflow service.

Responsibilities:

```text
Create order
Get order
Cancel order
Update order status
Order history
Order totals
```

Order statuses:

```text
CREATED
PAYMENT_PENDING
PAID
INVENTORY_PENDING
CONFIRMED
SHIPPED
DELIVERED
CANCELLED
FAILED
```

Endpoints:

```http
POST /api/orders
GET /api/orders/{id}
GET /api/orders
POST /api/orders/{id}/cancel
```

Database:

```text
order_db
```

---

# 17. Payment Service

Responsibilities:

```text
Create payment
Process payment
Check payment status
Handle payment success
Handle payment failure
Refund payment
```

Endpoints:

```http
POST /api/payments
GET /api/payments/{id}
POST /api/payments/{id}/refund
```

Database:

```text
payment_db
```

For the initial version, implement a simulated payment provider.

Later allow integration with:

```text
Razorpay
Stripe
```

---

# 18. Inventory Service

Responsibilities:

```text
Product stock
Warehouse stock
Reserve stock
Release stock
Deduct stock
Restock
```

Example:

```text
PRODUCT_ID
TOTAL_STOCK
RESERVED_STOCK
AVAILABLE_STOCK
```

Database:

```text
inventory_db
```

Endpoints:

```http
GET /api/inventory/{productId}
POST /api/inventory/reserve
POST /api/inventory/release
POST /api/inventory/restock
```

---

# 19. Kafka Architecture

Kafka must be used for **business events**, not for every communication.

Create topics:

```text
order-created
payment-completed
payment-failed
inventory-reserved
inventory-released
order-confirmed
order-cancelled
order-shipped
```

Use JSON event payloads.

Example:

```json
{
  "eventId": "uuid",
  "eventType": "ORDER_CREATED",
  "orderId": 1001,
  "userId": 55,
  "timestamp": "2026-09-23T10:30:00Z"
}
```

---

# 20. Kafka Producer/Consumer Rules

Order Service should publish:

```text
OrderCreated
OrderCancelled
```

Payment Service should publish:

```text
PaymentCompleted
PaymentFailed
```

Inventory Service should publish:

```text
InventoryReserved
InventoryReleased
```

Notification Service can consume relevant events.

Analytics Service should consume business events.

Do not make every service consume every topic unnecessarily.

---

# 21. Kafka Real-World Workflow

Implement:

```text
Customer
    |
    v
Order Service
    |
    | REST
    v
Payment Service
    |
    | Payment success
    v
Kafka
    |
    +-------------> Order Service
    |
    +-------------> Inventory Service
    |
    +-------------> Analytics Service
    |
    +-------------> Notification Service
```

The purpose is to demonstrate **event-driven architecture**.

---

# 22. RabbitMQ Architecture

RabbitMQ should be used primarily for **asynchronous task/message processing**.

Create queues:

```text
email.queue
sms.queue
invoice.queue
```

Example:

```text
Notification Service
        |
        v
     RabbitMQ
        |
        +----> email.queue
        |
        +----> sms.queue
        |
        +----> invoice.queue
```

---

# 23. RabbitMQ Email Workflow

When an order is confirmed:

```text
Order Service
      |
      v
Kafka
      |
      v
Notification Service
      |
      v
RabbitMQ
      |
      v
email.queue
      |
      v
Email Worker
      |
      v
Email Provider
```

Implement:

```text
Retry
Dead Letter Queue
Acknowledgement
Failure handling
```

This is required because the project is intended to demonstrate real enterprise messaging concepts.

---

# 24. REST Communication

Use REST for synchronous operations.

Examples:

```text
Frontend -> API Gateway
API Gateway -> Service

Order Service -> Payment Service
Order Service -> Product Service
Cart Service -> Product Service
```

Example:

```text
Order Service
      |
      | POST /api/payments
      v
Payment Service
      |
      v
Payment Response
```

The caller should wait for the response when immediate information is required.

---

# 25. OpenFeign

Use Spring Cloud OpenFeign where appropriate for internal synchronous service-to-service communication.

Example:

```java
@FeignClient(name = "payment-service")
public interface PaymentClient {

    @PostMapping("/api/payments")
    PaymentResponse createPayment(
        @RequestBody PaymentRequest request
    );
}
```

Do not use Feign for asynchronous event processing.

Use Kafka/RabbitMQ for those scenarios.

---

# 26. REST vs Kafka vs RabbitMQ

The project must clearly demonstrate the difference.

## REST

Use when:

```text
"I need an immediate response."
```

Example:

```text
Order -> Payment
```

---

## Kafka

Use when:

```text
"Something happened and other services may need to react."
```

Example:

```text
PaymentCompleted
```

Consumers:

```text
Order Service
Inventory Service
Notification Service
Analytics Service
```

---

## RabbitMQ

Use when:

```text
"This job/message needs to be processed asynchronously."
```

Examples:

```text
Send email
Send SMS
Generate invoice
```

---

# 27. Notification Service

Responsibilities:

```text
Email
SMS
Push notification
Notification history
```

Consume Kafka events such as:

```text
OrderCreated
PaymentCompleted
OrderConfirmed
OrderShipped
OrderCancelled
```

Convert them into appropriate notification jobs.

Publish jobs to RabbitMQ.

---

# 28. Analytics Service

Consume Kafka events.

Track:

```text
Orders
Revenue
Payments
Product sales
Cancellations
Customer activity
```

Endpoints:

```http
GET /api/analytics/orders
GET /api/analytics/revenue
GET /api/analytics/products/top
GET /api/analytics/payments
```

Database:

```text
analytics_db
```

---

# 29. Database Per Service

Follow the microservice database ownership principle.

Use:

```text
auth_db
user_db
product_db
cart_db
order_db
payment_db
inventory_db
analytics_db
```

A service must NOT directly query another service's database.

Incorrect:

```text
Order Service
     |
     v
product_db
```

Correct:

```text
Order Service
     |
     | REST / event
     v
Product Service
     |
     v
product_db
```

---

# 30. Redis

Use Redis for:

```text
Product caching
Session-related data where appropriate
Frequently accessed information
Rate limiting
Temporary data
```

Example:

```text
GET product/1001

        |
        v

Redis cache

        |
    cache miss
        |
        v

Product Service
        |
        v

MySQL
```

Implement cache invalidation when product data changes.

---

# 31. Distributed Transaction Strategy

Do NOT attempt to create one database transaction spanning all services.

Instead demonstrate a **Saga-style workflow**.

Example:

```text
Create Order
      |
      v
Payment
      |
      v
Inventory Reservation
      |
      v
Order Confirmation
```

If inventory reservation fails:

```text
Inventory Failed
      |
      v
Order Service
      |
      v
Cancel / compensate payment
```

Document the compensation flow.

---

# 32. Global Error Handling

Each service should have:

```text
@ControllerAdvice
```

Create a standard error response:

```json
{
  "timestamp": "2026-09-23T10:30:00Z",
  "status": 404,
  "error": "PRODUCT_NOT_FOUND",
  "message": "Product not found",
  "path": "/api/products/1001",
  "traceId": "abc-123"
}
```

Do not expose sensitive internal exception details.

---

# 33. Validation

Use:

```text
@NotNull
@NotBlank
@NotEmpty
@Email
@Size
@Min
@Max
@Positive
```

Validate DTOs rather than relying only on entity validation.

---

# 34. DTO Architecture

Do not expose JPA entities directly from REST controllers.

Use:

```text
Request DTO
Response DTO
Entity
Mapper
```

Example:

```text
CreateProductRequest
ProductResponse
ProductEntity
ProductMapper
```

---

# 35. Standard Service Structure

Use this pattern:

```text
controller/
service/
service/impl/
repository/
entity/
dto/
mapper/
exception/
config/
client/
event/
messaging/
```

Do not create unnecessary layers when they provide no value.

Keep business logic out of controllers.

---

# 36. Security

Implement:

```text
JWT
Spring Security
Role Based Access Control
```

Example:

```text
CUSTOMER
    |
    +--> View products
    +--> Manage cart
    +--> Create order

ADMIN
    |
    +--> Manage products
    +--> Manage inventory
    +--> View analytics

SELLER
    |
    +--> Manage own products
```

Do not trust role information sent directly by the client.

---

# 37. Swagger / OpenAPI

Every business service should expose OpenAPI documentation.

Document:

```text
Endpoints
Request body
Response
Authentication
Error responses
Parameters
```

API Gateway documentation should be documented separately.

---

# 38. Testing

Every service must contain tests.

Implement:

```text
Unit tests
Controller tests
Service tests
Repository tests where useful
Integration tests
Kafka tests
RabbitMQ tests
```

Use:

```text
JUnit 5
Mockito
Spring Boot Test
MockMvc
Testcontainers
```

Do not consider a service complete without tests.

---

# 39. Docker

Every service should have a Dockerfile.

Example:

```text
FROM eclipse-temurin:17-jre

COPY target/app.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
```

The exact base image can be changed if required by the selected Java/Spring versions.

---

# 40. Docker Compose

Create a root:

```text
docker-compose.yml
```

It should be capable of running:

```text
MySQL
Redis
Kafka
RabbitMQ
Config Server
Discovery Server
API Gateway
Business Services
```

Use health checks where practical.

Services should start in a controlled dependency order.

---

# 41. Observability

Implement structured logging.

Every request should have a correlation/trace ID where practical.

Logs should help trace:

```text
Frontend request
    ↓
Gateway
    ↓
Order Service
    ↓
Payment Service
    ↓
Kafka
    ↓
Inventory
```

Later add:

```text
Prometheus
Grafana
OpenTelemetry
```

---

# 42. Actuator

Enable Spring Boot Actuator.

Useful endpoints:

```text
/actuator/health
/actuator/info
/actuator/metrics
```

Do not expose sensitive actuator endpoints publicly without security.

---

# 43. Complete Business Scenario

The application must support this complete flow:

```text
1. Customer registers
        ↓
2. Customer logs in
        ↓
3. JWT generated
        ↓
4. Customer browses products
        ↓
5. Customer adds products to cart
        ↓
6. Customer creates order
        ↓
7. Order Service validates order
        ↓
8. Order Service calls Payment Service through REST
        ↓
9. Payment is processed
        ↓
10. PaymentCompleted event published to Kafka
        ↓
11. Inventory Service consumes event
        ↓
12. Inventory reserves stock
        ↓
13. InventoryReserved event published
        ↓
14. Order Service confirms order
        ↓
15. OrderConfirmed event published
        ↓
16. Notification Service consumes event
        ↓
17. Notification Service sends email job to RabbitMQ
        ↓
18. Email Worker consumes queue
        ↓
19. Confirmation email sent
        ↓
20. Analytics Service records business events
```

---

# 44. Failure Scenarios

The project must also demonstrate failures.

## Payment failure

```text
Order
 ↓
Payment
 ↓
FAILED
 ↓
Kafka
 ↓
Order
 ↓
CANCELLED
```

---

## Inventory failure

```text
Payment SUCCESS
       ↓
Inventory
       ↓
OUT OF STOCK
       ↓
Compensation
       ↓
Refund Payment
       ↓
Cancel Order
```

---

## Email failure

```text
Notification
      ↓
RabbitMQ
      ↓
Email Worker
      ↓
Provider Failure
      ↓
Retry
      ↓
Retry
      ↓
Dead Letter Queue
```

---

# 45. Kafka Requirements

Demonstrate:

```text
Producer
Consumer
Topic
Partition
Consumer Group
Offset
Serialization
Deserialization
Error handling
Retry
Dead Letter Topic
```

Also demonstrate why multiple consumers can independently consume an event.

---

# 46. RabbitMQ Requirements

Demonstrate:

```text
Producer
Exchange
Queue
Binding
Routing key
Consumer
Acknowledgement
Retry
Dead Letter Exchange
Dead Letter Queue
```

Explain the difference between Kafka and RabbitMQ in the documentation.

---

# 47. Required Documentation

Create these files:

```text
README.md
ARCHITECTURE.md
API_DOCUMENTATION.md
KAFKA_GUIDE.md
RABBITMQ_GUIDE.md
REST_COMMUNICATION.md
DATABASE_DESIGN.md
SECURITY.md
DOCKER_GUIDE.md
TESTING_GUIDE.md
TROUBLESHOOTING.md
```

The documentation should explain not only HOW something was implemented, but WHY it was implemented that way.

---

# 48. README Requirements

The root README must contain:

```text
Project overview
Architecture diagram
Technology stack
Services
Database architecture
REST communication
Kafka communication
RabbitMQ communication
How to run
Docker commands
API documentation
Testing
Common errors
Future improvements
```

---

# 49. Implementation Order

Do NOT attempt to create everything simultaneously.

Implement in phases.

## Phase 1 — Root Setup

Create:

```text
Root Maven project
README
PROJECT_GUIDE
Architecture documentation
Docker Compose skeleton
```

---

## Phase 2 — Infrastructure

Set up:

```text
MySQL
Redis
Kafka
RabbitMQ
```

Verify each independently.

---

## Phase 3 — Service Discovery

Implement:

```text
discovery-server
```

Then register:

```text
auth-service
user-service
product-service
```

---

## Phase 4 — Config Server

Implement:

```text
config-server
```

Move common configuration into config repository.

---

## Phase 5 — Authentication

Implement:

```text
auth-service
user-service
JWT
Spring Security
```

Verify login and authorization.

---

## Phase 6 — Product and Cart

Implement:

```text
product-service
cart-service
```

Use REST communication.

---

## Phase 7 — Gateway

Implement:

```text
api-gateway
```

Route all external API requests through the gateway.

---

## Phase 8 — Order and Payment

Implement:

```text
order-service
payment-service
```

Use REST/OpenFeign for:

```text
Order -> Payment
```

---

## Phase 9 — Kafka

Implement:

```text
OrderCreated
PaymentCompleted
PaymentFailed
InventoryReserved
OrderConfirmed
```

Verify producers and consumers independently.

---

## Phase 10 — Inventory

Implement:

```text
inventory-service
```

Consume Kafka events.

Implement reservation and compensation logic.

---

## Phase 11 — RabbitMQ

Implement:

```text
notification-service
```

and:

```text
Email Worker
SMS Worker
```

Use RabbitMQ queues.

---

## Phase 12 — Analytics

Implement:

```text
analytics-service
```

Consume Kafka events and generate reports.

---

## Phase 13 — Redis

Add caching and appropriate invalidation.

---

## Phase 14 — Testing

Implement unit, integration, messaging and end-to-end tests.

---

## Phase 15 — Docker

Containerize all services.

Make the entire system runnable with:

```bash
docker compose up -d
```

---

## Phase 16 — Observability

Add:

```text
Actuator
Metrics
Correlation IDs
Structured logs
Prometheus
Grafana
```

---

# 50. AI Development Rules

When implementing the project:

1. Do not skip architecture documentation.
2. Do not create fake microservices that have no independent responsibility.
3. Do not use Kafka where a simple REST call is clearly more appropriate.
4. Do not use RabbitMQ where a Kafka business event is clearly more appropriate.
5. Do not use REST for every communication just because it is easier.
6. Explain the communication decision for each service interaction.
7. Do not share database tables between services.
8. Do not expose entities directly through APIs.
9. Use DTOs.
10. Use global exception handling.
11. Validate all external input.
12. Never store plain-text passwords.
13. Never commit secrets.
14. Write tests as each service is implemented.
15. Keep each service independently buildable.
16. Keep each service independently deployable.
17. Do not mark a phase complete until it can actually run.
18. Do not silently invent missing configuration.
19. If a dependency/version is incompatible, fix the compatibility rather than bypassing it.
20. Prefer clean, production-style code over unnecessarily complex code.

---

# 51. Definition of Done

The project is considered complete only when:

```text
[ ] All services compile
[ ] All services start successfully
[ ] Eureka registration works
[ ] Config Server works
[ ] API Gateway works
[ ] JWT authentication works
[ ] Product APIs work
[ ] Cart APIs work
[ ] Order APIs work
[ ] Payment APIs work
[ ] Inventory APIs work
[ ] REST communication works
[ ] OpenFeign works
[ ] Kafka producer works
[ ] Kafka consumers work
[ ] Kafka events are persisted/processed correctly
[ ] RabbitMQ producer works
[ ] RabbitMQ consumer works
[ ] Retry works
[ ] Dead-letter handling works
[ ] Notification flow works
[ ] Analytics flow works
[ ] Redis caching works
[ ] Database ownership is respected
[ ] Failure scenarios work
[ ] Compensation workflow works
[ ] Unit tests exist
[ ] Integration tests exist
[ ] Docker builds work
[ ] Docker Compose starts the complete system
[ ] Swagger/OpenAPI works
[ ] Actuator works
[ ] Logging works
[ ] Documentation is complete
```

---

# 52. Final Architecture Goal

The final system should demonstrate all three communication patterns clearly:

```text
                    MICROSERVICES
                         |
          +--------------+--------------+
          |              |              |
          v              v              v
        REST           KAFKA        RABBITMQ
          |              |              |
    Synchronous      Events          Queues
    request/         /streaming      /jobs
    response
```

The project should make it obvious why each technology exists.

## REST

```text
"I need an answer now."
```

## Kafka

```text
"Something happened.
Other services may need to react."
```

## RabbitMQ

```text
"This job/message needs to be processed asynchronously."
```

---

# 53. Expected Learning Outcome

After completing this project, I should be able to explain and demonstrate:

```text
Monolith vs Microservices
Modular Monolith
Microservice boundaries
API Gateway
Service Discovery
Config Server
REST
OpenFeign
Kafka
RabbitMQ
Event-driven architecture
Message queues
Consumer groups
Partitions
Retries
Dead Letter Queues
Database per service
Saga pattern
Distributed transactions
JWT
RBAC
Redis
Docker
Testing
Observability
Production deployment
```

The implementation should prioritize **understanding the architecture and communication decisions**, not merely making the application run.

---

# 54. First Task

Start with **Phase 1 only**.

Do not implement all services immediately.

First create:

```text
Enterprise-Commerce-Microservices/
├── PROJECT_GUIDE.md
├── README.md
├── ARCHITECTURE.md
├── docker-compose.yml
├── pom.xml
└── infrastructure/
```

Then explain the generated structure and wait for the next implementation phase.

When proceeding to the next phase, preserve all previously established architecture and conventions.
