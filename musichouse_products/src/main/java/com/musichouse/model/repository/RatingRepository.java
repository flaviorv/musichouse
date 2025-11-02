package com.musichouse.model.repository;

import com.musichouse.model.domain.ProductRating;
import com.musichouse.model.domain.ProductRatingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<ProductRating, ProductRatingId> {
}
