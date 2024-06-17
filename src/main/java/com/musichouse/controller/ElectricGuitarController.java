package com.musichouse.controller;

import com.musichouse.model.domain.ElectricGuitar;
import com.musichouse.model.service.ElectricGuitarServiceImp;
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
@RequestMapping("/electricguitar")
public class ElectricGuitarController {

    private final ElectricGuitarServiceImp electricGuitarService;

    public ElectricGuitarController(ElectricGuitarServiceImp electricGuitarService) {
        this.electricGuitarService = electricGuitarService;
    }

    @Operation(summary = "Registering a new electric guitar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully registered",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ElectricGuitar.class)
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
    public ResponseEntity<?> save(@RequestBody ElectricGuitar electricGuitar) {
        try {
            electricGuitarService.save(electricGuitar);
            return ResponseEntity.status(HttpStatus.CREATED).body(electricGuitar);
        }catch (EntityExistsException e) {
            return ResponseEntity.badRequest().body(new MessagePayload(e.getMessage()));
        }

    }


    @Operation(summary = "Listing all electric guitars")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Showing all electric guitars",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ElectricGuitar.class)
                    )}
            ),
            @ApiResponse(responseCode = "404", description = "There are no electric guitars",
                    content= {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class)
                    )}
            )
    })
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<ElectricGuitar> electricGuitars = electricGuitarService.getAll();
        if(electricGuitars.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload("There are no electricGuitars"));
        }
        return ResponseEntity.ok(electricGuitars);
    }

    @Operation(summary = "One electric guitar by model")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Showing electric guitar by model",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ElectricGuitar.class)
                    )}
            ),
            @ApiResponse(responseCode = "404", description = "Cannot show the electric guitar",
                    content= {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class)
                    )}
            )
    })
    @GetMapping("/{model}")
    public ResponseEntity<?> getByModel(@PathVariable String model) {
        Optional<ElectricGuitar> electricGuitar = electricGuitarService.getByModel(model);
        if(electricGuitar.isPresent()){
            return ResponseEntity.ok(electricGuitar);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload("Model does not exist"));
    }

    @Operation(summary = "Updating electric guitar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Electric guitar updated",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ElectricGuitar.class)
                    )}
            ),
            @ApiResponse(responseCode = "404", description = "Electric guitar not found to update",
                    content= {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class)
                    )}
            )
    })
    @PutMapping("/{model}")
    public ResponseEntity<?> update(@PathVariable String model, @RequestBody ElectricGuitar electricGuitar) {
       try{
           electricGuitarService.update(model, electricGuitar);
           return ResponseEntity.ok(electricGuitar);
        }catch (EntityNotFoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
       }
    }

    @Operation(summary = "Deleting electric guitar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Electric guitar deleted",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class)
                    )}
            ),
            @ApiResponse(responseCode = "404", description = "Electric guitar not found to delete",
                    content= {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class)
                    )}
            )
    })
    @DeleteMapping("/{model}")
    public ResponseEntity<?> deleteById(@PathVariable String model) {
      try {
          electricGuitarService.delete(model);
          return ResponseEntity.ok(new MessagePayload("Electric guitar "+model+" has been deleted"));
      }catch (EntityNotFoundException e) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
      }
    }

}