package com.regi.backend.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private CustomerDTO customer;
    private List<OrderItemDTO> items;
    private Float totalAmount;
    private String orderDate;
    private String orderNumber;

    @Data
    public static class CustomerDTO {
        private String name;
        private String email;
        private String phone;
        private String address;
    }

    @Data
    public static class OrderItemDTO {
        private Long productId;
        private String name;
        private Float price;
        private Integer quantity;
        private Float subtotal;
    }
}