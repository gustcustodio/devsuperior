package com.gustcustodio.dscommerce.dto;

import com.gustcustodio.dscommerce.entities.Order;
import com.gustcustodio.dscommerce.entities.OrderItem;
import com.gustcustodio.dscommerce.entities.OrderStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {

    private Long id;
    private Instant moment;
    private OrderStatus status;
    private ClientDTO clientDTO;
    private PaymentDTO paymentDTO;
    private List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

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
            orderItemDTOList.add(orderItemDTO);
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

    public List<OrderItemDTO> getOrderItemDTOList() {
        return orderItemDTOList;
    }

    public Double getTotal() {
        double sum = 0.0;

        for (OrderItemDTO orderItemDTO : orderItemDTOList) {
            sum += orderItemDTO.getSubTotal();
        }

        return sum;
    }

}
