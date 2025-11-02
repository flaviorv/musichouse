package com.musichouse.dto;

import com.musichouse.domain.rating.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@AllArgsConstructor
public class RatingDTO {
    private String customerId;
    private String productModel;
    private Rating rating;
}
