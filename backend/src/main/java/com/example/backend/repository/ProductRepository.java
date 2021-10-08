package com.example.backend.repository;

import com.example.backend.entity.Product;

import java.util.List;

//Expected jpa repository
public class ProductRepository {

    private final static List<Product> PRODUCTS = List.of(
            new Product(1L, "product1", 11.1),
            new Product(2L, "product2", 22.2),
            new Product(3L, "product3", 33.3),
            new Product(4L, "product4", 444.4));

    public static List<Product> getAllProducts() {
        return PRODUCTS;
    }

}
