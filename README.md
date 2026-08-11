# Supply Chain Explorer

A graph-based supply chain impact analysis application built with **Spring Boot, Java 21, React, and CognoDB**.

Supply Chain Explorer helps identify how a supplier or component can affect downstream products, warehouses, and regions by traversing relationships in a graph database.

---

## 🚀 Problem Statement

Modern supply chains contain many interconnected entities such as suppliers, components, products, warehouses, and regions.

When a supplier or component is disrupted, it can be difficult to understand:

- Which products are affected?
- Which warehouses are affected?
- Which regions may be impacted?
- Why is a particular warehouse affected?
- Which suppliers have the largest downstream impact?

Traditional table-based approaches can make these relationship-based questions difficult to visualize and query.

**Supply Chain Explorer uses a graph database to model and traverse these relationships directly.**

---

## 💡 Solution

The application represents the supply chain as a connected graph:

```text
Supplier
   │
   │ SUPPLIES
   ▼
Component
   │
   │ USED_IN
   ▼
Product
   │
   │ STORED_AT
   ▼
Warehouse
   │
   │ LOCATED_IN
   ▼
Region

Products are also connected to categories:

Product
   │
   │ BELONGS_TO
   ▼
Category

This graph structure allows the application to trace downstream impact through multiple relationships using Cypher queries.

✨ Key Features
Supplier Impact Analysis
Analyze the downstream impact of a supplier across components, products, warehouses, and regions.

Supplier
   ↓
Component
   ↓
Product
   ↓
Warehouse
   ↓
Region


Component Impact Analysis
Analyze how a component affects downstream products, warehouses, and regions.

Component
   ↓
Product
   ↓
Warehouse
   ↓
Region


Why Is This Warehouse Affected?
Given a supplier and warehouse, the application finds the graph path explaining their relationship.

Supplier
   ↓
Component
   ↓
Product
   ↓
Warehouse

High-Impact Supplier Analysis
Identifies suppliers with larger downstream impact based on:

Components affected
Products affected
Warehouses affected

API Documentation
Interactive API documentation is provided using Swagger/OpenAPI.

Error Handling
Centralized exception handling provides clean API responses for invalid suppliers, components, and warehouses.

🕸️ Graph Data Model
Nodes
Supplier
Component
Product
Warehouse
Region
Category
Relationships

Supplier ──SUPPLIES──> Component

Component ──USED_IN──> Product

Product ──STORED_AT──> Warehouse

Warehouse ──LOCATED_IN──> Region

Product ──BELONGS_TO──> Category


🏗️ Architecture
              React Frontend
                    │
                  Axios
                    │
                    ▼
            Spring Boot REST API
                    │
                    ▼
                Services
                    │
              Cypher Queries
                    │
                    ▼
                 CognoDB
                    │
                    ▼
             Supply Chain Graph

🔌 REST APIs

Base URL:

http://localhost:8080
Supplier Impact
GET /api/supply-chain/suppliers/{supplierId}/impact

Example:

GET /api/supply-chain/suppliers/SUP-001/impact
Component Impact
GET /api/supply-chain/components/{componentId}/impact

Example:

GET /api/supply-chain/components/CMP-001/impact
Why Affected
GET /api/supply-chain/suppliers/{supplierId}/warehouses/{warehouseId}/why-affected

Example:

GET /api/supply-chain/suppliers/SUP-001/warehouses/WH-001/why-affected
High-Impact Suppliers
GET /api/supply-chain/suppliers/high-impact


📸 Screenshots
Dashboard
Supplier Impact
Component Impact
Why Affected

🛠️ Technology Stack
Backend
Java 21
Spring Boot
Spring Web MVC
Maven
CognoDB
Cypher
Swagger / OpenAPI

Frontend

React
Vite
JavaScript
Axios
React Router
Tools
IntelliJ IDEA
Postman
Git
GitHub


📁 Project Structure
supply-chain-explorer/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/supplychain/explorer/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── exception/
│   │   │       └── service/
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   └── utils/
│   ├── package.json
│   └── vite.config.js
│
├── screenshots/
│   ├── dashboard.png
│   ├── supplier-impact.png
│   ├── component-impact.png
│   └── why-affected.png
│
├── pom.xml
├── README.md
└── .gitignore


⚙️ Environment Variables
Database credentials are not stored directly in the source code.
Configure:

COGNODB_URI
COGNODB_USERNAME
COGNODB_PASSWORD

The backend uses:

cognodb.uri=${COGNODB_URI}
cognodb.username=${COGNODB_USERNAME}
cognodb.password=${COGNODB_PASSWORD}

Never commit actual database credentials to GitHub.

▶️ How to Run
Backend

From the project root:

.\mvnw.cmd spring-boot:run

Backend:

http://localhost:8080
Frontend

Open another terminal:

cd frontend
npm install
npm run dev

Frontend:

http://localhost:5173

Both the backend and frontend should be running for the complete application.

📖 Swagger / OpenAPI

After starting the backend, open:

http://localhost:8080/swagger-ui/index.html

The OpenAPI specification is available at:

http://localhost:8080/v3/api-docs

🔄 Application Flow
User selects a supplier/component
              ↓
        React Frontend
              ↓
          Axios Request
              ↓
      Spring Boot REST API
              ↓
       Business Service
              ↓
        Cypher Query
              ↓
            CognoDB
              ↓
      Graph relationships
              ↓
        Impact analysis
              ↓
        React UI result


🎯 What This Project Demonstrates
Graph database modeling
Cypher query development
Relationship-based impact analysis
Spring Boot REST API development
React frontend development
Full-stack API integration
Centralized exception handling
Swagger/OpenAPI documentation
Environment-based configuration
Git and GitHub workflow


🔮 Future Scope
Possible future improvements:

Advanced graph visualization
Real-time supply chain data
Supplier risk scoring
Historical impact analysis
Supply chain disruption alerts
AI-assisted impact explanations
Production deployment


👨‍💻 Author
Pranav Chavan
B.Tech Computer Science Engineering

📄 License
This project was developed for educational, assessment, and portfolio purposes.


### Do only this now

Replace your current `README.md` with the above.

Then create:

```text
screenshots/
├── dashboard.png
├── supplier-impact.png
├── component-impact.png
└── why-affected.png
