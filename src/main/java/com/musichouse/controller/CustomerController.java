package com.musichouse.controller;

import com.musichouse.model.domain.Customer;
import com.musichouse.model.service.CustomerServiceImp;
import com.musichouse.payload.MessagePayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceImp customerService;

    @Operation(summary = "Registering a new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully registered",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)
                    )}
            ),
            @ApiResponse(responseCode = "400", description = "Registration cannot be carried out",
                    content= {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class)
                    )}
            )
    })
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Customer customer) {
        try{
            customerService.save(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(customer);
        }catch (EntityExistsException e) {
            return ResponseEntity.badRequest().body(new MessagePayload(e.getMessage()));
        }
    }

    @Operation(summary = "Listing all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Showing all customers",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)
                    )}
            ),
            @ApiResponse(responseCode = "404", description = "Cannot show the customers",
                    content= {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class)
                    )}
            )
    })
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Customer> amplifiers = customerService.getAll();
        if(amplifiers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload("There are no customers"));
        }
        return ResponseEntity.ok(amplifiers);
    }

    @Operation(summary = "One customer by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Showing customer by email",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)
                    )}
            ),
            @ApiResponse(responseCode = "404", description = "Cannot show the Customer",
                    content= {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class)
                    )}
            )
    })
    @GetMapping("/{email}")
    public ResponseEntity<?> getByModel(@PathVariable String email) {
        Optional<Customer> customer = customerService.getByEmail(email);
        if(customer.isPresent()){
            return ResponseEntity.ok(customer);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload("Email does not exist"));
    }

    @Operation(summary = "Updating customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Customer updated",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)
                    )}
            ),
            @ApiResponse(responseCode = "404", description = "There is no customer",
                    content= {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class)
                    )}
            )
    })
    @PutMapping("/{email}")
    public ResponseEntity<?> update(@PathVariable String email, @RequestBody Customer customer) {
        try{
            customerService.update(email, customer);
            return ResponseEntity.ok(customer);
        }catch (EntityNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
        }
    }

    @Operation(summary = "Deleting customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Customer deleted",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class)
                    )}
            ),
            @ApiResponse(responseCode = "404", description = "There is no customer",
                    content= {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class)
                    )}
            )
    })
    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteById(@PathVariable String email) {
        try{
            customerService.delete(email);
            return ResponseEntity.ok(new MessagePayload("Customer has been deleted"));
        }catch(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
        }

    }

}
