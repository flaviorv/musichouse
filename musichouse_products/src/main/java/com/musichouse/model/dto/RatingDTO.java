package com.musichouse.model.dto;

import com.musichouse.model.domain.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@AllArgsConstructor
public class RatingDTO {
    private String customerId;
    private String productModel;
    private Rating rating;
}
