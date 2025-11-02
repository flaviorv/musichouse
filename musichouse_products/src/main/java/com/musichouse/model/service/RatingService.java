package com.musichouse.model.service;

import com.musichouse.model.dto.RatingDTO;

public interface RatingService {
    void addRating(RatingDTO dto);
    void updateRating(RatingDTO dto);
}
