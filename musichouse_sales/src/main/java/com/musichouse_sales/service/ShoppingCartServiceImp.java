package com.musichouse_sales.service;

import com.musichouse_sales.adapter.client.ProductClient;
import com.musichouse_sales.domain.Item;
import com.musichouse_sales.domain.ShoppingCart;
import com.musichouse_sales.dto.ItemRequestDTO;
import com.musichouse_sales.dto.ItemResponseDTO;
import com.musichouse_sales.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ShoppingCartServiceImp {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductClient productClient;

    public ShoppingCartServiceImp(ShoppingCartRepository shoppingCartRepository, ProductClient productClient) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productClient = productClient;
    }

    public void addProductToShoppingCart(ItemRequestDTO itemRequestDTO) throws Exception {

        ItemResponseDTO itemResponseDTO = productClient.getById(itemRequestDTO.model());
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findById(itemRequestDTO.customerId());
        if (shoppingCart.isEmpty()) {
            shoppingCart =  Optional.of(new ShoppingCart(itemRequestDTO.customerId()));
        }
        Item item = itemResponseDTO.mapToItem();
        shoppingCart.get().add(item);
        shoppingCartRepository.save(shoppingCart.get());
    }
}
