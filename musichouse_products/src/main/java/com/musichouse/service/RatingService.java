package com.musichouse.service;

import com.musichouse.dto.RatingDTO;

public interface RatingService {
    void addRating(RatingDTO dto);
    void updateRating(RatingDTO dto);
    boolean checkDelivery(String customerId, String model);
}
