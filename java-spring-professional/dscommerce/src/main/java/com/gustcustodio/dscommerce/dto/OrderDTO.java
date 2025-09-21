package com.gustcustodio.dscommerce.dto;

import com.gustcustodio.dscommerce.entities.Order;
import com.gustcustodio.dscommerce.entities.OrderItem;
import com.gustcustodio.dscommerce.entities.OrderStatus;
import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {

    private Long id;
    private Instant moment;
    private OrderStatus status;
    private ClientDTO clientDTO;
    private PaymentDTO paymentDTO;

    @NotEmpty(message = "Deve ter pelo menos uma categoria")
    private List<OrderItemDTO> items = new ArrayList<>();

    public OrderDTO() {
    }

    public OrderDTO(Long id, Instant moment, OrderStatus status, ClientDTO clientDTO, PaymentDTO paymentDTO) {
        this.id = id;
        this.moment = moment;
        this.status = status;
        this.clientDTO = clientDTO;
        this.paymentDTO = paymentDTO;
    }

    public OrderDTO(Order entity) {
        this.id = entity.getId();
        this.moment = entity.getMoment();
        this.status = entity.getStatus();
        this.clientDTO = new ClientDTO(entity.getClient());
        this.paymentDTO = (entity.getPayment() == null) ? null : new PaymentDTO(entity.getPayment());
        for (OrderItem orderItem : entity.getItems()) {
            OrderItemDTO orderItemDTO = new OrderItemDTO(orderItem);
            items.add(orderItemDTO);
        }
    }

    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public ClientDTO getClientDTO() {
        return clientDTO;
    }

    public PaymentDTO getPaymentDTO() {
        return paymentDTO;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public Double getTotal() {
        double sum = 0.0;

        for (OrderItemDTO orderItemDTO : items) {
            sum += orderItemDTO.getSubTotal();
        }

        return sum;
    }

}
