package com.musichouse_sales.controller;

import com.musichouse_sales.model.domain.Sale;
import com.musichouse_sales.model.service.SaleServiceConstants;
import com.musichouse_sales.model.service.SaleServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleController {

    private static final Logger LOG = LoggerFactory.getLogger(SaleController.class);
    private final SaleServiceImp saleServiceImp;

    public SaleController(SaleServiceImp saleServiceImp){
        this.saleServiceImp = saleServiceImp;
    }

    @Operation(summary = "Listing all sales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Showing all sales",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = List.class)
                    )}
            ),
            @ApiResponse(responseCode = "204", description = "There are no sales")
    })
    @GetMapping()
    public List<Sale> getAll(){
        return saleServiceImp.getAll();
    }

    @Operation(summary = "Add product to an existent sale")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )}
            ),
            @ApiResponse(responseCode = "500", description = "Product cannot be added",
                    content= {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )}
            )
    })
    @PostMapping("/{saleId}/{model}")
    public ResponseEntity addProductToAnExistentSale(@PathVariable String saleId, @PathVariable String model){
        try {
            LOG.info("Added product to sale " + saleId);
            saleServiceImp.addProductToAnExistentSale(saleId, model);
            return ResponseEntity.ok(SaleServiceConstants.PRODUCT_ADDED_SUCCESSFULLY);
        } catch (Exception e) {
            LOG.error(SaleServiceConstants.PRODUCT_ADDED_ERROR, e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Operation(summary = "Add product to a new sale")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )}
            ),
            @ApiResponse(responseCode = "500", description = "Product cannot be added",
                    content= {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )}
            )
    })
    @PostMapping("/{model}")
    public ResponseEntity addProductToANewSale(@PathVariable String model){
        try {
           saleServiceImp.addProductToANewSale(model);
           LOG.info("Added product to a new sale " + model);
           return ResponseEntity.ok(SaleServiceConstants.PRODUCT_ADDED_SUCCESSFULLY);
        } catch (Exception e) {
            LOG.error(SaleServiceConstants.PRODUCT_ADDED_ERROR, e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Close sale")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully closed",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Sale.class)
                    )}
            ),
            @ApiResponse(responseCode = "500", description = "Sale cannot be closed",
                    content= {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )}
            )
    })
    @PostMapping("/close")
    public ResponseEntity closeSale(@RequestBody Sale _sale) {
        try {
            Sale sale = saleServiceImp.close(_sale.getId());
            return ResponseEntity.ok(sale);
        }catch (Exception e) {
            LOG.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Delete a sale by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sale removed",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )}
            ),
            @ApiResponse(responseCode = "500", description = "Sale cannot be removed",
                    content= {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )}
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id){
        try{
            saleServiceImp.delete(id);
            return ResponseEntity.ok().body(SaleServiceConstants.SALE_REMOVED_SUCCESSFULLY);
        }catch (Exception e){
            LOG.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Delete all sales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully removed",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Sale.class)
                    )}
            )
    })
    @DeleteMapping()
    public ResponseEntity deleteAll(){
        saleServiceImp.deleteAll();
        return ResponseEntity.ok().body("Sales deleted successfully");
    }
}
