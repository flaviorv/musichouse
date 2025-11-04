package com.musichouse.repository;

import com.musichouse.domain.rating.ProductRating;
import com.musichouse.domain.rating.RatingId;
import com.musichouse.dto.RatingResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepository extends JpaRepository<ProductRating, RatingId> {
    @Query("SELECT new com.musichouse.dto.RatingResponseDTO(r.customer, r.rating) FROM ProductRating r WHERE r.product.model = ?1")
    List<RatingResponseDTO> getRatingsByProduct(String model);
}
