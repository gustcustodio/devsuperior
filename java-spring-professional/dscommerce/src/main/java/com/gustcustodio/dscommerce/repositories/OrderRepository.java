package com.gustcustodio.dscommerce.repositories;

import com.gustcustodio.dscommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
