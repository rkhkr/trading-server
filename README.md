# 📈 Trading Platform - Microservices Architecture

A robust, scalable simulated stock trading platform built using **Java Spring Boot** and **Microservices Architecture**. This project demonstrates enterprise-level patterns including **Centralized Authentication**, **Service Discovery**, and **Identity Propagation**.

## 🏗️ Architecture Diagram

This diagram illustrates the request flow. The **API Gateway** acts as the single entry point, handling security and routing.

```mermaid
graph TD
    Client[Client (Web/Mobile)] -->|HTTPS Request| Gateway[API Gateway <br/> Port: 8080]
    
    subgraph Infrastructure
    Eureka[Discovery Server (Eureka) <br/> Port: 8761]
    end
    
    subgraph Microservices
    Auth[Auth Service <br/> JWT Generation]
    Order[Order Service <br/> Trade Execution]
    Wallet[Wallet Service <br/> Balance Management]
    Portfolio[Portfolio Service <br/> Holdings]
    end

    Gateway -.->|Registers| Eureka
    Auth -.->|Registers| Eureka
    Order -.->|Registers| Eureka
    Wallet -.->|Registers| Eureka

    Gateway -->|1. Validate JWT & Extract Identity| Gateway
    Gateway -->|2. Forward with 'loggedInUser' Header| Order
    Gateway -->|2. Forward with 'loggedInUser' Header| Wallet
    Gateway -->|2. Forward with 'loggedInUser' Header| Portfolio
    
    Gateway -->|Login Request| Auth