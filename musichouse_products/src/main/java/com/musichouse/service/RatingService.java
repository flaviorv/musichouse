package com.musichouse.service;

import com.musichouse.dto.DeliveryResponseDTO;
import com.musichouse.dto.RatingRequestDTO;
import com.musichouse.dto.RatingResponseDTO;
import java.util.List;

public interface RatingService {
    void addRating(RatingRequestDTO dto);
    void updateRating(RatingRequestDTO dto);
    DeliveryResponseDTO checkDelivery(String customerId, String model);
    List<RatingResponseDTO> getRatingsByProduct(String model);
}
