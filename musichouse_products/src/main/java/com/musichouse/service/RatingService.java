package com.musichouse.service;

import com.musichouse.dto.DeliveryResponseDTO;
import com.musichouse.dto.RatingDTO;

public interface RatingService {
    void addRating(RatingDTO dto);
    void updateRating(RatingDTO dto);
    DeliveryResponseDTO checkDelivery(String customerId, String model);
}
