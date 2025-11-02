package com.musichouse.repository;

import com.musichouse.domain.rating.ProductRating;
import com.musichouse.domain.rating.ProductRatingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<ProductRating, ProductRatingId> {
}
