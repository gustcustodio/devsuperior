package com.gustcustodio.dscommerce.repositories;

import com.gustcustodio.dscommerce.entities.OrderItem;
import com.gustcustodio.dscommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
