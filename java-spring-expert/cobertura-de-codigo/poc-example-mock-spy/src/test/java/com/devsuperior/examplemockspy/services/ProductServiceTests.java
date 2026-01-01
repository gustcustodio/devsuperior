package com.devsuperior.examplemockspy.services;

import com.devsuperior.examplemockspy.dto.ProductDTO;
import com.devsuperior.examplemockspy.entities.Product;
import com.devsuperior.examplemockspy.repositories.ProductRepository;
import com.devsuperior.examplemockspy.services.exceptions.InvalidDataException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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

    private Long existingId, nonExistingId;
    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2L;
        product = new Product(1L, "Playstation", 2000.0);
        productDTO = new ProductDTO(product);

        Mockito.when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);
        Mockito.when(productRepository.getReferenceById(existingId)).thenReturn(product);
        Mockito.when(productRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
    }

    @Test
    public void insertShouldReturnProductDTOWhenValidData() {
        ProductService serviceSpy = Mockito.spy(productService);
        Mockito.doNothing().when(serviceSpy).validateData(productDTO);
        ProductDTO result = serviceSpy.insert(productDTO);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getName(), "Playstation");
    }

    @Test
    public void insertShouldThrowInvalidDataExceptionWhenProductNameIsBlank() {
        productDTO.setName("");
        ProductService serviceSpy = Mockito.spy(productService);
        Assertions.assertThrows(InvalidDataException.class, () -> serviceSpy.insert(productDTO));
    }

    @Test
    public void insertShouldThrowInvalidDataExceptionWhenProductPriceIsNegativeOrZero() {
        productDTO.setPrice(-5.0);
        ProductService serviceSpy = Mockito.spy(productService);
        Assertions.assertThrows(InvalidDataException.class, () -> serviceSpy.insert(productDTO));
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExistsAndValidData() {
        ProductService serviceSpy = Mockito.spy(productService);
        Mockito.doNothing().when(serviceSpy).validateData(productDTO);
        ProductDTO result = serviceSpy.update(existingId, productDTO);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), existingId);
    }

    @Test
    public void updateShouldThrowInvalidDataExceptionWhenIdExistsAndProductNameIsBlank() {
        productDTO.setName("");
        ProductService serviceSpy = Mockito.spy(productService);
        Assertions.assertThrows(InvalidDataException.class, () -> serviceSpy.update(existingId, productDTO));
    }

    @Test
    public void updateShouldThrowInvalidDataExceptionWhenProductPriceIsNegativeOrZero() {
        productDTO.setPrice(-5.0);
        ProductService serviceSpy = Mockito.spy(productService);
        Assertions.assertThrows(InvalidDataException.class, () -> serviceSpy.update(existingId, productDTO));
    }

}
