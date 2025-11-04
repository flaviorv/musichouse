package com.musichouse.service;

import com.musichouse.adapter.client.SaleClient;
import com.musichouse.dto.DeliveryResponseDTO;
import com.musichouse.dto.RatingResponseDTO;
import com.musichouse.exceptions.*;
import com.musichouse.domain.product.Product;
import com.musichouse.domain.rating.ProductRating;
import com.musichouse.domain.rating.RatingId;
import com.musichouse.dto.RatingRequestDTO;
import com.musichouse.repository.RatingRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public DeliveryResponseDTO checkDelivery(String customerId, String model) {
        return saleClient.deliveryStatus(customerId, model);
    }

    @Override@Transactional
    public void addRating(RatingRequestDTO dto) {
        Product p = productService.getByModel(dto.getProductModel())
                .orElseThrow(ProductNotFoundToAddRatingException::new);
        DeliveryResponseDTO responseDTO = checkDelivery(dto.getCustomerId(), dto.getProductModel());
        if (!responseDTO.delivered()) {
            throw new ProductNotDeliveredException();
        }
        if (!dto.getCustomerId().equals(responseDTO.customerId()) || !dto.getProductModel().equals(responseDTO.model())) {
            throw new DifferentRatingIdException("The Id returned by Sale is not the same as the one requested");
        }
        ProductRating r = new ProductRating(dto.getCustomerId(), p, dto.getRating());
        r.markNew();
        try {
            ratingRepository.saveAndFlush(r);
        } catch (DataIntegrityViolationException e) {
            throw new RatingAlreadyExistsException();
        }
        p.addRating(r);
        productService.save(p);
    }

    @Override@Transactional
    public void updateRating(RatingRequestDTO dto) {
        RatingId rId = new RatingId(dto.getCustomerId(), dto.getProductModel());
        ProductRating oldPr = ratingRepository.findById(rId)
                .orElseThrow(() -> new RatingNotFoundException(rId));
        oldPr.markNotNew();
        Product p = oldPr.getProduct();
        p.updateRating(oldPr, dto.getRating());
    }

    @Override
    public List<RatingResponseDTO> getRatingsByProduct(String model) {
        return ratingRepository.getRatingsByProduct(model);
    }
}
