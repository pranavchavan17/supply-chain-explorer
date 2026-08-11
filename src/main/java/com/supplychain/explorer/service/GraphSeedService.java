package com.supplychain.explorer.service;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Service;

@Service
public class GraphSeedService {

    private final Driver driver;

    public GraphSeedService(Driver driver) {
        this.driver = driver;
    }

    public void clearDatabase() {
        try (Session session = driver.session()) {
            session.run("MATCH (n) DETACH DELETE n").consume();
        }
    }
    public void seedSuppliers() {

        String query = """
        UNWIND $suppliers AS supplier
        CREATE (:Supplier {
            id: supplier.id,
            name: supplier.name,
            location: supplier.location,
            country: supplier.country,
            status: supplier.status
        })
        """;

        try (Session session = driver.session()) {

            var suppliers = java.util.List.of(
                    java.util.Map.of(
                            "id", "SUP-001",
                            "name", "Apex Components",
                            "location", "Pune",
                            "country", "India",
                            "status", "ACTIVE"
                    ),
                    java.util.Map.of(
                            "id", "SUP-002",
                            "name", "Nova Electronics",
                            "location", "Mumbai",
                            "country", "India",
                            "status", "ACTIVE"
                    ),
                    java.util.Map.of(
                            "id", "SUP-003",
                            "name", "Vertex Manufacturing",
                            "location", "Bengaluru",
                            "country", "India",
                            "status", "AT_RISK"
                    )
            );

            session.run(query, java.util.Map.of("suppliers", suppliers))
                    .consume();
        }
    }

    public void seedComponents() {

        String query = """
        UNWIND $components AS component
        CREATE (:Component {
            id: component.id,
            name: component.name,
            type: component.type,
            unitCost: component.unitCost
        })
        """;

        try (Session session = driver.session()) {

            var components = java.util.List.of(
                    java.util.Map.of(
                            "id", "CMP-001",
                            "name", "Lithium Battery",
                            "type", "Electrical",
                            "unitCost", 8500
                    ),
                    java.util.Map.of(
                            "id", "CMP-002",
                            "name", "Electric Motor",
                            "type", "Mechanical",
                            "unitCost", 12000
                    ),
                    java.util.Map.of(
                            "id", "CMP-003",
                            "name", "Motor Controller",
                            "type", "Electrical",
                            "unitCost", 4500
                    ),
                    java.util.Map.of(
                            "id", "CMP-004",
                            "name", "Charging Unit",
                            "type", "Electrical",
                            "unitCost", 3000
                    ),
                    java.util.Map.of(
                            "id", "CMP-005",
                            "name", "Brake Assembly",
                            "type", "Mechanical",
                            "unitCost", 2800
                    )
            );

            session.run(query, java.util.Map.of("components", components))
                    .consume();
        }
    }
    public void seedProducts() {

        String query = """
        UNWIND $products AS product
        CREATE (:Product {
            id: product.id,
            name: product.name,
            category: product.category,
            unitPrice: product.unitPrice
        })
        """;

        try (Session session = driver.session()) {

            var products = java.util.List.of(
                    java.util.Map.of(
                            "id", "PRD-001",
                            "name", "Electric Scooter",
                            "category", "Electric Vehicles",
                            "unitPrice", 95000
                    ),
                    java.util.Map.of(
                            "id", "PRD-002",
                            "name", "Electric Bike",
                            "category", "Electric Vehicles",
                            "unitPrice", 125000
                    ),
                    java.util.Map.of(
                            "id", "PRD-003",
                            "name", "Smart E-Rickshaw",
                            "category", "Electric Vehicles",
                            "unitPrice", 210000
                    ),
                    java.util.Map.of(
                            "id", "PRD-004",
                            "name", "Portable Charger",
                            "category", "Consumer Electronics",
                            "unitPrice", 8000
                    ),
                    java.util.Map.of(
                            "id", "PRD-005",
                            "name", "Delivery E-Bike",
                            "category", "Commercial Vehicles",
                            "unitPrice", 145000
                    )
            );

            session.run(query, java.util.Map.of("products", products))
                    .consume();
        }
    }

    public void seedWarehouses() {

        String query = """
        UNWIND $warehouses AS warehouse
        CREATE (:Warehouse {
            id: warehouse.id,
            name: warehouse.name,
            city: warehouse.city,
            capacity: warehouse.capacity
        })
        """;

        try (Session session = driver.session()) {

            var warehouses = java.util.List.of(
                    java.util.Map.of(
                            "id", "WH-001",
                            "name", "Pune Central Warehouse",
                            "city", "Pune",
                            "capacity", 5000
                    ),
                    java.util.Map.of(
                            "id", "WH-002",
                            "name", "Mumbai Distribution Hub",
                            "city", "Mumbai",
                            "capacity", 7000
                    ),
                    java.util.Map.of(
                            "id", "WH-003",
                            "name", "Bengaluru Tech Warehouse",
                            "city", "Bengaluru",
                            "capacity", 4500
                    ),
                    java.util.Map.of(
                            "id", "WH-004",
                            "name", "Delhi North Warehouse",
                            "city", "Delhi",
                            "capacity", 6000
                    )
            );

            session.run(query, java.util.Map.of("warehouses", warehouses))
                    .consume();
        }
    }

