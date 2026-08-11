package com.supplychain.explorer.controller;

import com.supplychain.explorer.service.SupplyChainQueryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/supply-chain")
public class SupplyChainController {

    private final SupplyChainQueryService supplyChainQueryService;

    public SupplyChainController(
            SupplyChainQueryService supplyChainQueryService) {
        this.supplyChainQueryService = supplyChainQueryService;
    }

    @GetMapping("/suppliers/{supplierId}/impact")
    public List<Map<String, Object>> getSupplierImpact(
            @PathVariable String supplierId) {

        return supplyChainQueryService.getSupplierImpact(supplierId);
    }

    @GetMapping("/components/{componentId}/impact")
    public List<Map<String, Object>> getComponentImpact(
            @PathVariable String componentId) {

        return supplyChainQueryService.getComponentImpact(componentId);
    }
    @GetMapping("/suppliers/{supplierId}/warehouses/{warehouseId}/why-affected")
    public List<String> getWarehouseImpactPath(
            @PathVariable String supplierId,
            @PathVariable String warehouseId) {

        return supplyChainQueryService.getWarehouseImpactPath(
                supplierId,
                warehouseId
        );
    }

    @GetMapping("/suppliers/high-impact")
    public List<Map<String, Object>> getHighImpactSuppliers() {

        return supplyChainQueryService.getHighImpactSuppliers();
    }
}
