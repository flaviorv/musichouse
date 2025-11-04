package com.musichouse.controller;

import com.musichouse.exceptions.*;
import com.musichouse.dto.RatingDTO;
import com.musichouse.service.RatingService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRating(@RequestBody RatingDTO dto){
        ratingService.addRating(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRating(@RequestBody RatingDTO dto){
        ratingService.updateRating(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
