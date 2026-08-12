package com.supplychain.explorer.controller;

import com.supplychain.explorer.service.SupplyChainQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "https://supply-chain-explorer-1.onrender.com")
@RestController
@RequestMapping("/api/supply-chain")
@Tag(
        name = "Supply Chain Analysis",
        description = "APIs for analyzing supplier and supply chain impact"
)
public class SupplyChainController {

    private final SupplyChainQueryService supplyChainQueryService;

    public SupplyChainController(
            SupplyChainQueryService supplyChainQueryService) {
        this.supplyChainQueryService = supplyChainQueryService;
    }

    @Operation(
            summary = "Analyze supplier impact",
            description = """
                    Finds the downstream impact of a supplier across
                    components, products, warehouses and regions.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Supplier impact found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Supplier not found"
            )
    })
    @GetMapping("/suppliers/{supplierId}/impact")
    public List<Map<String, Object>> getSupplierImpact(
            @Parameter(
                    description = "Unique supplier ID",
                    example = "SUP-001"
            )
            @PathVariable String supplierId) {

        return supplyChainQueryService.getSupplierImpact(supplierId);
    }


    @Operation(
            summary = "Analyze component impact",
            description = """
                    Finds products, warehouses and regions
                    affected by a specific component.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Component impact found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Component not found"
            )
    })
    @GetMapping("/components/{componentId}/impact")
    public List<Map<String, Object>> getComponentImpact(
            @Parameter(
                    description = "Unique component ID",
                    example = "CMP-001"
            )
            @PathVariable String componentId) {

        return supplyChainQueryService.getComponentImpact(componentId);
    }


    @Operation(
            summary = "Explain warehouse impact",
            description = """
                    Finds the graph paths explaining how a supplier
                    can affect a specific warehouse.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Impact paths found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Supplier or warehouse not found"
            )
    })
    @GetMapping("/suppliers/{supplierId}/warehouses/{warehouseId}/why-affected")
    public List<String> getWarehouseImpactPath(

            @Parameter(
                    description = "Unique supplier ID",
                    example = "SUP-001"
            )
            @PathVariable String supplierId,

            @Parameter(
                    description = "Unique warehouse ID",
                    example = "WH-001"
            )
            @PathVariable String warehouseId) {

        return supplyChainQueryService.getWarehouseImpactPath(
                supplierId,
                warehouseId
        );
    }


    @Operation(
            summary = "Find high-impact suppliers",
            description = """
                    Ranks suppliers according to their downstream
                    impact across components, products and warehouses.
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "High-impact suppliers retrieved successfully"
    )
    @GetMapping("/suppliers/high-impact")
    public List<Map<String, Object>> getHighImpactSuppliers() {

        return supplyChainQueryService.getHighImpactSuppliers();
    }
}