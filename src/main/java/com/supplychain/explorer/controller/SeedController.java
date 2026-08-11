package com.supplychain.explorer.controller;

import com.supplychain.explorer.service.GraphSeedService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeedController {

    private final GraphSeedService graphSeedService;

    public SeedController(GraphSeedService graphSeedService) {
        this.graphSeedService = graphSeedService;
    }

    @PostMapping("/api/test/seed")
    public String seed() {

        graphSeedService.clearDatabase();

        graphSeedService.seedSuppliers();
        graphSeedService.seedComponents();
        graphSeedService.seedProducts();
        graphSeedService.seedWarehouses();
        graphSeedService.seedRegions();
        graphSeedService.seedCategories();

        graphSeedService.createSupplierComponentRelationships();
        graphSeedService.createComponentProductRelationships();
        graphSeedService.createProductWarehouseRelationships();
        graphSeedService.createWarehouseRegionRelationships();
        graphSeedService.createProductCategoryRelationships();

        return "Graph seed data created successfully";
    }


}
