package com.supplychain.explorer.service;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Service;
import com.supplychain.explorer.exception.ResourceNotFoundException;


import java.util.List;
import java.util.Map;

@Service
public class SupplyChainQueryService {

    private final Driver driver;

    public SupplyChainQueryService(Driver driver) {
        this.driver = driver;
    }

    /**
     * Finds the complete downstream impact of a supplier.
     *
     * Supplier
     *    ↓
     * Component
     *    ↓
     * Product
     *    ↓
     * Warehouse
     *    ↓
     * Region
     */
    public List<Map<String, Object>> getSupplierImpact(String supplierId) {

        String query = """
        MATCH (s:Supplier {id: $supplierId})
        OPTIONAL MATCH (s)
              -[:SUPPLIES]->
              (c:Component)
              -[:USED_IN]->
              (p:Product)
              -[:STORED_AT]->
              (w:Warehouse)
              -[:LOCATED_IN]->
              (r:Region)

        RETURN
            s.id AS supplierId,
            s.name AS supplierName,
            c.id AS componentId,
            c.name AS componentName,
            p.id AS productId,
            p.name AS productName,
            w.id AS warehouseId,
            w.name AS warehouseName,
            r.id AS regionId,
            r.name AS regionName
        ORDER BY productName, warehouseName
        """;

        try (Session session = driver.session()) {

            var result = session.run(
                    query,
                    Map.of("supplierId", supplierId)
            ).list();

            if (result.isEmpty()) {
                throw new ResourceNotFoundException(
                        "Supplier not found: " + supplierId
                );
            }

            return result.stream()
                    .filter(record -> !record.get("componentId").isNull())
                    .map(record -> Map.<String, Object>of(
                            "supplierId", record.get("supplierId").asString(),
                            "supplierName", record.get("supplierName").asString(),
                            "componentId", record.get("componentId").asString(),
                            "componentName", record.get("componentName").asString(),
                            "productId", record.get("productId").asString(),
                            "productName", record.get("productName").asString(),
                            "warehouseId", record.get("warehouseId").asString(),
                            "warehouseName", record.get("warehouseName").asString(),
                            "regionId", record.get("regionId").asString(),
                            "regionName", record.get("regionName").asString()
                    ))
                    .toList();
        }
    }

    public List<Map<String, Object>> getComponentImpact(String componentId) {

        try (Session session = driver.session()) {

            // 1. First verify that the component exists
            var componentCheck = session.run(
                    """
                    MATCH (c:Component {id: $componentId})
                    RETURN c
                    """,
                    Map.of("componentId", componentId)
            );

            if (!componentCheck.hasNext()) {
                throw new ResourceNotFoundException(
                        "Component not found: " + componentId
                );
            }

            // 2. Component exists, now find its downstream impact
            String query = """
            MATCH (c:Component {id: $componentId})
                  -[:USED_IN]->
                  (p:Product)
                  -[:STORED_AT]->
                  (w:Warehouse)
                  -[:LOCATED_IN]->
                  (r:Region)

            RETURN DISTINCT
                c.id AS componentId,
                c.name AS componentName,
                p.id AS productId,
                p.name AS productName,
                w.id AS warehouseId,
                w.name AS warehouseName,
                r.id AS regionId,
                r.name AS regionName

            ORDER BY productName, warehouseName
            """;

            var result = session.run(
                    query,
                    Map.of("componentId", componentId)
            );

            return result.list(record -> Map.<String, Object>of(
                    "componentId",
                    record.get("componentId").asString(),

                    "componentName",
                    record.get("componentName").asString(),

                    "productId",
                    record.get("productId").asString(),

                    "productName",
                    record.get("productName").asString(),

                    "warehouseId",
                    record.get("warehouseId").asString(),

                    "warehouseName",
                    record.get("warehouseName").asString(),

                    "regionId",
                    record.get("regionId").asString(),

                    "regionName",
                    record.get("regionName").asString()
            ));
        }
    }

    public List<String> getWarehouseImpactPath(
            String supplierId,
            String warehouseId) {

        try (Session session = driver.session()) {

            // Check supplier exists
            var supplierResult = session.run(
                    """
                    MATCH (s:Supplier {id: $supplierId})
                    RETURN s.id AS id
                    """,
                    Map.of("supplierId", supplierId)
            );

            if (!supplierResult.hasNext()) {
                throw new ResourceNotFoundException(
                        "Supplier not found: " + supplierId
                );
            }

            // Check warehouse exists
            var warehouseResult = session.run(
                    """
                    MATCH (w:Warehouse {id: $warehouseId})
                    RETURN w.id AS id
                    """,
                    Map.of("warehouseId", warehouseId)
            );

            if (!warehouseResult.hasNext()) {
                throw new ResourceNotFoundException(
                        "Warehouse not found: " + warehouseId
                );
            }

            // Find impact paths
            var impactResult = session.run(
                    """
                    MATCH (s:Supplier {id: $supplierId})
                          -[:SUPPLIES]->
                          (c:Component)
                          -[:USED_IN]->
                          (p:Product)
                          -[:STORED_AT]->
                          (w:Warehouse {id: $warehouseId})
    
                    RETURN DISTINCT
                        s.name + " → " +
                        c.name + " → " +
                        p.name + " → " +
                        w.name AS path
    
                    ORDER BY path
                    """,
                    Map.of(
                            "supplierId", supplierId,
                            "warehouseId", warehouseId
                    )
            );

            return impactResult.list(
                    record -> record.get("path").asString()
            );
        }
    }

    public List<Map<String, Object>> getHighImpactSuppliers() {

        String query = """
        MATCH (s:Supplier)
              -[:SUPPLIES]->
              (c:Component)
              -[:USED_IN]->
              (p:Product)
              -[:STORED_AT]->
              (w:Warehouse)

        RETURN
            s.id AS supplierId,
            s.name AS supplierName,
            count(DISTINCT c) AS componentsAffected,
            count(DISTINCT p) AS productsAffected,
            count(DISTINCT w) AS warehousesAffected

        ORDER BY productsAffected DESC,
                 warehousesAffected DESC
        """;

        try (Session session = driver.session()) {

            return session.run(query).list(record -> Map.of(
                    "supplierId", record.get("supplierId").asString(),
                    "supplierName", record.get("supplierName").asString(),
                    "componentsAffected",
                    record.get("componentsAffected").asInt(),
                    "productsAffected",
                    record.get("productsAffected").asInt(),
                    "warehousesAffected",
                    record.get("warehousesAffected").asInt()
            ));
        }
    }
}
