package com.gustcustodio.dscommerce.services;

import com.gustcustodio.dscommerce.dto.OrderDTO;
import com.gustcustodio.dscommerce.dto.OrderItemDTO;
import com.gustcustodio.dscommerce.entities.*;
import com.gustcustodio.dscommerce.repositories.OrderItemRepository;
import com.gustcustodio.dscommerce.repositories.OrderRepository;
import com.gustcustodio.dscommerce.repositories.ProductRepository;
import com.gustcustodio.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = orderRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
        authService.validateSelfOrAdmin(order.getClient().getId());
        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto) {
        Order order = new Order();
        User user = userService.authenticated();

        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);
        order.setClient(user);

        for (OrderItemDTO orderItemDTO : dto.getItems()) {
            Product product = productRepository.getReferenceById(orderItemDTO.getProductId());
            OrderItem orderItem = new OrderItem(order, product, orderItemDTO.getQuantity(), orderItemDTO.getPrice());
            order.getItems().add(orderItem);
        }

        orderRepository.save(order);
        orderItemRepository.saveAll(order.getItems());

        return new OrderDTO(order);
    }

}
