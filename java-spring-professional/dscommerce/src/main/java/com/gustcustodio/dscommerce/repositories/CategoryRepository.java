package com.gustcustodio.dscommerce.repositories;

import com.gustcustodio.dscommerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
