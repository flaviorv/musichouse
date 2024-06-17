package com.musichouse.controller;

import com.musichouse.model.domain.Amplifier;
import com.musichouse.model.service.AmplifierService;
import com.musichouse.payload.MessagePayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/amplifier")
public class AmplifierController {

    private final AmplifierService amplifierService;


    public AmplifierController(AmplifierService amplifierService) {
        this.amplifierService = amplifierService;
    }

    @Operation(summary = "Registering a new amplifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully registered",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Amplifier.class)
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
    public ResponseEntity<?> save(@RequestBody Amplifier amplifier) {
       try{
           amplifierService.save(amplifier);
           return ResponseEntity.status(HttpStatus.CREATED).body(amplifier);
       }catch (EntityExistsException e) {
           return ResponseEntity.badRequest().body(new MessagePayload(e.getMessage()));
       }
    }


    @Operation(summary = "Listing all amplifiers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Showing all amplifiers",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Amplifier.class)
                    )}
            ),
            @ApiResponse(responseCode = "404", description = "Cannot show the amplifiers",
                    content= {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class)
                    )}
            )
    })
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Amplifier> amplifiers = amplifierService.getAll();
        if(amplifiers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload("There are no amplifiers"));
        }
        return ResponseEntity.ok(amplifiers);
    }

    @Operation(summary = "One amplifier by model")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Showing amplifier by model",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Amplifier.class)
                    )}
            ),
            @ApiResponse(responseCode = "404", description = "Cannot show the amplifier",
                    content= {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class)
                    )}
            )
    })
    @GetMapping("/{model}")
    public ResponseEntity<?> getByModel(@PathVariable String model) {
        Optional<Amplifier> amplifier = amplifierService.getByModel(model);
        if(amplifier.isPresent()){
            return ResponseEntity.ok(amplifier);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload("Model does not exist"));
    }

    @Operation(summary = "Updating amplifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Amplifier updated",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Amplifier.class)
                    )}
            ),
            @ApiResponse(responseCode = "404", description = "There is no amplifier",
                    content= {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class)
                    )}
            )
    })
    @PutMapping("/{model}")
    public ResponseEntity<?> update(@PathVariable String model, @RequestBody Amplifier amplifier) {
        if (amplifierService.update(model, amplifier).isPresent()) {
            return ResponseEntity.ok(amplifier);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload("Model does not exist"));
    }

    @Operation(summary = "Deleting amplifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Amplifier deleted",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class)
                    )}
            ),
            @ApiResponse(responseCode = "404", description = "There is no amplifier",
                    content= {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagePayload.class)
                    )}
            )
    })
    @DeleteMapping("/{model}")
    public ResponseEntity<?> deleteById(@PathVariable String model) {
        if (amplifierService.delete(model)) {
            return ResponseEntity.ok(new MessagePayload("Amplifier "+model+" has been deleted"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload("Model does not exist"));
    }
}
