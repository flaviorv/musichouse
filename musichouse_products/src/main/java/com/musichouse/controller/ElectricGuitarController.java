package com.musichouse.controller;

import com.musichouse.model.domain.ElectricGuitar;
import com.musichouse.model.service.ElectricGuitarServiceImp;
import com.musichouse.payload.MessagePayload;
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


    @PostMapping
    public ResponseEntity<?> save(@RequestBody ElectricGuitar electricGuitar) {
        try {
            electricGuitarService.save(electricGuitar);
            return ResponseEntity.status(HttpStatus.CREATED).body(electricGuitar);
        }catch (EntityExistsException e) {
            return ResponseEntity.badRequest().body(new MessagePayload(e.getMessage()));
        }

    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<ElectricGuitar> electricGuitars = electricGuitarService.getAll();
        if(electricGuitars.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload("There are no electricGuitars"));
        }
        return ResponseEntity.ok(electricGuitars);
    }


    @GetMapping("/{model}")
    public ResponseEntity<?> getByModel(@PathVariable String model) {
        Optional<ElectricGuitar> electricGuitar = electricGuitarService.getByModel(model);
        if(electricGuitar.isPresent()){
            return ResponseEntity.ok(electricGuitar);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload("Model does not exist"));
    }

    @PutMapping("/{model}")
    public ResponseEntity<?> update(@PathVariable String model, @RequestBody ElectricGuitar electricGuitar) {
       try{
           electricGuitarService.update(model, electricGuitar);
           return ResponseEntity.ok(electricGuitar);
        }catch (EntityNotFoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
       }
    }


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