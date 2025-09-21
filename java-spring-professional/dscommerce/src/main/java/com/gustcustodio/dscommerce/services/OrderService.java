package com.gustcustodio.dscommerce.services;

import com.gustcustodio.dscommerce.dto.OrderDTO;
import com.gustcustodio.dscommerce.entities.Order;
import com.gustcustodio.dscommerce.repositories.OrderRepository;
import com.gustcustodio.dscommerce.services.exceptions.ResourceNotFoundException;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = orderRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
        return new OrderDTO(order);
    }

}
