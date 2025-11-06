package com.musichouse_sales.controller;

import com.musichouse_sales.dto.ItemRequestDTO;
import com.musichouse_sales.service.ShoppingCartServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    private final static Logger LOG = LoggerFactory.getLogger(ShoppingCartController.class);

    private final ShoppingCartServiceImp shoppingCartService;

    public ShoppingCartController(ShoppingCartServiceImp shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }


    @PostMapping
    public ResponseEntity<?> addItem(ItemRequestDTO itemDTO) {
        try {
            shoppingCartService.addProductToShoppingCart(itemDTO);
            LOG.info("Item {} added to shopping cart", itemDTO.model());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
