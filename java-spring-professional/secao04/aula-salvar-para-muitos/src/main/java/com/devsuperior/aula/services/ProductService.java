package com.devsuperior.aula.services;

import com.devsuperior.aula.dto.CategoryDTO;
import com.devsuperior.aula.dto.ProductDTO;
import com.devsuperior.aula.entities.Category;
import com.devsuperior.aula.entities.Product;
import com.devsuperior.aula.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public ProductDTO insert(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        for (CategoryDTO categoryDTO : dto.getCategories()) {
            Category category = new Category();
            category.setId(categoryDTO.getId());
            product.getCategories().add(category);
        }
        product = productRepository.save(product);
        return new ProductDTO(product);
    }

}
