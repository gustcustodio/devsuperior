package com.gustcustodio.dscatalog.repositories;

import com.gustcustodio.dscatalog.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    private long existingId;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        existingId = 1L;
        productRepository.deleteById(existingId);
        Optional<Product> result = productRepository.findById(existingId);
        Assertions.assertFalse(result.isPresent());
    }

}
