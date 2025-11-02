package com.musichouse.service;

import com.musichouse.adapter.client.SaleClient;
import com.musichouse.exceptions.ProductNotDeliveredException;
import com.musichouse.exceptions.ProductNotFoundToAddRatingException;
import com.musichouse.exceptions.RatingNotFoundException;
import com.musichouse.domain.product.Product;
import com.musichouse.domain.rating.ProductRating;
import com.musichouse.domain.rating.ProductRatingId;
import com.musichouse.dto.RatingDTO;
import com.musichouse.repository.RatingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RatingServiceImp implements RatingService {

    private final RatingRepository ratingRepository;
    private final ProductServiceImp productService;
    private final SaleClient saleClient;

    public RatingServiceImp(RatingRepository ratingRepository, ProductServiceImp productService, SaleClient saleClient) {
        this.ratingRepository = ratingRepository;
        this.productService = productService;
        this.saleClient = saleClient;
    }

    @Override
    public boolean checkDelivery(String customerId, String model) {
        return saleClient.isProductDelivered(customerId, model);
    }

    @Override@Transactional
    public void addRating(RatingDTO dto) {
        Optional<Product> p = productService.getByModel(dto.getProductModel());
        if(p.isEmpty()) {
            throw new ProductNotFoundToAddRatingException();
        }
        boolean isDelivered = checkDelivery(dto.getCustomerId(), dto.getProductModel());
        if (!isDelivered) {
            throw new ProductNotDeliveredException();
        }
        ProductRating r = new ProductRating(dto.getCustomerId(), p.get(), dto.getRating());
        r.markNew();
        ratingRepository.save(r);
        p.get().addRating(r);
        productService.save(p.get());
    }

    @Override@Transactional
    public void updateRating(RatingDTO dto) {
        ProductRatingId rId = new ProductRatingId(dto.getCustomerId(), dto.getProductModel());
        Optional<ProductRating> oldPr = ratingRepository.findById(rId);
        if (oldPr.isEmpty()) {
            throw new RatingNotFoundException(rId);
        }
        oldPr.get().markNotNew();
        Product p = oldPr.get().getProduct();
        p.updateRating(oldPr.get(), dto.getRating());
    }
}
