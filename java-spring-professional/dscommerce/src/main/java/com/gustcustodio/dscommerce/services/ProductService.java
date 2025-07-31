package com.gustcustodio.dscommerce.services;

import com.gustcustodio.dscommerce.dto.ProductDTO;
import com.gustcustodio.dscommerce.entities.Product;
import com.gustcustodio.dscommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public ProductDTO findById(Long id) {
        Optional<Product> result = repository.findById(id);
        Product product = result.get();
        return new ProductDTO(product);
    }
}
