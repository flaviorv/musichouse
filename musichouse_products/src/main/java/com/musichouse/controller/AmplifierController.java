package com.musichouse.controller;

import com.musichouse.model.domain.Amplifier;
import com.musichouse.model.service.AmplifierServiceImp;
import com.musichouse.payload.MessagePayload;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/amplifier")
public class AmplifierController {

    private final AmplifierServiceImp amplifierService;


    public AmplifierController(AmplifierServiceImp amplifierService) {
        this.amplifierService = amplifierService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Amplifier amplifier) {
       try{
           amplifierService.save(amplifier);
           return ResponseEntity.status(HttpStatus.CREATED).body(amplifier);
       }catch (EntityExistsException e) {
           return ResponseEntity.badRequest().body(new MessagePayload(e.getMessage()));
       }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Amplifier> amplifiers = amplifierService.getAll();
        if(amplifiers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload("There are no amplifiers"));
        }
        return ResponseEntity.ok(amplifiers);
    }

    @GetMapping("/{model}")
    public ResponseEntity<?> getByModel(@PathVariable String model) {
        Optional<Amplifier> amplifier = amplifierService.getByModel(model);
        if(amplifier.isPresent()){
            return ResponseEntity.ok(amplifier);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload("Model does not exist"));
    }

    @PutMapping("/{model}")
    public ResponseEntity<?> update(@PathVariable String model, @RequestBody Amplifier amplifier) {
        try{
            amplifierService.update(model, amplifier);
            return ResponseEntity.ok(amplifier);
        }catch (EntityNotFoundException|IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
        }
    }

    @DeleteMapping("/{model}")
    public ResponseEntity<?> deleteById(@PathVariable String model) {
        try{
            amplifierService.delete(model);
            return ResponseEntity.ok(new MessagePayload("Amplifier "+model+" has been deleted"));
        }catch(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
        }

    }
}
