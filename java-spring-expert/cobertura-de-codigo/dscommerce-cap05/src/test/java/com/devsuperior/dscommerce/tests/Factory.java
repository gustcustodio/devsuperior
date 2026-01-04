package com.devsuperior.dscommerce.tests;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Category;
import com.devsuperior.dscommerce.entities.Product;

public class Factory {

    public static Category createCategory() {
        return new Category(1L, "Games");
    }

    public static Category createCategory(Long id, String name) {
        return new Category(id, name);
    }

    public static Product createProduct() {
        Product product = new Product(1L, "PlayStation 5", "Lorem ipsum dolor sit amet", 3999.99, "Lorem ipsum dolor sit amet");
        product.getCategories().add(createCategory());
        return product;
    }

    public static ProductDTO createProductDTO() {
        return new ProductDTO(createProduct());
    }

}
