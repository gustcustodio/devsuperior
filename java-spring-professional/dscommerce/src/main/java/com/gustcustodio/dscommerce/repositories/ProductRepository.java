package com.gustcustodio.dscommerce.repositories;

import com.gustcustodio.dscommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
