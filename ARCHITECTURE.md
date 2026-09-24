# System Architecture

This document explains the architectural decisions behind the Enterprise Commerce Microservices Platform.

## 1. Microservices vs Monolith

The application is split into independently deployable units to demonstrate:
- **Independent Scaling:** Services with high load (like Product Service) can be scaled independently of others.
- **Fault Isolation:** A failure in the Analytics Service won't bring down the Order Service.
- **Technology Diversity:** Allows choosing the best tool for each specific domain if needed (though we standardise on Java/Spring Boot for this reference).

## 2. API Gateway

We use **Spring Cloud Gateway**.
- **Why?** It abstracts the internal microservices structure from the frontend. It handles cross-cutting concerns like CORS, routing, rate-limiting, and centralizes initial security checks (validating the JWT token).

## 3. Communication Patterns

Our architecture deliberately uses three different communication styles to demonstrate when each is appropriate:

### 3.1 REST (Synchronous)
- **Use Case:** "I need an answer right now to continue."
- **Example:** Order Service -> Payment Service. The Order Service cannot proceed without knowing if the payment was successful. We use Spring Cloud OpenFeign for this.

### 3.2 Apache Kafka (Event-Driven)
- **Use Case:** "Something happened in my domain, and others might care."
- **Example:** Payment Service publishes `PaymentCompleted`. The Inventory Service consumes this to reserve stock, and the Notification Service consumes it to send a receipt.
- **Why Kafka?** High throughput, persistent events, and the ability for multiple independent consumer groups to process the same events at their own pace.

### 3.3 RabbitMQ (Asynchronous Task Queues)
- **Use Case:** "Please do this job in the background."
- **Example:** Notification Service receives an event from Kafka and generates a concrete job (e.g., "Send Email to user@example.com"). It places this job on an `email.queue` in RabbitMQ.
- **Why RabbitMQ?** Excellent support for fine-grained routing, dead-letter queues, and retries for individual tasks that might fail (like connecting to an external email provider).

## 4. Database-per-Service

Each microservice manages its own database.
- **Why?** Prevents tight coupling. If the Order Service directly queries the Product Database, it bypasses the Product Service's business logic and creates a brittle dependency on the database schema.

## 5. Distributed Transactions (Saga Pattern)

We avoid 2-Phase Commit (2PC) distributed transactions, which cause locks and reduce availability.
- Instead, we use the **Choreography Saga Pattern** via Kafka events.
- If a step fails (e.g., `InventoryFailed` due to out-of-stock), the system triggers **compensating transactions** (e.g., Order Service listens to the failure event and issues a refund to the Payment Service, then sets the order status to `CANCELLED`).
