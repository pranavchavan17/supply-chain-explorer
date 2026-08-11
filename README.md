# Supply Chain Explorer

A graph-based supply chain impact analysis application built using Spring Boot and CognoDB.

## Tech Stack

- Java 21
- Spring Boot
- Spring Web MVC
- CognoDB
- Cypher
- Maven

## Graph Model

The application models the supply chain as a graph:

Supplier
↓ SUPPLIES
Component
↓ USED_IN
Product
↓ STORED_AT
Warehouse
↓ LOCATED_IN
Region

Product
↓ BELONGS_TO
Category

## Business APIs

### 1. Supplier Impact

GET /api/supply-chain/suppliers/{supplierId}/impact

Example:

GET /api/supply-chain/suppliers/SUP-001/impact

Returns the downstream impact of a supplier across components, products, warehouses and regions.

---

### 2. Component Impact

GET /api/supply-chain/components/{componentId}/impact

Example:

GET /api/supply-chain/components/CMP-001/impact

Returns products, warehouses and regions affected by a component.

---

### 3. Why Is a Warehouse Affected?

GET /api/supply-chain/suppliers/{supplierId}/warehouses/{warehouseId}/why-affected

Example:

GET /api/supply-chain/suppliers/SUP-001/warehouses/WH-001/why-affected

Returns the graph paths explaining how a supplier can affect a particular warehouse.

---

### 4. High Impact Suppliers

GET /api/supply-chain/suppliers/high-impact

Returns suppliers ranked by their downstream impact across components, products and warehouses.

## Error Handling

The application provides centralized exception handling.

Example:

GET /api/supply-chain/suppliers/SUP-999/impact

Response:

{
  "status": 404,
  "error": "Not Found",
  "message": "Supplier not found: SUP-999"
}