package com.musichouse.controller;

import com.musichouse.dto.RatingRequestDTO;
import com.musichouse.dto.RatingResponseDTO;
import com.musichouse.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRating(@RequestBody RatingRequestDTO dto){
        ratingService.addRating(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRating(@RequestBody RatingRequestDTO dto){
        ratingService.updateRating(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<?>  getRatingsByProduct(@RequestParam String model){
        List<RatingResponseDTO> ratings = ratingService.getRatingsByProduct(model);
        return ResponseEntity.status(HttpStatus.OK).body(ratings);
    }

}