    public void seedRegions() {

        String query = """
        UNWIND $regions AS region
        CREATE (:Region {
            id: region.id,
            name: region.name,
            state: region.state,
            country: region.country
        })
        """;

        try (Session session = driver.session()) {

            var regions = java.util.List.of(
                    java.util.Map.of(
                            "id", "REG-001",
                            "name", "West India",
                            "state", "Maharashtra",
                            "country", "India"
                    ),
                    java.util.Map.of(
                            "id", "REG-002",
                            "name", "South India",
                            "state", "Karnataka",
                            "country", "India"
                    ),
                    java.util.Map.of(
                            "id", "REG-003",
                            "name", "North India",
                            "state", "Delhi",
                            "country", "India"
                    ),
                    java.util.Map.of(
                            "id", "REG-004",
                            "name", "Central India",
                            "state", "Madhya Pradesh",
                            "country", "India"
                    )
            );

            session.run(query, java.util.Map.of("regions", regions))
                    .consume();
        }
    }
    public void seedCategories() {

        String query = """
        UNWIND $categories AS category
        CREATE (:Category {
            id: category.id,
            name: category.name
        })
        """;

        try (Session session = driver.session()) {

            var categories = java.util.List.of(
                    java.util.Map.of(
                            "id", "CAT-001",
                            "name", "Electric Vehicles"
                    ),
                    java.util.Map.of(
                            "id", "CAT-002",
                            "name", "Consumer Electronics"
                    ),
                    java.util.Map.of(
                            "id", "CAT-003",
                            "name", "Commercial Vehicles"
                    ),
                    java.util.Map.of(
                            "id", "CAT-004",
                            "name", "Energy Systems"
                    )
            );

            session.run(query, java.util.Map.of("categories", categories))
                    .consume();
        }
    }
    public void createSupplierComponentRelationships() {

        String query = """
        UNWIND $relationships AS rel

        MATCH (s:Supplier {id: rel.supplierId})
        MATCH (c:Component {id: rel.componentId})

        CREATE (s)-[:SUPPLIES {
            leadTimeDays: rel.leadTimeDays,
            quantityPerMonth: rel.quantityPerMonth
        }]->(c)
        """;

        try (Session session = driver.session()) {

            var relationships = java.util.List.of(

                    java.util.Map.of(
                            "supplierId", "SUP-001",
                            "componentId", "CMP-001",
                            "leadTimeDays", 12,
                            "quantityPerMonth", 500
                    ),

                    java.util.Map.of(
                            "supplierId", "SUP-001",
                            "componentId", "CMP-002",
                            "leadTimeDays", 15,
                            "quantityPerMonth", 300
                    ),

                    java.util.Map.of(
                            "supplierId", "SUP-001",
                            "componentId", "CMP-003",
                            "leadTimeDays", 10,
                            "quantityPerMonth", 400
                    ),

                    java.util.Map.of(
                            "supplierId", "SUP-002",
                            "componentId", "CMP-001",
                            "leadTimeDays", 18,
                            "quantityPerMonth", 350
                    ),

                    java.util.Map.of(
                            "supplierId", "SUP-002",
                            "componentId", "CMP-004",
                            "leadTimeDays", 8,
                            "quantityPerMonth", 600
                    ),

                    java.util.Map.of(
                            "supplierId", "SUP-003",
                            "componentId", "CMP-005",
                            "leadTimeDays", 14,
                            "quantityPerMonth", 250
                    ),

                    java.util.Map.of(
                            "supplierId", "SUP-003",
                            "componentId", "CMP-002",
                            "leadTimeDays", 20,
                            "quantityPerMonth", 200
                    )
            );

            session.run(
                    query,
                    java.util.Map.of("relationships", relationships)
            ).consume();
        }
    }
    public void createComponentProductRelationships() {

        String query = """
        UNWIND $relationships AS rel

        MATCH (c:Component {id: rel.componentId})
        MATCH (p:Product {id: rel.productId})

        CREATE (c)-[:USED_IN {
            quantityRequired: rel.quantityRequired
        }]->(p)
        """;

        try (Session session = driver.session()) {

            var relationships = java.util.List.of(

                    java.util.Map.of(
                            "componentId", "CMP-001",
                            "productId", "PRD-001",
                            "quantityRequired", 1
                    ),

                    java.util.Map.of(
                            "componentId", "CMP-002",
                            "productId", "PRD-001",
                            "quantityRequired", 1
                    ),

                    java.util.Map.of(
                            "componentId", "CMP-003",
                            "productId", "PRD-001",
                            "quantityRequired", 1
                    ),

                    java.util.Map.of(
                            "componentId", "CMP-001",
                            "productId", "PRD-002",
                            "quantityRequired", 1
                    ),

                    java.util.Map.of(
                            "componentId", "CMP-002",
                            "productId", "PRD-002",
                            "quantityRequired", 1
                    ),

                    java.util.Map.of(
                            "componentId", "CMP-003",
                            "productId", "PRD-002",
                            "quantityRequired", 1
                    ),

                    java.util.Map.of(
                            "componentId", "CMP-002",
                            "productId", "PRD-003",
                            "quantityRequired", 2
                    ),

                    java.util.Map.of(
                            "componentId", "CMP-004",
                            "productId", "PRD-004",
                            "quantityRequired", 1
                    )
            );

            session.run(
                    query,
                    java.util.Map.of("relationships", relationships)
            ).consume();
        }
    }
    public void createProductWarehouseRelationships() {

        String query = """
        UNWIND $relationships AS rel

        MATCH (p:Product {id: rel.productId})
        MATCH (w:Warehouse {id: rel.warehouseId})

        CREATE (p)-[:STORED_AT {
            stockQuantity: rel.stockQuantity
        }]->(w)
        """;

        try (Session session = driver.session()) {

            var relationships = java.util.List.of(

                    java.util.Map.of(
                            "productId", "PRD-001",
                            "warehouseId", "WH-001",
                            "stockQuantity", 450
                    ),

                    java.util.Map.of(
                            "productId", "PRD-001",
                            "warehouseId", "WH-002",
                            "stockQuantity", 300
                    ),

                    java.util.Map.of(
                            "productId", "PRD-002",
                            "warehouseId", "WH-001",
                            "stockQuantity", 250
                    ),

                    java.util.Map.of(
                            "productId", "PRD-002",
                            "warehouseId", "WH-003",
                            "stockQuantity", 180
                    ),

                    java.util.Map.of(
                            "productId", "PRD-003",
                            "warehouseId", "WH-004",
                            "stockQuantity", 120
                    ),

                    java.util.Map.of(
                            "productId", "PRD-004",
                            "warehouseId", "WH-002",
                            "stockQuantity", 800
                    ),

                    java.util.Map.of(
                            "productId", "PRD-005",
                            "warehouseId", "WH-003",
                            "stockQuantity", 200
                    )
            );

            session.run(
                    query,
                    java.util.Map.of("relationships", relationships)
            ).consume();
        }
    }
    public void createWarehouseRegionRelationships() {

        String query = """
        UNWIND $relationships AS rel

        MATCH (w:Warehouse {id: rel.warehouseId})
        MATCH (r:Region {id: rel.regionId})

        CREATE (w)-[:LOCATED_IN]->(r)
        """;

        try (Session session = driver.session()) {

            var relationships = java.util.List.of(

                    java.util.Map.of(
                            "warehouseId", "WH-001",
                            "regionId", "REG-001"
                    ),

                    java.util.Map.of(
                            "warehouseId", "WH-002",
                            "regionId", "REG-001"
                    ),

                    java.util.Map.of(
                            "warehouseId", "WH-003",
                            "regionId", "REG-002"
                    ),

                    java.util.Map.of(
                            "warehouseId", "WH-004",
                            "regionId", "REG-003"
                    )
            );

            session.run(
                    query,
                    java.util.Map.of("relationships", relationships)
            ).consume();
        }
    }
    public void createProductCategoryRelationships() {

        String query = """
        UNWIND $relationships AS rel

        MATCH (p:Product {id: rel.productId})
        MATCH (c:Category {id: rel.categoryId})

        CREATE (p)-[:BELONGS_TO]->(c)
        """;

        try (Session session = driver.session()) {

            var relationships = java.util.List.of(

                    java.util.Map.of(
                            "productId", "PRD-001",
                            "categoryId", "CAT-001"
                    ),

                    java.util.Map.of(
                            "productId", "PRD-002",
                            "categoryId", "CAT-001"
                    ),

                    java.util.Map.of(
                            "productId", "PRD-003",
                            "categoryId", "CAT-001"
                    ),

                    java.util.Map.of(
                            "productId", "PRD-004",
                            "categoryId", "CAT-002"
                    ),

                    java.util.Map.of(
                            "productId", "PRD-005",
                            "categoryId", "CAT-003"
                    )
            );

            session.run(
                    query,
                    java.util.Map.of("relationships", relationships)
            ).consume();
        }
    }
}