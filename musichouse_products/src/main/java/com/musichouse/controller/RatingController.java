package com.musichouse.controller;

import com.musichouse.exceptions.ProductNotFoundToAddRatingException;
import com.musichouse.exceptions.RatingNotFoundException;
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
        try {
            ratingService.addRating(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ProductNotFoundToAddRatingException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRating(@RequestBody RatingDTO dto){
        try {
            ratingService.updateRating(dto);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (RatingNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
