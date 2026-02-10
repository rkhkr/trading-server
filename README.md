# 📈 Trading Server - Microservices Architecture

A robust, scalable simulated stock trading server app built using **Java Spring Boot** and **Microservices Architecture**. This project demonstrates enterprise-level patterns including **Centralized Authentication**, **Service Discovery**, and **Identity Propagation**.

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
	
📂 Project Hierarchy
	
	trading-platform/
	├── discovery-server/   # Eureka Service Registry (Port 8761)
	├── api-gateway/        # Spring Cloud Gateway (Port 8080)
	├── auth-service/       # Identity Management & JWT (Port 9000)
	├── order-service/      # Buy/Sell Logic & History (Port 8081)
	├── wallet-service/     # User Funds Management (Port 8082)
	└── portfolio-service/  # Stock Holdings Tracker (Port 8083)
	
🚀 Services Overview

	1. Discovery Server (Eureka)
		Port: 8761
		Role: The "Phonebook" of the architecture. All services register here.
		Why: Enables services to find each other dynamically without hardcoding URLs.

	2. API Gateway
		Port: 8080
		Role: The single entry point for all traffic (The "Bouncer").
		Key Features:
			Security: Implements a global AuthenticationFilter.
			JWT Validation: Validates the signature of incoming tokens.
			Identity Propagation: Extracts the username from the token and injects it as a secure header (loggedInUser) before forwarding the request to internal services.

	3. Auth Service
		Port: 9000
		Role: Handles user registration and login.
		Tech: Spring Security, JWT (JJWT library).
		Function: Verifies credentials and issues signed JWT Tokens containing the user's identity.

	4. Order Service
		Port: 8081
		Role: Core trading logic.
		Flow:
			Accepts trade requests (Buy/Sell).
			Validates orders against current market prices (simulated).
			Synchronously communicates with Wallet Service to deduct funds.
			Synchronously communicates with Portfolio Service to update holdings.
		Security: Trusts the loggedInUser header passed by the Gateway.

	5. Wallet Service
		Port: 8082
		Role: Manages user balances.
		Features:
			Supports depositing and withdrawing funds.
			Ensures atomic transactions for trade processing.
			Design: Uses userName as the Primary Key for O(1) fast lookups.

	6. Portfolio Service
		Port: 8083
		Role: Tracks what stocks a user owns.
		Database: Stores holdings with average buy price and quantity.

🛠️ Tech Stack
	
	Language: Java 17
	Framework: Spring Boot 3.4
	Cloud Native: Spring Cloud Gateway, Netflix Eureka
	Database: MySQL / PostgreSQL (JPA & Hibernate)
	Security: JWT (JSON Web Tokens)
	Build Tool: Maven
	
⚡ How to Run
	
	1. Clone the repository: 
		git clone [https://github.com/rkhkr/trading-server.git](https://github.com/rkhkr/trading-server.git)
	2. Start Infrastructure:
		Run DiscoveryServerApplication (Wait for it to start).
	3. Start Gateway:
		Run ApiGatewayApplication.
	4. Start Microservices:
		Run AuthServiceApplication, OrderServiceApplication, WalletServiceApplication, PortfolioServiceApplication.

Service,Method,Endpoint,Description
Auth,POST,/auth/register,Create new user
Auth,POST,/auth/token,Login & get JWT
Wallet,GET,/api/wallet,Get balance (Secured)
Order,POST,/api/orders,Place a trade (Secured)
Order,GET,/api/orders/history,View history (Secured)
Portfolio,GET,/api/portfolio,View holdings (Secured)


Built with ❤️ by [RKumar]

```