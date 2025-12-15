package com.gustcustodio.dscatalog.repositories;

import com.gustcustodio.dscatalog.entities.Product;
import com.gustcustodio.dscatalog.projections.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(nativeQuery = true, value = """
             SELECT * FROM (\s
             SELECT DISTINCT tb_product.id, tb_product.name\s
             FROM tb_product\s
             INNER JOIN tb_product_category\s
             ON tb_product_category.product_id = tb_product.id\s
             WHERE (:categoryIds IS NULL OR tb_product_category.category_id IN (:categoryIds))\s
             AND (LOWER(tb_product.name) LIKE LOWER(CONCAT('%',:name,'%')))\s
             ) AS tb_result
            \s""", countQuery = """
             SELECT COUNT(*) FROM (\s
                     SELECT DISTINCT tb_product.id, tb_product.name\s
                     FROM tb_product\s
                     INNER JOIN tb_product_category\s
                     ON tb_product_category.product_id = tb_product.id\s
                     WHERE (:categoryIds IS NULL OR tb_product_category.category_id IN (:categoryIds))\s
                     AND (LOWER(tb_product.name) LIKE LOWER(CONCAT('%',:name,'%')))\s
                     ) AS tb_result\s
            \s""")
    Page<ProductProjection> searchProducts(List<Long> categoryIds, String name, Pageable pageable);

    @Query("SELECT obj FROM Product obj JOIN FETCH obj.categories WHERE obj.id IN :productIds")
    List<Product> searchProductsWithCategories(List<Long> productIds);

}
