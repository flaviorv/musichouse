package com.musichouse.model.domain;

import lombok.Data;

@Data
public class ProductRatingMetrics {
    private double averageRating = 0d;
    private int ratingCount = 0;
    private double totalRatingSum = 0d;

    public void add(int newRatingValue) {
        totalRatingSum += newRatingValue;
        ratingCount++;
        this.averageRating = totalRatingSum/ratingCount;
    }

    public void update(int oldRating, int newRating) {
        totalRatingSum -= oldRating;
        totalRatingSum += newRating;
        this.averageRating = totalRatingSum/ratingCount;
    }
}
