package com.regi.backend.dto;

import com.regi.backend.entity.Order;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDetailDTO {
    private Long id;
    private String orderNumber;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String customerAddress;
    private Float totalAmount;
    private LocalDateTime orderDate;
    private String status;
    private List<OrderItemDTO> items;

    @Data
    public static class OrderItemDTO {
        private Long id;
        private String productName;
        private Float productPrice;
        private Integer quantity;
        private Float subtotal;

        // Constructor for easy conversion from entity
        public OrderItemDTO(Long id, String productName, Float productPrice, Integer quantity, Float subtotal) {
            this.id = id;
            this.productName = productName;
            this.productPrice = productPrice;
            this.quantity = quantity;
            this.subtotal = subtotal;
        }
    }

    // Static method to convert Order entity to DTO
    public static OrderDetailDTO fromEntity(Order order) {
        OrderDetailDTO dto = new OrderDetailDTO();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setCustomerName(order.getCustomerName());
        dto.setCustomerEmail(order.getCustomerEmail());
        dto.setCustomerPhone(order.getCustomerPhone());
        dto.setCustomerAddress(order.getCustomerAddress());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus().name());

        // Convert order items
        dto.setItems(order.getItems().stream()
                .map(item -> new OrderItemDTO(
                        item.getId(),
                        item.getProductName(),
                        item.getProductPrice(),
                        item.getQuantity(),
                        item.getSubtotal()
                ))
                .collect(Collectors.toList()));

        return dto;
    }
}