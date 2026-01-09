package com.devsuperior.dscommerce.tests;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;

public class ProductFactory {

    public static Product createProduct() {
        Product product = new Product(1L, "PlayStation 5", "Lorem ipsum dolor sit amet", 3999.99, "Lorem ipsum dolor sit amet");
        product.getCategories().add(CategoryFactory.createCategory());
        return product;
    }

    public static ProductDTO createProductDTO() {
        return new ProductDTO(createProduct());
    }

}
