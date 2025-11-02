package com.musichouse.model.service;

import com.musichouse.exceptions.ProductAlreadyExistsException;
import com.musichouse.exceptions.ProductNotFoundToAddRatingException;
import com.musichouse.exceptions.RatingAlreadyExistsException;
import com.musichouse.exceptions.RatingNotFoundException;
import com.musichouse.model.domain.Product;
import com.musichouse.model.domain.ProductRating;
import com.musichouse.model.domain.ProductRatingId;
import com.musichouse.model.dto.RatingDTO;
import com.musichouse.model.repository.RatingRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RatingServiceImp implements RatingService {

    private final RatingRepository ratingRepository;

    private final ProductServiceImp productService;

    public RatingServiceImp(RatingRepository ratingRepository, ProductServiceImp productService) {
        this.ratingRepository = ratingRepository;
        this.productService = productService;
    }

    @Override
    public void addRating(RatingDTO dto) {
        System.out.println(dto);
        System.out.println(dto.getRating());
        System.out.println(dto.getCustomerId());
        System.out.println(dto.getProductModel());
        Optional<Product> p = productService.getByModel(dto.getProductModel());
        if(p.isPresent()){
            ProductRating r = new ProductRating(dto.getCustomerId(), p.get(), dto.getRating());
            p.get().addRating(r);
            try {
                productService.update(p.get());
            } catch (DataIntegrityViolationException e) {
                throw new RatingAlreadyExistsException();
            }
        } else throw new ProductNotFoundToAddRatingException();
    }

    @Override
    public void updateRating(RatingDTO dto) {
            ProductRatingId rId = new ProductRatingId(dto.getCustomerId(), dto.getProductModel());
            Optional<ProductRating> oldPr = ratingRepository.findById(rId);
            if (oldPr.isPresent()){
                Product p = oldPr.get().getProduct();
                ProductRating newRating = p.updateRating(oldPr.get(), dto.getRating());
                productService.update(p);
            } else throw new RatingNotFoundException(rId);
    }
}
