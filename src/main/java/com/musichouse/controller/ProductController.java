package com.musichouse.controller;

import com.musichouse.model.domain.Product;
import com.musichouse.model.service.ProductService;
import com.musichouse.payload.MessagePayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
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
}
