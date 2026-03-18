package com.service;


import com.model.CartItem;
import com.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {
    private CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
    @Transactional
    public void insert(CartItem item) {
        cartRepository.insert(item);
    }
    public void deleteItem(int id) {
        cartRepository.deleteById(id);
    }

    public List<CartItem> fetchAllItems() {
        return cartRepository.fetchAllItems();
    }

    public List<CartItem> fetchByUsername(String name) {
        return cartRepository.fetchByUsername(name);
    }

}
