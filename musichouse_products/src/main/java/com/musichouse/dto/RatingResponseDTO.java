package com.musichouse.dto;

import com.musichouse.domain.rating.Rating;

public record RatingResponseDTO (
    String customer,
    Rating rating
){}
