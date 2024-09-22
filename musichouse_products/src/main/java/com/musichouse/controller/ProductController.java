package com.musichouse.controller;

import com.musichouse.model.domain.Product;
import com.musichouse.model.service.ProductServiceImp;
import com.musichouse.payload.MessagePayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductServiceImp productService;

    public ProductController(ProductServiceImp productService) {
        this.productService = productService;
    }

    @Operation(summary = "Listing all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Showing all products",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Product.class)
                    )}
            ),
            @ApiResponse(responseCode = "404", description = "Cannot show the products",
                    content= {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class)
                    )}
            )
    })
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Product> products = productService.getAll();
        if(products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload("There are no products"));
        }
        return ResponseEntity.ok(products);
    }


    @Operation(summary = "Product by model")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Showing product by model",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Product.class)
                    )}
            ),
            @ApiResponse(responseCode = "404", description = "Cannot show the product",
                    content= {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class)
                    )}
            )
    })
    @GetMapping("/{model}")
    public ResponseEntity<?> getByModel(@PathVariable String model) {
        Optional<Product> product = productService.getByModel(model);
        if(product.isPresent()){
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload("Model does not exist"));
    }

    @Operation(summary = "Deleting product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Product deleted",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class)
                    )}
            ),
            @ApiResponse(responseCode = "404", description = "There is no product",
                    content= {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class)
                    )}
            )
    })
    @DeleteMapping("/{model}")
    public ResponseEntity<?> deleteByModel(@PathVariable String model) {
        try {
            productService.deleteByModel(model);
            return ResponseEntity.ok(new MessagePayload("Product "+model+" has been deleted"));
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
        }
    }

    @Operation(summary = "Deleting All")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "All products have been deleted",
                    content = {@Content (
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class)
                    )}
            ),
            @ApiResponse(responseCode = "204", description = "No products to delete",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class)
                    )}
            )
    })
    @DeleteMapping
    public ResponseEntity<?> deleteAll(){
        try {
            productService.deleteAll();
            return ResponseEntity.ok(new MessagePayload("All products have been deleted"));
        }catch (EntityExistsException e){
            return ResponseEntity.noContent().build();
        }
    }
}
