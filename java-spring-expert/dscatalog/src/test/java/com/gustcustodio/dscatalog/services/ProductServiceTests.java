package com.gustcustodio.dscatalog.services;

import com.gustcustodio.dscatalog.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private long existingId;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;

        Mockito.doNothing().when(productRepository).deleteById(existingId);

        Mockito.when(productRepository.existsById(existingId)).thenReturn(true);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            productService.delete(existingId);
        });
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(existingId);
    }

}
