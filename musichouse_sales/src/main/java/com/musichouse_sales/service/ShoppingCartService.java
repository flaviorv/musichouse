package com.musichouse_sales.service;

import com.musichouse_sales.dto.ItemRequestDTO;

public interface ShoppingCartService {
    void addToShoppingCart(ItemRequestDTO itemRequestDTO);
}
